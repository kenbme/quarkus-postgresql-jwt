package io.github.kenbme.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
public class Client extends PanacheEntity {
  @Username
  @NotNull
  public String username;
  @Password
  @NotNull
  public String encryptedPassword;
  @NotNull
  public String email;
  @Roles
  @NotNull
  public String role;
}
