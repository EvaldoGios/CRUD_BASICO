package br.com.senai.teste.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senai.teste.model.Emprestimo;

@Repository 
public interface EmprestimoRepository
        extends JpaRepository<Emprestimo, Integer> {

                boolean existsByLivroIdAndDataDevolucaoIsNull(Integer livroId);

                boolean existsByAlunoIdAndLivroIdAndDataDevolucaoIsNull
                (Integer alunoId, Integer livroId);


        List<Emprestimo> findByDataDevolucaoIsNull();
        List<Emprestimo> findByAlunoId(Integer alunoId);
        List<Emprestimo> findByLivroId(Integer livroId);
}
