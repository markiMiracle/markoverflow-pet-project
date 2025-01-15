package org.miracle.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Getter
@Setter
@RequiredArgsConstructor
public class RsaKeyProperties {

    private String publicKey;
    private String privateKey;

    private final ResourceLoader resourceLoader;

    private String loadKeyContent(String path) throws IOException {
        Resource resource = resourceLoader.getResource(path);
        return new String(Files.readAllBytes(resource.getFile().toPath()));
    }

    public RSAPrivateKey loadPrivateKey() throws Exception {
        String privateKeyPEM = loadKeyContent(privateKey)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) keyFactory.generatePrivate(spec);
    }

    public RSAPublicKey loadPublicKey() throws Exception {
        String publicKeyPEM = loadKeyContent(publicKey)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);

        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(spec);
    }
}
