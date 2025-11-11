package com.example.gestorTEDI.infrastructure.dtos;

import com.example.gestorTEDI.domain.model.NivelDigital;
import lombok.Data;

@Data
public class SaveAlunoDataDTO {
    private final String nome;
    private final String email;
    private final String telefone;
    private final String rg;
    private final NivelDigital nivelDigital;
}
