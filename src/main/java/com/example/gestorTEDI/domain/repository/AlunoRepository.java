package com.example.gestorTEDI.domain.repository;

import com.example.gestorTEDI.domain.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, String> {
    List<Aluno> findByNomeContainingIgnoreCaseOrRgContainingIgnoreCase(String nome, String rg);
    Optional<Aluno> findByRg(String rg);
    List<Aluno> findByRgContainingIgnoreCase(String rg);
}
