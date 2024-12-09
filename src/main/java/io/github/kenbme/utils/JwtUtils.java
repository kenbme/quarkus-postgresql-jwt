package io.github.kenbme.utils;

import java.time.Instant;

import io.github.kenbme.entities.Client;
import io.smallrye.jwt.build.Jwt;

public class JwtUtils {

  private static final String ISSUER = "quarkus";

  public static String generateToken(Client client) {
    return Jwt.issuer(ISSUER)
        .upn(client.username)
        .groups(client.role)
        .claim("email", client.email)
        .expiresAt(Instant.now().plusSeconds(3600)) // 1 hora
        .sign();
  }

}
