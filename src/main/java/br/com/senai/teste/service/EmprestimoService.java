package br.com.senai.teste.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import br.com.senai.teste.model.Aluno;
import br.com.senai.teste.model.Emprestimo;
import br.com.senai.teste.model.Livro;
import br.com.senai.teste.repository.AlunoRepository;
import br.com.senai.teste.repository.EmprestimoRepository;
import br.com.senai.teste.repository.LivroRepository;
import java.time.LocalDate;
import java.util.Optional; 
import java.util.List;
import org.springframework.web.server.ResponseStatusException;

@Service 
public class EmprestimoService {      
    
    private final EmprestimoRepository emprestimoRepository;
    private final AlunoRepository alunoRepository;
    private final LivroRepository livroRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository, AlunoRepository alunoRepository, LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.alunoRepository = alunoRepository;
        this.livroRepository = livroRepository;
    }

    public Optional<Emprestimo> cadastrar(
        Integer alunoId, 
        Integer livroId, 
        LocalDate dataDevolucaoPrevista) {
    
        Optional<Aluno> aluno = alunoRepository.findById(alunoId);
        Optional<Livro> livro = livroRepository.findById(livroId);

        if (aluno.isEmpty() || livro.isEmpty()) {
            return Optional.empty();
        }

        boolean livroEstaEmprestado = emprestimoRepository
        .existsByAlunoIdAndLivroIdAndDataDevolucaoIsNull(alunoId, livroId);


        if (livroEstaEmprestado) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "O Livro já está emprestado para este aluno e ainda não foi devolvido.");
        }

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setAluno(aluno.get());
        emprestimo.setLivro(livro.get());
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataDevolucaoPrevista(dataDevolucaoPrevista);

        return Optional.of(emprestimoRepository.save(emprestimo));
    }

    public List<Emprestimo> listar() {
        return emprestimoRepository.findAll();
    }

    public Optional<Emprestimo> buscarPorId(Integer id) {
        return emprestimoRepository.findById(id);
    }

    public Optional<Emprestimo> devolver(Integer id) {
        Optional<Emprestimo> encontrato = emprestimoRepository.findById(id);

        if (encontrato.isEmpty()) {
            return Optional.empty();
        }  

        Emprestimo emprestimo = encontrato.get();

        if (emprestimo.getDataDevolucao() == null) {
            emprestimo.setDataDevolucao(LocalDate.now());
            emprestimoRepository.save(emprestimo);
        }

        return Optional.of(emprestimo);
    }

    public List<Emprestimo> listarEmprestimosAtivos() {
        return emprestimoRepository.findByDataDevolucaoIsNull();
    }

    public List<Emprestimo> listarEmprestimosPorAluno(Integer alunoId) {
        return emprestimoRepository.findByAlunoId(alunoId);
    }

    public List<Emprestimo> listarEmprestimosPorLivro(Integer livroId) {
        return emprestimoRepository.findByLivroId(livroId);
    }

    public List<Emprestimo> listarEmprestimosAtrasados() {
        return emprestimoRepository
            .findByDataDevolucaoPrevistaBeforeAndDataDevolucaoIsNull(
                LocalDate.now());
    }

    
    public List<Emprestimo> buscarAtrasadosPorAluno(Integer alunoId) {
        return emprestimoRepository
            .findByAluno_IdAndDataDevolucaoPrevistaBeforeAndDataDevolucaoIsNull(
                alunoId,
                LocalDate.now()
            );
    }

    public Optional<Emprestimo> renovarEmprestimo(
        Integer iDRenovacao, LocalDate novaDataDevolucaoPrevista) {

        Optional<Emprestimo> emprestimoOptional = emprestimoRepository.findById(iDRenovacao);

        if (emprestimoOptional.isEmpty()) {
            return Optional.empty();
        }

        Emprestimo emprestimo = emprestimoOptional.get();

        if (emprestimo.getDataDevolucao() != null) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "O empréstimo já foi devolvido e não pode ser renovado."
            );
        }

        emprestimo.setDataDevolucaoPrevista(novaDataDevolucaoPrevista);
       

        return Optional.of(emprestimoRepository.save(emprestimo));
    }

}
