package com.example.gestorTEDI.infrastructure.controller;

import com.example.gestorTEDI.domain.entity.Aluno;
import com.example.gestorTEDI.domain.model.NivelDigital;
import com.example.gestorTEDI.domain.repository.AlunoRepository;
import com.example.gestorTEDI.domain.service.AlunoService;
import com.example.gestorTEDI.infrastructure.dtos.SaveAlunoDataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web/alunos")
@RequiredArgsConstructor
public class AlunoWebController {

    private final AlunoService alunoService;
    private final AlunoRepository alunoRepository;

    // 1. LISTAR E BUSCAR
    @GetMapping
    public String getAlunosPage(Model model, @RequestParam(name = "query", required = false, defaultValue = "") String query) {
        List<Aluno> alunos;
        if (query.isBlank()) {
            alunos = alunoRepository.findAll();
        } else {
            alunos = alunoRepository.findByNomeContainingIgnoreCaseOrRgContainingIgnoreCase(query, query);
        }
        model.addAttribute("listaDeAlunos", alunos);
        return "lista-alunos";
    }

    // 2. FORMULÁRIO DE CRIAÇÃO
    @GetMapping("/novo")
    public String getFormNovoAluno(Model model) {
        model.addAttribute("alunoDTO", new SaveAlunoDataDTO(null, null, null, null, null));
        model.addAttribute("niveisDigitais", NivelDigital.values());
        model.addAttribute("isEdit", false);
        return "form-aluno";
    }

    // 3. SALVAR NOVO
    @PostMapping("/salvar")
    public String salvarNovoAluno(SaveAlunoDataDTO novoAluno) {
        alunoService.createAluno(novoAluno);
        return "redirect:/web/alunos";
    }

    // --- NOVOS MÉTODOS PARA EDIÇÃO ---

    // 4. FORMULÁRIO DE EDIÇÃO
    @GetMapping("/editar/{rg}")
    public String getFormEditarAluno(@PathVariable("rg") String rg, Model model) {
        Aluno aluno = alunoService.findByRg(rg); // Você já tem esse método no service (ou loadAluno)

        // Converte Entidade -> DTO
        SaveAlunoDataDTO dto = new SaveAlunoDataDTO(
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getTelefone(),
                aluno.getRg(),
                aluno.getNivelDigital()
        );

        model.addAttribute("alunoDTO", dto);
        model.addAttribute("niveisDigitais", NivelDigital.values());
        model.addAttribute("isEdit", true);
        return "form-aluno";
    }

    // 5. SALVAR EDIÇÃO
    @PostMapping("/editar/{rg}")
    public String atualizarAluno(@PathVariable("rg") String rg, SaveAlunoDataDTO dadosAtualizados) {
        alunoService.updateAluno(rg, dadosAtualizados); // Certifique-se de ter este método no AlunoService
        return "redirect:/web/alunos";
    }
}