package com.example.gestorTEDI.infrastructure.dtos;

import lombok.Data;

@Data
public class SaveMembroDataDTO {
    private final String raMembro;
    private final String nomeMembro;
    private final String emailMembro;
    private final String departamentoMembro;
    private final String funcaoMembro;
    private final String telefoneMembro;

}
