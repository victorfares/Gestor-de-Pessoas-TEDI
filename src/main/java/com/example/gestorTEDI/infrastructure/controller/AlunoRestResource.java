package com.example.gestorTEDI.infrastructure.controller;


import com.example.gestorTEDI.domain.entity.Aluno;
import com.example.gestorTEDI.domain.repository.AlunoRepository;
import com.example.gestorTEDI.domain.service.AlunoService;
import com.example.gestorTEDI.infrastructure.dtos.AlunoDTO;
import com.example.gestorTEDI.infrastructure.dtos.MembroDTO;
import com.example.gestorTEDI.infrastructure.dtos.SaveAlunoDataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoRestResource {

    private final AlunoService alunoService;
    private final AlunoRepository alunoRepository;

    @PostMapping
    public ResponseEntity<AlunoDTO> saveAluno(@RequestBody SaveAlunoDataDTO saveAlunoDataDTO){
        Aluno aluno = alunoService.createAluno(saveAlunoDataDTO);
        return ResponseEntity.created(URI.create("/alunos/" + aluno.getRg()))
                .body(AlunoDTO.createAlunoDTO(aluno));
    }

    @GetMapping("/{rg}")
    public ResponseEntity<AlunoDTO> loadAlunoByRg(@PathVariable("rg") String memberId){
        Aluno aluno = alunoService.findByRg(memberId);
        return ResponseEntity.ok(AlunoDTO.createAlunoDTO(aluno));
    }
}
