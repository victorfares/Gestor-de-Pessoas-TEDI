package com.example.gestorTEDI.infrastructure.dtos;

import com.example.gestorTEDI.domain.entity.Membro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class MembroDTO {
    private final String nome;
    private final String email;
    private final String ra;
    private final String departamento;
    private final String funcao;
    private final String telefone;

    public static MembroDTO createMembro(Membro membro) {
        return new MembroDTO(
                membro.getNome(),
                membro.getEmail(),
                membro.getRa(),
                membro.getDepartamento(),
                membro.getFuncao(),
                membro.getTelefone()
        );
    }
}
