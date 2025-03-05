package pt.ipleiria.estg.dei.ei.esoft.devolucao;

import pt.ipleiria.estg.dei.ei.esoft.PaginaPrincipal;
import pt.ipleiria.estg.dei.ei.esoft.Utilizador;
import pt.ipleiria.estg.dei.ei.esoft.emprestimo.Emprestimo;
import pt.ipleiria.estg.dei.ei.esoft.socio.Socio;
import pt.ipleiria.estg.dei.ei.esoft.data.Data;

import javax.swing.*;
import java.awt.*;

public class DetalhesDevolucao {

    private Utilizador utilizador;
    private Socio socio;

    public DetalhesDevolucao(Utilizador utilizador, Socio socio) {
        this.utilizador = utilizador;
        this.socio = socio;
    }

    public void abrirDetalhesDevolucao(Emprestimo emprestimo) {

        JFrame detalhesFrame = new JFrame("Detalhes do Empréstimo");
        detalhesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detalhesFrame.setSize(1100, 900);

        JPanel detalhesPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        detalhesPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        detalhesPanel.add(new JLabel("Número de sócio:"));
        JTextField socioField = new JTextField(String.valueOf(socio.getNumeroSocio()));
        socioField.setEditable(false);
        detalhesPanel.add(socioField);

        detalhesPanel.add(new JLabel("Título:"));
        JTextField tituloField = new JTextField(emprestimo.getLivro().getTitulo());
        tituloField.setEditable(false);
        detalhesPanel.add(tituloField);

        detalhesPanel.add(new JLabel("Autor:"));
        JTextField autorField = new JTextField(emprestimo.getLivro().getAutor());
        autorField.setEditable(false);
        detalhesPanel.add(autorField);

        detalhesPanel.add(new JLabel("Data de Devolução Prevista:"));
        JTextField dataDevolucaoPrevistaField = new JTextField(emprestimo.getDataPrevistaDevolucao().toString());
        dataDevolucaoPrevistaField.setEditable(false);
        detalhesPanel.add(dataDevolucaoPrevistaField);

        detalhesPanel.add(new JLabel("Data de Devolução:"));
        JTextField dataDevolucaoField = new JTextField(Data.today().toString());
        dataDevolucaoField.setEditable(false);
        detalhesPanel.add(dataDevolucaoField);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Voltar");
        JButton registerReturnButton = new JButton("Registar Devolução");

        backButton.addActionListener(e -> {
            EmprestimosSocio emprestimosSocio = new EmprestimosSocio(utilizador, socio);
            emprestimosSocio.abrirPaginaEmprestimosSocio();
            detalhesFrame.dispose();
        });
        registerReturnButton.addActionListener(e -> {
            Devolucao devolucao = new Devolucao(socio, emprestimo.getLivro(), emprestimo);
            JOptionPane.showMessageDialog(detalhesFrame, "Devolução registada com sucesso.");

            PaginaPrincipal paginaPrincipal = new PaginaPrincipal(utilizador);
            paginaPrincipal.abrirPaginaPrincipal();
            detalhesFrame.dispose();
        });

        buttonsPanel.add(backButton);
        buttonsPanel.add(registerReturnButton);
        detalhesPanel.add(buttonsPanel);

        detalhesFrame.add(detalhesPanel, BorderLayout.CENTER);
        detalhesFrame.add(buttonsPanel, BorderLayout.SOUTH);
        detalhesFrame.setVisible(true);
    }
}
