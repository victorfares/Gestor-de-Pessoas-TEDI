package com.example.gestorTEDI.domain.exception;

import com.example.gestorTEDI.infrastructure.exception.RequestException;

public class AlunoJaExisteException extends RequestException {
    public AlunoJaExisteException(String nome) {
        super("Aluno Ja cadastrado com esse nome.", "Nome : "+nome);
    }
}
