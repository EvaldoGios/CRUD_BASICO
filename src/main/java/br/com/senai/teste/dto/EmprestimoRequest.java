package br.com.senai.teste.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public class EmprestimoRequest {
    
    @NotNull (message = "O ID do aluno é obrigatório.")
    private Integer alunoId;

    @NotNull (message = "O ID do livro é obrigatório.")
    private Integer livroId;

    @NotNull (message = "A data de devolução prevista é obrigatória.")
    @Future (message = "A data de devolução prevista deve ser uma data futura.")
    private LocalDate dataDevolucaoPrevista;

    public EmprestimoRequest() {
    }

    public Integer getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Integer alunoId) {
        this.alunoId = alunoId;
    }

    public Integer getLivroId() {
        return livroId;
    }

    public void setLivroId(Integer livroId) {
        this.livroId = livroId;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }
}
