package io.github.kenbme.utils;

import java.time.Instant;

import io.github.kenbme.entities.Client;
import io.smallrye.jwt.build.Jwt;

public class JwtUtils {

  private static final String ISSUER = "quarkus";

  public static final String HASH_SAMPLE = "$2a$10$7EqJtq98hPqEX7fNZaFWoOaMdI1ajxZ7dkpQ.j8H2vMGy1xHDVdZK";

  public static String generateToken(Client client) {
    return Jwt.issuer(ISSUER)
        .upn(client.username)
        .groups(client.role)
        .claim("email", client.email)
        .expiresAt(Instant.now().plusSeconds(3600)) // 1 hora
        .sign();
  }

}
