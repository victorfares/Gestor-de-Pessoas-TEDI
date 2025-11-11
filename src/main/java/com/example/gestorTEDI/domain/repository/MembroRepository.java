package com.example.gestorTEDI.domain.repository;

import com.example.gestorTEDI.domain.entity.Membro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembroRepository extends JpaRepository<Membro, String> {
    List<Membro> findByNomeContainingIgnoreCaseOrRaContainingIgnoreCase(String nome, String ra);

}
