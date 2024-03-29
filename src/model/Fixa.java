package model;


import java.io.Serializable;

public class Fixa extends Solicitacao{
    private String disciplina;
    public Fixa(int ano, String semestre, String curso, String disciplina,int vagas, String horario) {
        super(ano, semestre, curso, horario, vagas);
        this.disciplina = disciplina;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String toStringArquivo(){
        return "fixa;" + ano + ";" + semestre + ";" + curso + ";" + disciplina + ";" + vagas + ";" + horario;
    }
}
