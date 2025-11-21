package com.example.gestorTEDI.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AulaEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aulaevento_id", nullable = false)
    private Long id;

    @Column(name = "aulaevento_descricao", nullable = false)
    private String descricao;

    @Column(name = "aulaevento_titulo", nullable = false)
    private String titulo;

    @Column(name = "aulaevento_data", nullable = false)
    private LocalDate data;

    @ManyToMany
    @JoinTable(
            name = "aulaevento_aluno",
            joinColumns = @JoinColumn(name = "aulaevento_id"),
            inverseJoinColumns =  @JoinColumn(name = "aluno_rg")
    )
    private List<Aluno> alunosPart;

    @ManyToMany
    @JoinTable(
            name = "aulaevento_membro",
            joinColumns = @JoinColumn(name = "aulaevento_id"),
            inverseJoinColumns =  @JoinColumn(name = "membro_ra")
    )
    private List<Membro> membrosPart;

}
