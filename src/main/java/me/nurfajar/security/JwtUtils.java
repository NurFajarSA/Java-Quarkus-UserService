package me.nurfajar.security;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import me.nurfajar.model.Role;
import me.nurfajar.model.UserModel;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collections;
import java.util.UUID;

@Singleton
public class JwtUtils {

    @ConfigProperty(name = "user.app.jwtExpirationMs")
    String jwtExpirationTime;

    @Inject
    JWTParser jwtParser;

    public String generateToken(UserModel user) {
        return Jwt.issuer("https://nurfajar.me")
                .subject(user.getId().toString())
                .groups(Collections.singleton(user.getRole().name()))
                .claim("email", user.getEmail())
                .claim("username", user.getUsername())
                .expiresAt(System.currentTimeMillis() + Long.parseLong(jwtExpirationTime))
                .sign();
    }



    public UserModel parseToken(String token) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, ParseException {
        PublicKey publicKey = readPublicKey("public_key.pem");
        JsonWebToken jwt = jwtParser.verify(token, publicKey);
        UserModel user = new UserModel();
        user.setId(UUID.fromString(jwt.getSubject()));
        user.setEmail(jwt.getClaim("email"));
        user.setUsername(jwt.getClaim("username"));
        user.setRole(jwt.getGroups().contains("ADMIN") ? Role.ADMIN : Role.USER);
        return user;
    }

    public PublicKey readPublicKey(String resourcePath) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder key = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("-----")) {
                    key.append(line.trim());
                }
            }

            byte[] decoded = Base64.getDecoder().decode(key.toString());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(new X509EncodedKeySpec(decoded));
        }
    }

}
