package org.miracle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.miracle.dto.SessionDTO;
import org.miracle.dto.UserDTO;
import org.miracle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    private WireMockServer wireMockServer;

    @Autowired
    private UserRepository repository;

    @Container
    private static PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres");

    @Container
    private static GenericContainer<?> redisContainer = new GenericContainer<>("redis")
            .withExposedPorts(6379);


    private static final String SESSION_COOKIE_NAME = "SESSIONID";

    private static final String IP_HEADER = "ip";
    private static final String IP_VALUE = "123.12.1";

    private static final String DEVICE_HEADER = "device";
    private static final String DEVICE_VALUE = "android";

    private static final String SESSION_URL = "/session/";
    private static final String REGISTRATION_URL = "/registration";
    private static final String LOGIN_URL = "/login";
    private static final String AUDIT_URL = "/api/audit";
    private static final String AUDIT_URI = "http://localhost:8083";

    private static final String USER_LOGIN = "Ryan Gosling";
    private static final String USER_PASSWORD = "1234";

    private UserDTO userDTO;

    @DynamicPropertySource
    public static void setupMockBaseUrl(DynamicPropertyRegistry registry) {
        registry.add( "spring.redis.port", () -> redisContainer.getMappedPort(6379));

        registry.add("spring.datasource.url", database::getJdbcUrl);
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
        registry.add("audit.feign.client.url", () -> AUDIT_URI);
    }

    @BeforeEach
    public void setup() {
        userDTO = new UserDTO(USER_LOGIN + System.currentTimeMillis(), USER_PASSWORD);
        wireMockServer = new WireMockServer(8083);
        wireMockServer.start();

        wireMockServer.stubFor(
                WireMock.post(AUDIT_URL)
                        .willReturn(aResponse().withStatus(200))
        );
    }

    @AfterEach
    public void shutDown() {
        wireMockServer.stop();
    }

    @Test
    public void registrationTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REGISTRATION_URL)
                        .content(om.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(IP_HEADER, IP_VALUE)
                        .header(DEVICE_HEADER, DEVICE_VALUE))
                .andExpect(status().isOk()).andExpect(cookie().exists(SESSION_COOKIE_NAME));

        var user = repository.findByLogin(userDTO.getLogin());

        assertThat(user).isNotEmpty();
        assertThat(BCrypt.checkpw(userDTO.getPassword(), user.get().getPasswordDigest())).isTrue();
    }

    @Test
    public void registrationWithExistsUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REGISTRATION_URL)
                .content(om.writeValueAsString(userDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .header(IP_HEADER, IP_VALUE)
                .header(DEVICE_HEADER, DEVICE_VALUE));

        mockMvc.perform(MockMvcRequestBuilders.post(REGISTRATION_URL)
                        .content(om.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(IP_HEADER, IP_VALUE)
                        .header(DEVICE_HEADER, DEVICE_VALUE))
                .andExpect(status().isBadRequest()).andExpect(cookie().doesNotExist(SESSION_COOKIE_NAME));
    }

    @Test
    public void registrationWithoutHeadersTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REGISTRATION_URL)
                        .content(om.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andExpect(cookie().doesNotExist(SESSION_COOKIE_NAME));
    }

    @Test
    public void loginTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REGISTRATION_URL)
                .content(om.writeValueAsString(userDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .header(IP_HEADER, IP_VALUE)
                .header(DEVICE_HEADER, DEVICE_VALUE));

        mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_URL)
                        .content(om.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(IP_HEADER, IP_VALUE)
                        .header(DEVICE_HEADER, DEVICE_VALUE))
                .andExpect(status().isOk()).andExpect(cookie().exists(SESSION_COOKIE_NAME));
    }

    @Test
    public void loginWithoutExistsUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_URL)
                        .content(om.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(IP_HEADER, IP_VALUE)
                        .header(DEVICE_HEADER, DEVICE_VALUE))
                .andExpect(status().isForbidden()).andExpect(cookie().doesNotExist(SESSION_COOKIE_NAME));
    }

    @Test
    public void loginWithWrongPasswordTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REGISTRATION_URL)
                .content(om.writeValueAsString(userDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .header(IP_HEADER, IP_VALUE)
                .header(DEVICE_HEADER, DEVICE_VALUE));

        userDTO.setPassword("wrongPassword");

        mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_URL)
                        .content(om.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(IP_HEADER, IP_VALUE)
                        .header(DEVICE_HEADER, DEVICE_VALUE))
                .andExpect(status().isBadRequest()).andExpect(cookie().doesNotExist(SESSION_COOKIE_NAME));
    }

    @Test
    public void loginWithoutHeadersTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REGISTRATION_URL)
                .content(om.writeValueAsString(userDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .header(IP_HEADER, IP_VALUE)
                .header(DEVICE_HEADER, DEVICE_VALUE));

        mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_URL)
                        .content(om.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andExpect(cookie().doesNotExist(SESSION_COOKIE_NAME));
    }

    @Test
    public void getSessionTest() throws Exception {
        var perform = mockMvc.perform(MockMvcRequestBuilders.post(REGISTRATION_URL)
                        .content(om.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(IP_HEADER, IP_VALUE)
                        .header(DEVICE_HEADER, DEVICE_VALUE))
                .andReturn();

        var sessionId = perform.getResponse().getCookie(SESSION_COOKIE_NAME).getValue();
        var sessionDTOJson = om.writeValueAsString(new SessionDTO(userDTO.getLogin()));

        mockMvc.perform(MockMvcRequestBuilders.get(SESSION_URL + sessionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(sessionDTOJson));
    }

    @Test
    public void getNotExistsSessionTest() throws Exception {
        var sessionId = "wrongSessionId";

        mockMvc.perform(MockMvcRequestBuilders.get(SESSION_URL + sessionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

}