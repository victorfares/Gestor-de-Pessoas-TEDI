package com.example.gestorTEDI.domain.entity;

import com.example.gestorTEDI.domain.model.NivelDigital;
import com.example.gestorTEDI.domain.model.Pessoa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

@AttributeOverrides({
        @AttributeOverride(name = "nome", column = @Column(name = "aluno_nome", nullable = false)),
        @AttributeOverride(name = "email", column = @Column(name = "aluno_email", unique = true)),
        @AttributeOverride(name = "telefone", column = @Column(name = "aluno_telefone", unique = true))
})

public class Aluno extends Pessoa {
    @Id
    @Column(name  = "aluno_rg",  nullable = false, unique = true)
    private String rg;

    @Enumerated(EnumType.STRING)
    @Column(name = "aluno_niveldigital",  nullable = false)
    private NivelDigital nivelDigital;
}
