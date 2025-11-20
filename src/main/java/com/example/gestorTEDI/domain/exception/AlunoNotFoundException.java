package com.example.gestorTEDI.domain.exception;

import com.example.gestorTEDI.infrastructure.exception.RequestException;
import org.apache.coyote.BadRequestException;

public class AlunoNotFoundException extends RequestException {


    public AlunoNotFoundException(String alunoRg) {
        super("AlunoNaoEncontrado", "Aluno nao encontrado com este rg: " +alunoRg);
    }
}
