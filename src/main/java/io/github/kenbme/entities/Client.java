package io.github.kenbme.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.Entity;

@Entity
public class Client extends PanacheEntity {
  @Username
  public String username;
  @Password
  public String encryptedPassword;
  public String email;
  @Roles
  public String role;
}
