package model;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Eventual extends Solicitacao {
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

    public String toStringArquivo(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "eventual;" +ano + ";" + semestre + ";" + curso + ";" + finalidade + ";" + vagas + ";" + horario + ";" + dataInicio.format(formatter) + ";" + dataFim.format(formatter);
    }
}
