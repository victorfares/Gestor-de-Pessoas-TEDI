package com.example.gestorTEDI.infrastructure.controller;


import com.example.gestorTEDI.domain.entity.Aluno;
import com.example.gestorTEDI.domain.repository.AlunoRepository;
import com.example.gestorTEDI.domain.service.AlunoService;
import com.example.gestorTEDI.infrastructure.dtos.AlunoDTO;
import com.example.gestorTEDI.infrastructure.dtos.MembroDTO;
import com.example.gestorTEDI.infrastructure.dtos.SaveAlunoDataDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/alunos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AlunoRestResource {

    private final AlunoService alunoService;
    private final AlunoRepository alunoRepository;

    @PostMapping
    public ResponseEntity<AlunoDTO> saveAluno(@RequestBody @Valid SaveAlunoDataDTO saveAlunoDataDTO) {
        Aluno aluno = alunoService.createAluno(saveAlunoDataDTO);
        return ResponseEntity.created(URI.create("/alunos/" + aluno.getRg()))
                .body(AlunoDTO.createAlunoDTO(aluno));
    }

    @GetMapping("/{rg}")
    public ResponseEntity<AlunoDTO> loadAlunoByRg(@PathVariable("rg") String memberId) {
        Aluno aluno = alunoService.findByRg(memberId);
        return ResponseEntity.ok(AlunoDTO.createAlunoDTO(aluno));
    }

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> findAlunos(
            @RequestParam(value = "rg", required = false) String rg) {
        List<Aluno> alunos = alunoService.findAlunos(rg);

        return ResponseEntity.ok(
                alunos.stream().map(AlunoDTO::createAlunoDTO).toList()
        );
    }

    @PutMapping("/{rg}")
    public ResponseEntity<AlunoDTO> updateAluno(
            @PathVariable("rg") String alunoRg,
            @RequestBody @Valid SaveAlunoDataDTO saveAlunoData
    ) {
        Aluno aluno = alunoService.updateAluno(alunoRg, saveAlunoData);
        return ResponseEntity.ok(AlunoDTO.createAlunoDTO(aluno));

    }

    @DeleteMapping("/{rg}")
    public ResponseEntity<Void> deleteAluno(@PathVariable String rg) {
        alunoService.deleteAluno(rg);
        return ResponseEntity.noContent().build();
    }
}