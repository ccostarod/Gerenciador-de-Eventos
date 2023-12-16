package model;

import java.time.LocalDate;

public class Eventual extends Solicitacao{
    private String finalidade;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    public Eventual(int ano, String semestre, String curso, String finalidade, int vagas, String horario, LocalDate dataInicio, LocalDate dataFim) {
        super(ano, semestre, curso, horario, vagas);
        this.finalidade = finalidade;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }
}
