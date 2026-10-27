    package br.com.senai.teste.dto;
    
    import java.time.LocalDate;
    import jakarta.validation.constraints.NotNull;
    import jakarta.validation.constraints.Future;
    
    public class RenovacaoRequest {
        
        @NotNull(message = "A data de devolução prevista é obrigatória")
        @Future(message = "A data de devolução prevista deve ser uma data futura")
        private LocalDate dataDevolucaoPrevista;

        public RenovacaoRequest() {
        }

        public LocalDate getDataDevolucaoPrevista() {
            return dataDevolucaoPrevista;
        }

        public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
            this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        }

    }