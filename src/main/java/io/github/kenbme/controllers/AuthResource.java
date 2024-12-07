package io.github.kenbme.controllers;

import java.util.Map;
import java.util.Optional;

import io.github.kenbme.dtos.LoginDTO;
import io.github.kenbme.dtos.RegisterDTO;
import io.github.kenbme.entities.Client;
import io.github.kenbme.utils.JwtUtils;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@Path("/api")
public class AuthResource {

  @Path("/login")
  @POST
  public Map<String, String> login(@Valid LoginDTO dto) {
    Optional<Client> clientOpt = Client.find("email", dto.email).firstResultOptional();
    if (clientOpt.isEmpty()) {
      BcryptUtil.matches(dto.password, JwtUtils.HASH_SAMPLE);
      throw new WebApplicationException("Invalid email or password", Response.Status.CONFLICT);
    }
    var client = clientOpt.get();
    if (!BcryptUtil.matches(dto.password, client.encryptedPassword)) {
      throw new WebApplicationException("Invalid email or password", Response.Status.CONFLICT);
    }
    return Map.of("token", JwtUtils.generateToken(client));
  }

  @Path("/register")
  @POST
  @Transactional
  public Map<String, String> register(@Valid RegisterDTO dto) {
    var clientOpt = Client.find("email", dto.email).firstResultOptional();
    if (!clientOpt.isEmpty()) {
      throw new WebApplicationException("Email already registered", Response.Status.CONFLICT);
    }
    var client = new Client();
    client.username = dto.username;
    client.email = dto.email;
    client.encryptedPassword = BcryptUtil.bcryptHash(dto.password);
    client.role = "customer";
    client.persist();
    return Map.of("message", "Registered successfully");
  }

}
