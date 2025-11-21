package com.example.gestorTEDI.domain.service;

import com.example.gestorTEDI.domain.entity.Aluno;
import com.example.gestorTEDI.domain.entity.AulaEvento;
import com.example.gestorTEDI.domain.entity.Membro;
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

import java.util.List;

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
        // 1. Busca os Alunos pelos RGs fornecidos
        // O método findAllById aceita uma lista de IDs e retorna quem ele achou
        List<Aluno> alunosEncontrados = alunoRepository.findAllById(dto.getRgsAlunos());

        // 2. Busca os Membros pelos RAs fornecidos
        List<Membro> membrosEncontrados = membroRepository.findAllById(dto.getRasMembros());

        // 3. Cria o objeto AulaEvento
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
}
