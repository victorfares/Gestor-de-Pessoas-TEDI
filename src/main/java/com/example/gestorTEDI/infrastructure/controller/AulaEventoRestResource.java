package com.example.gestorTEDI.infrastructure.controller;

import com.example.gestorTEDI.domain.entity.AulaEvento;
import com.example.gestorTEDI.domain.service.AulaEventoService;
import com.example.gestorTEDI.infrastructure.dtos.AulaEventoDTO;
import com.example.gestorTEDI.infrastructure.dtos.MembroDTO;
import com.example.gestorTEDI.infrastructure.dtos.SaveAulaEventoDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/aulas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AulaEventoRestResource {
    private final AulaEventoService aulaEventoService;

    @PostMapping
    public ResponseEntity<AulaEventoDTO> saveAula(@RequestBody @Valid SaveAulaEventoDTO dto) {
        AulaEvento aula = aulaEventoService.createAulaEvento(dto);

        return ResponseEntity.created(URI.create("/api/aulas/" + aula.getId()))
                .body(AulaEventoDTO.create(aula));
    }

    @GetMapping
    public ResponseEntity<List<AulaEventoDTO>> listAulas() {
        List<AulaEvento> aulas = aulaEventoService.findAll();
        return ResponseEntity.ok(
                aulas.stream().map(AulaEventoDTO::create).toList()
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<AulaEventoDTO> getAulaById(@PathVariable Long id) {
        AulaEvento aula = aulaEventoService.loadAula(id);
        return ResponseEntity.ok(AulaEventoDTO.create(aula));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAula(@PathVariable Long id) {
        aulaEventoService.deleteAula(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{id}/alunos/{rg}")
    public ResponseEntity<AulaEventoDTO> addAlunoToAula(@PathVariable Long id, @PathVariable String rg) {
        AulaEvento aulaAtualizada = aulaEventoService.associarAluno(id, rg);
        return ResponseEntity.ok(AulaEventoDTO.create(aulaAtualizada));
    }
    @PostMapping("/{id}/membros/{ra}")
    public ResponseEntity<AulaEventoDTO> addMembroToAula(@PathVariable Long id, @PathVariable String ra) {
        AulaEvento aulaAtualizada = aulaEventoService.associarMembro(id, ra);
        return ResponseEntity.ok(AulaEventoDTO.create(aulaAtualizada));
    }
    @PutMapping("/{id}")
    public ResponseEntity<AulaEventoDTO> updateAula(@PathVariable Long id,
                                                    @RequestBody @Valid SaveAulaEventoDTO dto) {

        AulaEvento aulaAtualizada = aulaEventoService.updateAula(id, dto);
        return ResponseEntity.ok(AulaEventoDTO.create(aulaAtualizada));
    }
}
