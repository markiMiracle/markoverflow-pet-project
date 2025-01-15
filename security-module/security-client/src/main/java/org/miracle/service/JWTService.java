package org.miracle.service;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface JWTService {

    String generateTokenCookie(String rootAttribute) throws Exception;

    DecodedJWT verifyRootToken();

}
