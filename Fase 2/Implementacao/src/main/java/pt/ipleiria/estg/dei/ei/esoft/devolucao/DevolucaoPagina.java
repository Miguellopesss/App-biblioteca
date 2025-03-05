package pt.ipleiria.estg.dei.ei.esoft.devolucao;

import pt.ipleiria.estg.dei.ei.esoft.PaginaPrincipal;
import pt.ipleiria.estg.dei.ei.esoft.Utilizador;
import pt.ipleiria.estg.dei.ei.esoft.socio.Socio;
import pt.ipleiria.estg.dei.ei.esoft.socio.EstadoSocio;

import javax.swing.*;
import java.awt.*;

public class DevolucaoPagina {

    private Utilizador utilizador;

    public DevolucaoPagina(Utilizador utilizador) {
        this.utilizador = utilizador;
    }

    public void abrirPaginaDevolucao() {
        JFrame frame = new JFrame("Biblioteca Patos do Liz - Devolução");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 900);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());


        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        JLabel memberNumberLabel = new JLabel("Número de sócio:");
        JTextField memberNumberField = new JTextField(10);
        inputPanel.add(memberNumberLabel);
        inputPanel.add(memberNumberField);
        panel.add(inputPanel, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Voltar");
        JButton nextButton = new JButton("Seguinte");

        backButton.addActionListener(e -> {
            PaginaPrincipal paginaPrincipal = new PaginaPrincipal(utilizador);
            paginaPrincipal.abrirPaginaPrincipal();
            frame.dispose();
        });

        nextButton.addActionListener(e -> {
            String numeroSocioStr = memberNumberField.getText();
            try {
                int numeroSocio = Integer.parseInt(numeroSocioStr);
                Socio socio = utilizador.getSocioByNumeroSocio(numeroSocio);
                if (socio != null) {
                    if (socio.getEstadoSocio() != EstadoSocio.ATIVO) {
                        JOptionPane.showMessageDialog(frame, "Sócio inativo.", "Erro", JOptionPane.ERROR_MESSAGE);
                    } else if (socio.getEmprestimos().isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Sócio não possui empréstimos.", "Erro", JOptionPane.ERROR_MESSAGE);
                    } else {
                        EmprestimosSocio emprestimosSocio = new EmprestimosSocio(utilizador, socio);
                        emprestimosSocio.abrirPaginaEmprestimosSocio();
                        frame.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Sócio não encontrado. Por favor, verifique o número e tente novamente.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Número de sócio inválido. Por favor, insira um número válido.");
            }
        });

        buttonsPanel.add(backButton);
        buttonsPanel.add(nextButton);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }
}
