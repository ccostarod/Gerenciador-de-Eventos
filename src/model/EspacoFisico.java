package model;

import javax.xml.transform.SourceLocator;
import java.util.ArrayList;
import java.util.List;

public abstract class EspacoFisico {
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

    public void addEvento(Solicitacao solicitacao) {
        eventosAlocados.add(solicitacao);
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
