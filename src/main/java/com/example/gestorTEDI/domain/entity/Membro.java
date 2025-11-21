package com.example.gestorTEDI.domain.entity;

import com.example.gestorTEDI.domain.model.Pessoa;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = "nome", column = @Column(name = "membro_nome", nullable = false)),
        @AttributeOverride(name = "email", column = @Column(name = "membro_email", unique = true)),
        @AttributeOverride(name = "telefone", column = @Column(name = "membro_telefone", unique = true))
})

public class Membro extends Pessoa {
    @Id
    @Column(name = "membro_ra", nullable = false,  unique = true)
    private String ra;
    @Column(name = "membro_departamento",  nullable = false)
    private String departamento;
    @Column(name = "membro_funcao")
    private String funcao;

    @ManyToMany(mappedBy = "membrosPart")
    private List<AulaEvento> aulasEventos;

}
