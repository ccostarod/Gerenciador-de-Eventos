package view;

public class MainPrograma {
    public static void main(String[] args) {
        iniciarTelaDeInicio();
    }

    public static void iniciarTelaDeInicio() {
        java.awt.EventQueue.invokeLater(() -> {
            TelaDeInicio telaDeInicio = new TelaDeInicio();
            telaDeInicio.setVisible(true);
        });
    }
}

