package io.github.kenbme.utils;

import io.github.kenbme.entities.Client;
import io.smallrye.jwt.build.Jwt;

public class JwtUtils {

  public static String getHashSample() {
    return "$2a$10$7EqJtq98hPqEX7fNZaFWoOaMdI1ajxZ7dkpQ.j8H2vMGy1xHDVdZK";
  }

  public static String generateToken(Client client) {
    return Jwt.issuer("quarkus")
        .upn(client.username)
        .groups(client.role)
        .claim("email", client.email)
        .expiresAt(System.currentTimeMillis() + 3600 * 1000) // 1 hora
        .sign();
  }

}
