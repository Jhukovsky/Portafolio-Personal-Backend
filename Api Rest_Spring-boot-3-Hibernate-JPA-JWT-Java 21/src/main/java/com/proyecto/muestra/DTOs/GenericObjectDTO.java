package com.proyecto.muestra.DTOs;

import jakarta.validation.constraints.*;

public record GenericObjectDTO(
        @NotNull
        boolean active,

        @NotNull
        @Max(100)
        int amount,

        @NotNull
        @Max(100)
        float amountTotal,

        @NotNull
        @Min(0)
        long identification,

        @NotBlank
        String description,
        @NotNull
        char type,

        @NotNull
        long userId) {
}
