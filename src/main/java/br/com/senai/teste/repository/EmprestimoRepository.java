package br.com.senai.teste.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.senai.teste.model.Emprestimo;

public interface EmprestimoRepository
        extends JpaRepository<Emprestimo, Integer> {

        List<Emprestimo> findByDataDevolucaoIsNull();
}
