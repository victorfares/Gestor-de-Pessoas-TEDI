package com.example.gestorTEDI.infrastructure.controller;

import com.example.gestorTEDI.domain.entity.Membro;
import com.example.gestorTEDI.domain.repository.MembroRepository;
import com.example.gestorTEDI.domain.service.MembroService;
import com.example.gestorTEDI.infrastructure.dtos.SaveMembroDataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/web")
@RequiredArgsConstructor

public class MembroWebController {

    private final MembroService membroService;
    private final MembroRepository membroRepository;

    @GetMapping("/membros")
    public String getMembrosPage(Model model,
                                 // 1. Esta anotação captura o "?query=..." da URL
                                 @RequestParam(name = "query", required = false, defaultValue = "") String query) {

        List<Membro> membros; // 2. Declare a lista

        if (query.isBlank()) {
            // 3. Se a busca (query) estiver vazia, busca todos
            membros = membroRepository.findAll();
        } else {
            // 4. Se a busca NÃO estiver vazia, usa o novo método do repositório
            membros = membroRepository.findByNomeContainingIgnoreCaseOrRaContainingIgnoreCase(query, query);
        }

        model.addAttribute("listaDeMembros", membros);
        return "lista-membros"; // Renderiza /resources/templates/lista-membros.html
    }

    @GetMapping("/membros/novo")
    public String getFormNovoMembro(Model model) {
        // Envia um objeto DTO vazio para o Thymeleaf
        // O Thymeleaf vai usar este objeto para "amarrar" (bind) os campos do form
        model.addAttribute("novoMembro", new SaveMembroDataDTO(null, null, null, null, null, null));
        return "form-membro"; // Renderiza /resources/templates/form-membro.html
    }

    @PostMapping("/membros/salvar")
    public String salvarNovoMembro(SaveMembroDataDTO novoMembro) {
        // O Spring/Thymeleaf magicamente pega os dados do form
        // e preenche o objeto 'novoMembro' para você.

        // Usa o Service que já criamos para salvar no banco
        membroService.createMembro(novoMembro);

        // Redireciona o usuário de volta para a página de listagem
        return "redirect:/web/membros";
    }
}
