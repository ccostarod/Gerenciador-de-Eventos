package model;

public abstract class Solicitacao {
    protected int ano;
    protected String semestre;
    protected String curso;
    protected String horario;
    protected int vagas;

    public Solicitacao(int ano, String semestre, String curso, String horario, int vagas) {
        this.ano = ano;
        this.semestre = semestre;
        this.curso = curso;
        this.horario = horario;
        this.vagas = vagas;
    }

    public int getAno() {
        return ano;
    }

    public String getSemestre() {
        return semestre;
    }

    public String getCurso() {
        return curso;
    }

    public String getHorario() {
        return horario;
    }

    public int getVagas() {
        return vagas;
    }

    @Override
    public String toString() {
        return "Solicitacao{" +
                "ano=" + ano +
                ", semestre='" + semestre + '\'' +
                ", curso='" + curso + '\'' +
                ", horario='" + horario + '\'' +
                ", vagas=" + vagas +
                '}';
    }
}
