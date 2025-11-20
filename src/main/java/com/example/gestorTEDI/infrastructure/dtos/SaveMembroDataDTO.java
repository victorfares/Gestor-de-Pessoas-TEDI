package com.example.gestorTEDI.infrastructure.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SaveMembroDataDTO {
    @NotNull(message = "ra nao pode ser nulo")
    private final String raMembro;
    @NotNull(message = "nome nao pode ser vazio")
    @Size(min = 1, max = 100)
    private final String nomeMembro;
    @NotNull
    @Email
    private final String emailMembro;
    @NotNull
    private final String departamentoMembro;
    @NotNull
    private final String funcaoMembro;
    @NotNull
    private final String telefoneMembro;

}
