package com.example.gestorTEDI.domain.service;

import com.example.gestorTEDI.domain.entity.Membro;
import com.example.gestorTEDI.domain.repository.MembroRepository;
import com.example.gestorTEDI.infrastructure.dtos.SaveMembroDataDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j

public class MembroService {

    private final MembroRepository membroRepository;

    @Transactional
    public Membro createMembro(SaveMembroDataDTO saveMembroData){
        Membro membro = Membro.builder()
                .nome(saveMembroData.getNomeMembro())
                .email(saveMembroData.getEmailMembro())
                .ra(saveMembroData.getRaMembro())
                .departamento(saveMembroData.getDepartamentoMembro())
                .funcao(saveMembroData.getFuncaoMembro())
                .telefone(saveMembroData.getTelefoneMembro())
                .build();
        membroRepository.save(membro);
        return membro;
    }
}
