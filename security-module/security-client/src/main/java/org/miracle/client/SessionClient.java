package org.miracle.client;

import org.miracle.dto.SessionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "sessionId", url = "${security.feign.client.url}")
public interface SessionClient {

    @RequestMapping(method = RequestMethod.GET, value = "/security/session/{sessionId}")
    SessionDTO getSessionById(@PathVariable("sessionId") String sessionId);

}
