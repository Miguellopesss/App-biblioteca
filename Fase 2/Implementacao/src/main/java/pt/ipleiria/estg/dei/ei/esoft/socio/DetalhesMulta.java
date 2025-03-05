package pt.ipleiria.estg.dei.ei.esoft.socio;

import pt.ipleiria.estg.dei.ei.esoft.Utilizador;
import pt.ipleiria.estg.dei.ei.esoft.multa.Multa;

import javax.swing.*;
import java.awt.*;

public class DetalhesMulta {

    private Socio socio;
    private Multa multa;
    private Utilizador utilizador;

    public DetalhesMulta(Socio socio, Multa multa, Utilizador utilizador) {
        this.socio = socio;
        this.multa = multa;
        this.utilizador = utilizador;
    }

    public void abrirPaginaDetalhesMulta() {
        JFrame frame = new JFrame("Biblioteca Patos do Liz - Detalhes da Multa");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1100, 900);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Número Sócio:"), gbc);
        gbc.gridx = 1;
        JTextField numeroSocioField = new JTextField(String.valueOf(socio.getNumeroSocio()), 20);
        numeroSocioField.setEditable(false);
        panel.add(numeroSocioField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        JTextField nomeField = new JTextField(socio.getNome(), 20);
        nomeField.setEditable(false);
        panel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Título do Livro:"), gbc);
        gbc.gridx = 1;
        JTextField tituloLivroField = new JTextField(multa.getLivro().getTitulo(), 20);
        tituloLivroField.setEditable(false);
        panel.add(tituloLivroField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Código do Livro:"), gbc);
        gbc.gridx = 1;
        JTextField codigoLivroField = new JTextField(String.valueOf(multa.getLivro().getCodigo()), 20);
        codigoLivroField.setEditable(false);
        panel.add(codigoLivroField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Data Prevista Devolução:"), gbc);
        gbc.gridx = 1;
        JTextField dataPrevistaDevolucaoField = new JTextField(multa.getDevolucao().getEmprestimo().getDataPrevistaDevolucao().toString(), 20);
        dataPrevistaDevolucaoField.setEditable(false);
        panel.add(dataPrevistaDevolucaoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Data de Devolução:"), gbc);
        gbc.gridx = 1;
        JTextField dataDevolucaoField = new JTextField(multa.getDevolucao().getDataDevolucao().toString(), 20);
        dataDevolucaoField.setEditable(false);
        panel.add(dataDevolucaoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Número da Multa:"), gbc);
        gbc.gridx = 1;
        JTextField numeroMultaField = new JTextField(String.valueOf(multa.getNumeroMulta()), 20);
        numeroMultaField.setEditable(false);
        panel.add(numeroMultaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("Valor da Multa:"), gbc);
        gbc.gridx = 1;
        JTextField valorMultaField = new JTextField(String.valueOf(multa.getValorMulta()), 20);
        valorMultaField.setEditable(false);
        panel.add(valorMultaField, gbc);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Voltar");
        JButton pagarButton = new JButton("Pagar Multa");

        backButton.addActionListener(e -> {
            ListaSocioMulta listaSocioMulta = new ListaSocioMulta(utilizador);
            listaSocioMulta.abrirPaginaListaSocioMulta();
            frame.dispose();
        });

        pagarButton.addActionListener(e -> {
            socio.removeMulta(multa);
            JOptionPane.showMessageDialog(frame, "Multa paga com sucesso.");
            ListaSocioMulta listaSocioMulta = new ListaSocioMulta(utilizador);
            listaSocioMulta.abrirPaginaListaSocioMulta();
            frame.dispose();
        });

        buttonsPanel.add(backButton);
        buttonsPanel.add(pagarButton);
        panel.add(buttonsPanel, gbc);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonsPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}