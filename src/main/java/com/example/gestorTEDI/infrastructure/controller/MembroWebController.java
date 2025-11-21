package com.example.gestorTEDI.infrastructure.controller;

import com.example.gestorTEDI.domain.entity.Membro;
import com.example.gestorTEDI.domain.repository.MembroRepository;
import com.example.gestorTEDI.domain.service.MembroService;
import com.example.gestorTEDI.infrastructure.dtos.SaveMembroDataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web/membros")
@RequiredArgsConstructor
public class MembroWebController {

    private final MembroService membroService;
    private final MembroRepository membroRepository;

    @GetMapping
    public String getMembrosPage(Model model, @RequestParam(name = "query", required = false, defaultValue = "") String query) {
        List<Membro> membros;
        if (query.isBlank()) {
            membros = membroRepository.findAll();
        } else {
            membros = membroRepository.findByNomeContainingIgnoreCaseOrRaContainingIgnoreCase(query, query);
        }
        model.addAttribute("listaDeMembros", membros);
        return "lista-membros";
    }

    @GetMapping("/novo")
    public String getFormNovoMembro(Model model) {
        model.addAttribute("membroDTO", new SaveMembroDataDTO(null, null, null, null, null, null));
        model.addAttribute("isEdit", false);
        return "form-membro";
    }

    @PostMapping("/salvar")
    public String salvarNovoMembro(SaveMembroDataDTO novoMembro) {
        membroService.createMembro(novoMembro);
        return "redirect:/web/membros";
    }

    @GetMapping("/editar/{ra}")
    public String getFormEditarMembro(@PathVariable("ra") String ra, Model model) {
        // Busca o membro existente
        Membro membro = membroService.loadMembro(ra);
        SaveMembroDataDTO dto = new SaveMembroDataDTO(
                membro.getNome(),
                membro.getEmail(),
                membro.getTelefone(),
                membro.getRa(),
                membro.getDepartamento(),
                membro.getFuncao()
        );

        model.addAttribute("membroDTO", dto);
        model.addAttribute("isEdit", true);
        return "form-membro";
    }

    @PostMapping("/editar/{ra}")
    public String atualizarMembro(@PathVariable("ra") String ra, SaveMembroDataDTO dadosAtualizados) {
        membroService.updateMembro(ra, dadosAtualizados);
        return "redirect:/web/membros";
    }

    @GetMapping("/deletar/{ra}")
    public String deletarMembro(@PathVariable("ra") String ra) {
        membroService.deleteMembro(ra);
        return "redirect:/web/membros";
    }
}