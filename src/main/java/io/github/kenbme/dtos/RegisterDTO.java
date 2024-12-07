package io.github.kenbme.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class RegisterDTO {
  @NotNull
  public String username;
  @NotNull
  public String password;
  @NotNull
  @Email
  public String email;
}
