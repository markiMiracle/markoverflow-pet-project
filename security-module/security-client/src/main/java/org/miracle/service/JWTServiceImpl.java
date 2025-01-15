package org.miracle.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import org.miracle.exception.InvalidRootTokenException;
import org.miracle.utils.RsaKeyProperties;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
public class JWTServiceImpl implements JWTService {

    private RsaKeyProperties keyProperties;
    private HttpServletRequest request;

    private final static String TOKEN_ROOT_COOKIE_NAME = "ROOT_TOKEN";

    @Override
    public String generateTokenCookie(String rootAttribute) throws Exception {
        Instant now = Instant.now();
        String tokenCookie = JWT.create()
                .withIssuedAt(now)
                .withSubject(rootAttribute)
                .withExpiresAt(now.plus(1, ChronoUnit.MINUTES))
                .sign(Algorithm.RSA256(keyProperties.loadPrivateKey()));
        Cookie cookie = new Cookie(TOKEN_ROOT_COOKIE_NAME, tokenCookie);
        cookie.setPath("/");
        return tokenCookie;
    }

    @Override
    public DecodedJWT verifyRootToken() {
        Cookie token = WebUtils.getCookie(request, TOKEN_ROOT_COOKIE_NAME);
        if (token == null || token.getValue().isEmpty()) {
            throw new InvalidRootTokenException();
        }
        try {
            Algorithm algorithm = Algorithm.RSA256(keyProperties.loadPublicKey(), null);
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token.getValue());
        } catch (Exception ex) {
            throw new InvalidRootTokenException();
        }
    }
}