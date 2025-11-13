package com.example.gestorTEDI.domain.service;

import com.example.gestorTEDI.domain.entity.Aluno;
import com.example.gestorTEDI.domain.repository.AlunoRepository;
import com.example.gestorTEDI.domain.repository.MembroRepository;
import com.example.gestorTEDI.infrastructure.dtos.SaveAlunoDataDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j



public class AlunoService {
    private final AlunoRepository alunoRepository;
    private final MembroRepository membroRepository;

    @Transactional
    public Aluno createAluno(SaveAlunoDataDTO saveAlunoDataDTO) {
        Aluno aluno = Aluno.builder()
                .nome(saveAlunoDataDTO.getNome())
                .rg(saveAlunoDataDTO.getRg())
                .email(saveAlunoDataDTO.getEmail())
                .telefone(saveAlunoDataDTO.getTelefone())
                .nivelDigital(saveAlunoDataDTO.getNivelDigital())
                .build();
        alunoRepository.save(aluno);
        return aluno;
    }

    public Aluno findByRg(String rg) {
        return alunoRepository.findByRg(rg)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com RG: " + rg));
    }

    public List<Aluno> findAlunos(String rg) { // <-- MUDANÇA AQUI
        if (rg != null && !rg.isBlank()) {
            // Se o RG foi passado, busca por ele
            return alunoRepository.findByRgContainingIgnoreCase(rg); // <-- MUDANÇA AQUI
        } else {
            // Se o RG está nulo ou vazio, retorna todos
            return alunoRepository.findAll();
        }
    }
}
