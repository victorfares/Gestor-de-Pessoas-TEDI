package com.example.gestorTEDI.domain.service;

import com.example.gestorTEDI.domain.entity.Aluno;
import com.example.gestorTEDI.domain.entity.AulaEvento;
import com.example.gestorTEDI.domain.entity.Membro;
import com.example.gestorTEDI.domain.exception.AlunoNotFoundException;
import com.example.gestorTEDI.domain.exception.AulaEventoNotFoundException;
import com.example.gestorTEDI.domain.exception.MembroNotFoundException;
import com.example.gestorTEDI.domain.repository.AlunoRepository;
import com.example.gestorTEDI.domain.repository.AulaEventoRepository;
import com.example.gestorTEDI.domain.repository.MembroRepository;
import com.example.gestorTEDI.infrastructure.dtos.SaveAulaEventoDTO;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
@RequiredArgsConstructor
@Slf4j
@Service
public class AulaEventoService {
    private final AulaEventoRepository aulaEventoRepository;

    private final AlunoRepository alunoRepository;
    private final MembroRepository membroRepository;

    @Transactional
    public AulaEvento createAulaEvento(SaveAulaEventoDTO dto) {
        List<String> rgsSeguros = Optional.ofNullable(dto.getRgsAlunos())
                .orElse(Collections.emptyList());

        List<String> rasSeguros = Optional.ofNullable(dto.getRasMembros())
                .orElse(Collections.emptyList());

        List<Aluno> alunosEncontrados = alunoRepository.findAllById(rgsSeguros);
        List<Membro> membrosEncontrados = membroRepository.findAllById(rasSeguros);
        AulaEvento aula = AulaEvento.builder()
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .data(dto.getData())
                .alunosPart(alunosEncontrados)
                .membrosPart(membrosEncontrados)
                .build();

        return aulaEventoRepository.save(aula);
    }

    public List<AulaEvento> findAll() {
        return aulaEventoRepository.findAll();
    }

    @Transactional
    public AulaEvento associarAluno(Long aulaId, String alunoRg) {
        AulaEvento aula = loadAula(aulaId);
        Aluno aluno = alunoRepository.findByRg(alunoRg)
                .orElseThrow(() -> new AlunoNotFoundException("Aluno não encontrado com RG: " + alunoRg));
        if (aula.getAlunosPart() == null) {
            aula.setAlunosPart(new ArrayList<>());
        }
        if (!aula.getAlunosPart().contains(aluno)) {
            aula.getAlunosPart().add(aluno);
        }
        log.info("### Associando Aluno {} à Aula {} ###", aluno.getNome(), aula.getId());
        return aulaEventoRepository.save(aula);
    }


    @Transactional
    public AulaEvento associarMembro(Long aulaId, String membroRa) {
        AulaEvento aula = loadAula(aulaId);

        Membro membro = membroRepository.findByRa(membroRa)
                .orElseThrow(() -> new MembroNotFoundException("Membro não encontrado com RA: " + membroRa));

        if (aula.getMembrosPart() == null) {
            aula.setMembrosPart(new ArrayList<>());
        }

        if (!aula.getMembrosPart().contains(membro)) {
            aula.getMembrosPart().add(membro);
        }

        log.info("### Associando Membro {} à Aula {} ###", membro.getNome(), aula.getId());
        return aulaEventoRepository.save(aula);
    }


    public AulaEvento loadAula(Long id) {
        return aulaEventoRepository.findById(id)
                .orElseThrow(() -> new AulaEventoNotFoundException("Aula/Evento não encontrada com ID: " + id));
    }
    @Transactional
    public void deleteAula(Long id) {
        if (!aulaEventoRepository.existsById(id)) {
            throw new AulaEventoNotFoundException("Não é possível deletar. Aula não encontrada com ID: " + id);
        }
        aulaEventoRepository.deleteById(id);
        log.info("### AulaEvento com ID {} deletada com sucesso ###", id);
    }

    @Transactional
    public AulaEvento updateAula(Long id, SaveAulaEventoDTO dto) {
        AulaEvento aula = loadAula(id);
        aula.setTitulo(dto.getTitulo());
        aula.setDescricao(dto.getDescricao());
        aula.setData(dto.getData());
        if (dto.getRgsAlunos() != null) {
            List<Aluno> alunos = alunoRepository.findAllById(dto.getRgsAlunos());
            aula.setAlunosPart(alunos);
        }
        if (dto.getRasMembros() != null) {
            List<Membro> membros = membroRepository.findAllById(dto.getRasMembros());
            aula.setMembrosPart(membros);
        }
        return aula;
    }
}
