package com.example.gestorTEDI.domain.model;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Pessoa {

    private String nome;
    private String email;
    private int telefone;
}
