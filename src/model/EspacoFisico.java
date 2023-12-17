package model;

import javax.xml.transform.SourceLocator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class EspacoFisico implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String nome;
    protected int capacidade;
    protected List<Solicitacao> eventosAlocados;

    public EspacoFisico(String nome, int capacidade){
        this.nome = nome;
        this.capacidade = capacidade;
        eventosAlocados = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public List<Solicitacao> getEventosAlocados() {
        return eventosAlocados;
    }

    public void setEventosAlocados(List<Solicitacao> eventosAlocados) {
        this.eventosAlocados = eventosAlocados;
    }

    public void addEvento(Solicitacao solicitacao) {
        eventosAlocados.add(solicitacao);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EspacoFisico that = (EspacoFisico) o;
        return capacidade == that.capacidade &&
                Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, capacidade);
    }


    @Override
    public String toString() {
        return "EspacoFisico{" +
                "nome='" + nome + '\'' +
                ", capacidade=" + capacidade + '}';
    }

    public String toStringArquivo(){
        return nome + ";" + capacidade;
    }
}
