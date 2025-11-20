package com.example.gestorTEDI.infrastructure.dtos;

import com.example.gestorTEDI.domain.model.NivelDigital;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class SaveAlunoDataDTO {

    @NotNull(message = "nome nao pode ser vazio")
    @Size(min = 1, max = 100)
    private final String nome;
    @NotNull
    @Email
    private final String email;
    @NotNull
    private final String telefone;
    @NotNull
    private final String rg;
    @NotNull
    private final NivelDigital nivelDigital;
}
