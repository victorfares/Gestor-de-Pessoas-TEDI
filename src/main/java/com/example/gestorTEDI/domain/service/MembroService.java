package com.example.gestorTEDI.domain.service;

import com.example.gestorTEDI.domain.entity.AulaEvento;
import com.example.gestorTEDI.domain.entity.Membro;
import com.example.gestorTEDI.domain.exception.MembroNotFoundException;
import com.example.gestorTEDI.domain.repository.MembroRepository;
import com.example.gestorTEDI.infrastructure.dtos.SaveMembroDataDTO;
import com.example.gestorTEDI.infrastructure.exception.RequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Membro loadMembro(String ra) {
        return membroRepository.findByRa(ra)
                .orElseThrow(() -> new MembroNotFoundException("Membro não encontrado com RA: " + ra));
    }
    private boolean existeMembroNome(String nome) {
        return membroRepository.findByNome(nome).isPresent();
    }
    @Transactional
    public Membro updateMembro(String ra, SaveMembroDataDTO saveMembroData) {
        Membro membroExistentePorNome = membroRepository.findByNome(saveMembroData.getNomeMembro()).orElse(null);
        if (membroExistentePorNome != null && !membroExistentePorNome.getRa().equals(ra)) {
            throw new RequestException("Já existe um membro cadastrado com este nome.", "membro.nome.duplicate");
        }
        Membro membro = loadMembro(ra);
        membro.setNome(saveMembroData.getNomeMembro());
        membro.setEmail(saveMembroData.getEmailMembro());
        membro.setTelefone(saveMembroData.getTelefoneMembro());
        membro.setRa(saveMembroData.getRaMembro());
        membro.setDepartamento(saveMembroData.getDepartamentoMembro());
        membro.setFuncao(saveMembroData.getFuncaoMembro());
        return membro;
    }
    @Transactional
    public void deleteMembro(String ra) {
        Membro membro = membroRepository.findByRa(ra)
                .orElseThrow(() -> new MembroNotFoundException("Não é possível deletar. Membro não encontrado com RA: " + ra));

        // 2. Remove o membro das listas de presença das aulas
        if (membro.getAulasEventos() != null) {
            for (AulaEvento aula : membro.getAulasEventos()) {
                aula.getMembrosPart().remove(membro);
            }
        }
        membroRepository.deleteById(ra);
        log.info("### Membro com RA {} deletado com sucesso ###", ra);
    }

    public List<Membro> getAllMembros() {
        return membroRepository.findAll();
    }
}
