package com.example.userservice.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestLogin {
  @NotNull(message = "Email cannot be null")
  @Size(min = 2, message = "Email not be less than 2 characters")
  @Email
  private String email;

  @NotNull(message = "Password cannot be null")
  @Size(min = 2, message = "Password must be equal or grater than 8 characters")
  @Email
  private String password;
}
