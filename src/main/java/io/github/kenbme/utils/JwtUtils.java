package io.github.kenbme.utils;

import java.time.Instant;

import io.github.kenbme.entities.Client;
import io.smallrye.jwt.build.Jwt;

public class JwtUtils {

  private static final String SECRET_KEY = "NvH0oIr8gxCyuWQlPmOl0Jjo7uypRPa1r5LLYRRq3H8";

  public static final String HASH_SAMPLE = "$2a$10$7EqJtq98hPqEX7fNZaFWoOaMdI1ajxZ7dkpQ.j8H2vMGy1xHDVdZK";

  public static String generateToken(Client client) {
    return Jwt.issuer("quarkus")
        .upn(client.username)
        .groups(client.role)
        .claim("email", client.email)
        .expiresAt(Instant.now().plusSeconds(3600)) // 1 hora
        .signWithSecret(SECRET_KEY);
  }

}
