package com.example.gestorTEDI.infrastructure.controller;

import com.example.gestorTEDI.domain.entity.Aluno;
import com.example.gestorTEDI.domain.model.NivelDigital; // Importe o Enum
import com.example.gestorTEDI.domain.repository.AlunoRepository;
import com.example.gestorTEDI.domain.service.AlunoService;
import com.example.gestorTEDI.infrastructure.dtos.SaveAlunoDataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/web/alunos") // <-- Prefixo para todas as rotas de Aluno
@RequiredArgsConstructor
public class AlunoWebController {

    private final AlunoService alunoService;
    private final AlunoRepository alunoRepository;

    @GetMapping
    public String getAlunosPage(Model model,
                                @RequestParam(name = "query", required = false, defaultValue = "") String query) {
        List<Aluno> alunos;
        if (query.isBlank()) {
            alunos = alunoRepository.findAll();
        } else {
            // Usa o método de busca por Nome ou RG
            alunos = alunoRepository.findByNomeContainingIgnoreCaseOrRgContainingIgnoreCase(query, query);
        }
        model.addAttribute("listaDeAlunos", alunos);
        return "lista-alunos"; // Referencia /resources/templates/lista-alunos.html
    }

    /**
     * GET /web/alunos/novo
     * Mostra o formulário para criar um novo aluno.
     */
    @GetMapping("/novo")
    public String getFormNovoAluno(Model model) {

        // ---- A CORREÇÃO ESTÁ AQUI ----
        // Chamamos o construtor com 5 argumentos (null)
        model.addAttribute("novoAluno", new SaveAlunoDataDTO(null, null, null, null, null));

        model.addAttribute("niveisDigitais", NivelDigital.values());
        return "form-aluno";
    }

    /**
     * POST /web/alunos/salvar
     * Recebe os dados do formulário e salva o novo aluno.
     */
    @PostMapping("/salvar")
    public String salvarNovoAluno(SaveAlunoDataDTO novoAluno) {
        // Este método já funciona perfeitamente com DTOs imutáveis!
        alunoService.createAluno(novoAluno);

        return "redirect:/web/alunos";
    }
}