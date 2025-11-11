package com.example.gestorTEDI.infrastructure.dtos;


import com.example.gestorTEDI.domain.entity.Aluno;
import com.example.gestorTEDI.domain.model.NivelDigital;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlunoDTO {
    private final String nome;
    private final String email;
    private final String telefone;
    private final String rg;
    private final NivelDigital nivelDigital;

    public static AlunoDTO createAlunoDTO(Aluno aluno) {
        return new AlunoDTO(
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getTelefone(),
                aluno.getRg(),
                aluno.getNivelDigital()
        );

    }
}
