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

    public String[] getDia() {
        String[] separado = horario.split("");
        int i = 0;
        String resultado = null;

        while (i < separado.length && (!separado[i].equalsIgnoreCase("M") && !separado[i].equalsIgnoreCase("T") && !separado[i].equalsIgnoreCase("N"))) {
            if (resultado == null) {
                resultado = separado[i];
            } else {
                resultado = resultado + separado[i];
            }
            i++;
        }

        assert resultado != null;
        return resultado.split("");
    }

    public String getTurno() {
        String[] separado = horario.split("");
        int i = 0;

        // Adiciona verificação para evitar índice fora dos limites
        while (i < separado.length && (!separado[i].equals("M") && !separado[i].equals("T") && !separado[i].equals("N"))) {
            i++;
        }
        // Verifica se i está dentro dos limites antes de retornar
        if (i < separado.length) {
            return separado[i];
        }
        return null;
    }

    public String[] getHorarios(){
        String[] separado = horario.split("");
        int i = 0;
        while (i < separado.length && (!separado[i].equals("M") && !separado[i].equals("T") && !separado[i].equals("N"))) {
            i++;
        }
        String resultado = null;
        if (i < separado.length){
            for (int j = i+1; j < separado.length; j++){
                if (resultado == null) {
                    resultado = separado[j];
                } else {
                    resultado = resultado + separado[j];
                }
            }
            assert resultado != null;
            return resultado.split("");
        }
        return null;
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
