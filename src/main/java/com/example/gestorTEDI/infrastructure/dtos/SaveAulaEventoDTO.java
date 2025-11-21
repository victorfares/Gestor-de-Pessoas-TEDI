package com.example.gestorTEDI.infrastructure.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor

public class SaveAulaEventoDTO {

    @NotNull(message = "O título não pode ser nulo")
    @Size(min = 5, max = 100, message = "O título deve ter entre 5 e 100 caracteres")
    private final String titulo;

    @NotNull(message = "A descrição é obrigatória")
    private final String descricao;

    @NotNull(message = "A data do evento é obrigatória")
    private final LocalDate data;

    private final List<String> rgsAlunos;

    private final List<String> rasMembros;
}

