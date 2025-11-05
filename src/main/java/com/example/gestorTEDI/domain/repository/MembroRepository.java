package com.example.gestorTEDI.domain.repository;

import com.example.gestorTEDI.domain.entity.Membro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembroRepository extends JpaRepository<Membro, String> {
}
