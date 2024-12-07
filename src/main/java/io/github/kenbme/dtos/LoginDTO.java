package io.github.kenbme.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class LoginDTO {
  @NotNull
  @Email
  public String email;
  @NotNull
  public String password;
}
