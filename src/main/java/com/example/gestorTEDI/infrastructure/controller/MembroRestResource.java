package com.example.gestorTEDI.infrastructure.controller;

import com.example.gestorTEDI.domain.entity.Membro;
import com.example.gestorTEDI.domain.service.MembroService;
import com.example.gestorTEDI.infrastructure.dtos.MembroDTO;
import com.example.gestorTEDI.infrastructure.dtos.SaveMembroDataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/membros")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class MembroRestResource {
    private final MembroService membroService;

    @PostMapping
    public ResponseEntity<MembroDTO> saveMembro(@RequestBody SaveMembroDataDTO saveMembroDataDTO) {
        Membro membro =membroService.createMembro(saveMembroDataDTO);
        return ResponseEntity.created(URI.create("/membros/" + membro.getRa()))
                .body(MembroDTO.createMembro(membro));
    }
}
