package com.example.gestorTEDI.domain.exception;

import jakarta.persistence.EntityNotFoundException;

public class MembroNotFoundException extends EntityNotFoundException {
    public MembroNotFoundException(String message) {
        super(message);
    }
}
