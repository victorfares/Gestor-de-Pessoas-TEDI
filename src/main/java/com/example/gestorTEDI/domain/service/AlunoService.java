package com.example.gestorTEDI.domain.service;

import com.example.gestorTEDI.domain.entity.Aluno;
import com.example.gestorTEDI.domain.exception.AlunoNotFoundException;
import com.example.gestorTEDI.domain.repository.AlunoRepository;
import com.example.gestorTEDI.domain.repository.MembroRepository;
import com.example.gestorTEDI.infrastructure.dtos.SaveAlunoDataDTO;
import com.example.gestorTEDI.infrastructure.exception.RequestException;
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
    public Aluno loadAluno (String alunoRg) {
        return alunoRepository
                .findByRg(alunoRg)
                .orElseThrow(() -> new AlunoNotFoundException("Aluno não encontrado com o RG: " + alunoRg));

    }

    public List<Aluno> findAlunos(String rg) { // <-- MUDANÇA AQUI
        if (rg != null && !rg.isBlank()) {
            return alunoRepository.findByRgContainingIgnoreCase(rg); // <-- MUDANÇA AQUI
        } else {
            return alunoRepository.findAll();
        }
    }

    @Transactional
    public Aluno updateAluno(String alunoRg, SaveAlunoDataDTO saveAlunoData){
        if(existeAlunoNome(saveAlunoData.getNome())){
            throw new RequestException("Já existe um aluno cadastrado com este nome.", "aluno.nome.duplicate");
        }
        Aluno aluno = loadAluno(alunoRg);
        aluno.setNome(saveAlunoData.getNome());
        aluno.setRg(saveAlunoData.getRg());
        aluno.setEmail(saveAlunoData.getEmail());
        aluno.setTelefone(saveAlunoData.getTelefone());
        aluno.setNivelDigital(saveAlunoData.getNivelDigital());

        return aluno;
    }

    private boolean existeAlunoNome(String nome) {
        return alunoRepository.findByNome(nome).isPresent();
    }


}
