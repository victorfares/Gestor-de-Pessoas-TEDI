package com.example.gestorTEDI.infrastructure.controller;

import com.example.gestorTEDI.domain.entity.AulaEvento;
import com.example.gestorTEDI.domain.service.AulaEventoService;
import com.example.gestorTEDI.infrastructure.dtos.SaveAulaEventoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/web/aulas")
@RequiredArgsConstructor
public class AulaEventoWebController {

    private final AulaEventoService aulaEventoService;


    @GetMapping
    public String listAulas(Model model) {
        List<AulaEvento> aulas = aulaEventoService.findAll();
        model.addAttribute("listaAulas", aulas);
        return "lista-aulas";
    }


    @GetMapping("/novo")
    public String formNovaAula(Model model) {
        // Inicializa com listas vazias para evitar NullPointer no DTO
        model.addAttribute("aulaDTO", new SaveAulaEventoDTO(null, null, null, Collections.emptyList(), Collections.emptyList()));
        return "form-aula";
    }

    @GetMapping("/editar/{id}")
    public String formEditarAula(@PathVariable Long id, Model model) {
        AulaEvento aula = aulaEventoService.loadAula(id);

        // Prepara o DTO para a tela
        SaveAulaEventoDTO dto = new SaveAulaEventoDTO(
                aula.getTitulo(),
                aula.getDescricao(),
                aula.getData(),
                null, // Listas null para não alterar participantes
                null
        );

        model.addAttribute("aulaDTO", dto);
        model.addAttribute("aulaId", id); // Importante para a URL do form
        model.addAttribute("isEdit", true);
        return "form-aula";
    }

    @PostMapping("/salvar")
    public String salvarAula(SaveAulaEventoDTO dto) {
        aulaEventoService.createAulaEvento(dto);
        return "redirect:/web/aulas";
    }


    @GetMapping("/{id}")
    public String detalhesAula(@PathVariable Long id, Model model) {
        AulaEvento aula = aulaEventoService.loadAula(id);
        model.addAttribute("aula", aula);
        return "detalhes-aula";
    }

    @PostMapping("/{id}/adicionar-aluno")
    public String adicionarAluno(@PathVariable Long id, @RequestParam("rgAluno") String rg) {
        aulaEventoService.associarAluno(id, rg);
        return "redirect:/web/aulas/" + id;
    }

    @PostMapping("/{id}/adicionar-membro")
    public String adicionarMembro(@PathVariable Long id, @RequestParam("raMembro") String ra) {
        aulaEventoService.associarMembro(id, ra);
        return "redirect:/web/aulas/" + id;
    }

    @GetMapping("/deletar/{id}")
    public String deletarAula(@PathVariable Long id) {
        aulaEventoService.deleteAula(id);
        return "redirect:/web/aulas";
    }

    @PostMapping("/editar/{id}")
    public String atualizarAula(@PathVariable Long id, SaveAulaEventoDTO dto) {
        aulaEventoService.updateAula(id, dto);
        return "redirect:/web/aulas";
    }
}