package com.proyecto.muestra.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotBlank
        @Size(max = 30)
        String userName,

        @NotBlank
        String password,
        @Email
        @NotBlank
        @Size(max = 50)
        String email) {
}
