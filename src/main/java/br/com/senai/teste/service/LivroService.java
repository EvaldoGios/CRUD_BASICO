package br.com.senai.teste.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import br.com.senai.teste.repository.LivroRepository;
import br.com.senai.teste.model.Livro;

@Service 
public class LivroService {
    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public Livro cadastrar(Livro livro) {
        return livroRepository.save(livro);
    }

    public List<Livro> listar() {
        return livroRepository.findAll();
    }

    public Optional<Livro> buscarPorId(Integer id) {
        return livroRepository.findById(id);
    }

    public Optional<Livro> atualizar(
        Integer id, Livro novosDados) {

        Optional<Livro> livroExistente = livroRepository.findById(id);

        if (livroExistente.isEmpty()) {
         return Optional.empty();   
        }

        Livro livro = livroExistente.get();

        livro.setTitulo(novosDados.getTitulo());
        livro.setAutor(novosDados.getAutor());
        livro.setAnoPublicacao(novosDados.getAnoPublicacao());

        return Optional.of(livroRepository.save(livro));
    }

    public boolean deletar(Integer id) {

        if (livroRepository.findById(id).isEmpty()) {
            return false;
        }

        livroRepository.deleteById(id);
        return true;
    }
}
