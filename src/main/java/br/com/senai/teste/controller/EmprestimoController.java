package br.com.senai.teste.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.senai.teste.dto.EmprestimoRequest;
import br.com.senai.teste.model.Emprestimo;
import br.com.senai.teste.service.EmprestimoService;
import jakarta.validation.Valid;
import br.com.senai.teste.dto.RenovacaoRequest;

import java.util.Optional;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import java.time.LocalDate;


@RestController 
@RequestMapping ("/emprestimos")
public class EmprestimoController {
    
    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping 
    public ResponseEntity<Emprestimo> cadastrar(
        @Valid @RequestBody EmprestimoRequest dados) {

            Integer alunoId = dados.getAlunoId();
            Integer livroId = dados.getLivroId();

            LocalDate dataDevolucaoPrevista = dados.getDataDevolucaoPrevista();

            if (alunoId == null
                 || livroId == null
                 || dataDevolucaoPrevista == null) {

                return ResponseEntity.badRequest().build();
            }

            Optional<Emprestimo> emprestimo = emprestimoService.cadastrar(
                alunoId, 
                livroId, 
                dataDevolucaoPrevista);

            if (emprestimo.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(emprestimo.get());

    }

    @GetMapping
    public ResponseEntity<List<Emprestimo>> listar() {
        List<Emprestimo> emprestimos = emprestimoService.listar();
        return ResponseEntity.ok(emprestimos);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Emprestimo> buscarPorId(
        @PathVariable Integer id) {

            Optional<Emprestimo> emprestimo = emprestimoService.buscarPorId(id);

            if (emprestimo.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(emprestimo.get());
    }

    @PatchMapping ("/{id}/devolucao")
    public ResponseEntity<Emprestimo> devolver(
        @PathVariable Integer id) {

            Optional<Emprestimo> emprestimo = emprestimoService.devolver(id);

            if (emprestimo.isPresent()) {
                return ResponseEntity.ok(emprestimo.get());
            }

            return ResponseEntity.notFound().build();
    }

    @GetMapping ("/ativos")
    public ResponseEntity<List<Emprestimo>> listarEmprestimosAtivos() {
        List<Emprestimo> emprestimosAtivos = emprestimoService.listarEmprestimosAtivos();
        return ResponseEntity.ok(emprestimosAtivos);
    }

    @GetMapping ("/aluno/{alunoId}")
    public ResponseEntity<List<Emprestimo>> listarEmprestimosPorAluno(
        @PathVariable Integer alunoId) {
            List<Emprestimo> emprestimos = emprestimoService.listarEmprestimosPorAluno(alunoId);
            return ResponseEntity.ok(emprestimos);
    }

    @GetMapping ("/livro/{livroId}")
    public ResponseEntity<List<Emprestimo>> listarEmprestimosPorLivro(
        @PathVariable Integer livroId) {
            List<Emprestimo> emprestimos = emprestimoService.listarEmprestimosPorLivro(livroId);
            return ResponseEntity.ok(emprestimos);
    }

    @GetMapping ("/atrasados")
    public ResponseEntity<List<Emprestimo>> listarEmprestimosAtrasados() {
        List<Emprestimo> emprestimosAtrasados = emprestimoService.listarEmprestimosAtrasados();
        return ResponseEntity.ok(emprestimosAtrasados);
    }

    
    @GetMapping("/aluno/{id}/atrasados")
    public List<Emprestimo> buscarAtrasadosPorAluno(
            @PathVariable Integer id) {

        return emprestimoService.buscarAtrasadosPorAluno(id);
    }

    @PatchMapping("/{id}/renovacao")
    public ResponseEntity<Emprestimo> renovarEmprestimo(
        @PathVariable Integer id,
        @Valid @RequestBody RenovacaoRequest dados) {

            Optional<Emprestimo> emprestimo = emprestimoService.renovarEmprestimo(
                id, 
                dados.getDataDevolucaoPrevista());

            if (emprestimo.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(emprestimo.get());
    }

}
