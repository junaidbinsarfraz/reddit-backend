package com.demo.redditbackend.security;

import com.demo.redditbackend.exception.SpringRedditException;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import static io.jsonwebtoken.Jwts.parser;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init() throws SpringRedditException {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new SpringRedditException("Exception while loading the keystore");
        }
    }

    public String generateToken(Authentication authentication) throws SpringRedditException {
        User principle = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principle.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }

    public boolean validateToken(String token) throws SpringRedditException {
        getClaimsJwt(token);
        return true;
    }

    public String getUsernameFromJwt(String token) throws SpringRedditException {
        return getClaimsJwt(token).getBody().getSubject();
    }

    private Jws<Claims> getClaimsJwt(String token) throws SpringRedditException {
        return parser().setSigningKey(getPublicKey()).parseClaimsJws(token);
    }

    private PrivateKey getPrivateKey() throws SpringRedditException {
        try {
            return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new SpringRedditException("Exception while loading private key from the keystore");
        }
    }

    private PublicKey getPublicKey() throws SpringRedditException {
        try {
            return keyStore.getCertificate("springblog").getPublicKey();
        } catch (KeyStoreException e) {
            throw new SpringRedditException("Exception while loading public key from the keystore");
        }
    }
}
