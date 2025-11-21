package com.example.gestorTEDI.domain.exception;

import jakarta.persistence.EntityNotFoundException;

public class AulaEventoNotFoundException extends EntityNotFoundException {
    public AulaEventoNotFoundException(String message) {
        super(message);
    }
}