package com.example.gestorTEDI.infrastructure.controller;

import com.example.gestorTEDI.domain.entity.Membro;
import com.example.gestorTEDI.domain.repository.MembroRepository;
import com.example.gestorTEDI.domain.service.MembroService;
import com.example.gestorTEDI.infrastructure.dtos.MembroDTO;
import com.example.gestorTEDI.infrastructure.dtos.SaveMembroDataDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/membros")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class MembroRestResource {
    private final MembroService membroService;
    private final MembroRepository membroRepository;

    @PostMapping
    public ResponseEntity<MembroDTO> saveMembro(@RequestBody SaveMembroDataDTO saveMembroDataDTO) {
        Membro membro =membroService.createMembro(saveMembroDataDTO);
        return ResponseEntity.created(URI.create("/membros/" + membro.getRa()))
                .body(MembroDTO.createMembro(membro));
    }

    @GetMapping("/view")
    public String getMembrosPage(Model model,
                                 // Pega o parâmetro "?query=" da URL
                                 // defaultValue = "" garante que não dê erro se vier vazio
                                 @RequestParam(name = "query", required = false, defaultValue = "") String query) {

        List<Membro> membros;

        if (query.isBlank()) {
            // Se a busca estiver vazia, lista todos
            membros = membroRepository.findAll();
        } else {
            // Se tiver algo na busca, filtra pelo repositório
            membros = membroRepository.findByNomeContainingIgnoreCaseOrRaContainingIgnoreCase(query, query);
        }

        model.addAttribute("listaDeMembros", membros);
        return "lista-membros"; // Renderiza /resources/templates/lista-membros.html
    }

    @PutMapping("/{ra}")
    public ResponseEntity<MembroDTO> updateMembro(@PathVariable String ra,
                                                  @RequestBody @Valid SaveMembroDataDTO dto) {

        Membro membroAtualizado = membroService.updateMembro(ra, dto);

        return ResponseEntity.ok(MembroDTO.createMembro(membroAtualizado));
    }
    @DeleteMapping("/{ra}")
    public ResponseEntity<Void> deleteMembro(@PathVariable String ra) {
        membroService.deleteMembro(ra);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<MembroDTO>> getAllMembros() {
        List<Membro> membros = membroService.getAllMembros();
        return ResponseEntity.ok(
                membros.stream().map(MembroDTO::createMembro).toList()
        );
    }

    @GetMapping("/{ra}")
    public ResponseEntity<MembroDTO> getMembroByRa(@PathVariable String ra) {
        Membro membro = membroService.loadMembro(ra);
        return ResponseEntity.ok(MembroDTO.createMembro(membro));
    }
}

