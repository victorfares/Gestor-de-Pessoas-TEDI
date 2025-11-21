package com.example.gestorTEDI.infrastructure.dtos;

import com.example.gestorTEDI.domain.entity.AulaEvento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AulaEventoDTO {

    private String id;
    private String descricao;
    private String titulo;
    private LocalDate data;

    // Vamos retornar a lista completa de DTOs aninhada
    private List<AlunoDTO> alunos;
    private List<MembroDTO> membros;

    public static AulaEventoDTO create(AulaEvento aulaEvento) {
        // Previne erro se a lista estiver nula
        List<AlunoDTO> listaAlunos = (aulaEvento.getAlunosPart() != null) ?
                aulaEvento.getAlunosPart().stream()
                        .map(AlunoDTO::createAlunoDTO)
                        .toList() : List.of();

        List<MembroDTO> listaMembros = (aulaEvento.getMembrosPart() != null) ?
                aulaEvento.getMembrosPart().stream()
                        .map(MembroDTO::createMembro)
                        .toList() : List.of();

        return AulaEventoDTO.builder()
                // Converte o Long da entidade para String do DTO
                .id(String.valueOf(aulaEvento.getId()))
                .descricao(aulaEvento.getDescricao())
                .titulo(aulaEvento.getTitulo())
                .data(aulaEvento.getData())
                .alunos(listaAlunos)
                .membros(listaMembros)
                .build();
    }
}