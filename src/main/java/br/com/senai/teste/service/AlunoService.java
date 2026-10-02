package br.com.senai.teste.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import br.com.senai.teste.model.Aluno;
import br.com.senai.teste.repository.AlunoRepository;

@Service 
public class AlunoService {
    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public Aluno cadastrar(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public List<Aluno> listar() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarPorId(Integer id) {
        return alunoRepository.findById(id);
    }

    public Optional<Aluno> atualizar(
        Integer id, Aluno novosDados) {

        Optional<Aluno> alunoExistente = alunoRepository.findById(id);

        if (alunoExistente.isEmpty()) {
         return Optional.empty();   
        }

        Aluno aluno = alunoExistente.get();

        aluno.setNome(novosDados.getNome());
        aluno.setEmail(novosDados.getEmail());

        return Optional.of(alunoRepository.save(aluno));
    }

    public boolean deletar(Integer id) {

        if (alunoRepository.findById(id).isEmpty()) {
            return false;
        }

        alunoRepository.deleteById(id);
        return true;
    }
}
