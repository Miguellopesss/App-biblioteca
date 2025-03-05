package pt.ipleiria.estg.dei.ei.esoft.socio;

import pt.ipleiria.estg.dei.ei.esoft.Utilizador;

import javax.swing.*;
import java.awt.*;

public class DetalhesSocio {

    private Socio socio;
    private Utilizador utilizador;

    public DetalhesSocio(Socio socio, Utilizador utilizador) {
        this.socio = socio;
        this.utilizador = utilizador;
    }

    public void abrirPaginaDetalhesSocio() {
        JFrame frame = new JFrame("Biblioteca Patos do Liz - Detalhes do Sócio");
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
        panel.add(new JLabel("NIF:"), gbc);
        gbc.gridx = 1;
        JTextField nifField = new JTextField(socio.getNif(), 20);
        nifField.setEditable(false);
        panel.add(nifField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        JTextField telefoneField = new JTextField(socio.getTelefone(), 20);
        telefoneField.setEditable(false);
        panel.add(telefoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Morada:"), gbc);
        gbc.gridx = 1;
        JTextField moradaField = new JTextField(socio.getMorada(), 20);
        moradaField.setEditable(false);
        panel.add(moradaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        JTextField emailField = new JTextField(socio.getEmail(), 20);
        emailField.setEditable(false);
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Tipo Notificação:"), gbc);
        gbc.gridx = 1;
        JTextField notificacaoField = new JTextField(socio.getNotificacaoPor(), 20);
        notificacaoField.setEditable(false);
        panel.add(notificacaoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("Tipo de Sócio:"), gbc);
        gbc.gridx = 1;
        JTextField tipoSocioField = new JTextField(socio.getTipoSocio().toString(), 20);
        tipoSocioField.setEditable(false);
        panel.add(tipoSocioField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(new JLabel("Data de Anuidade:"), gbc);
        gbc.gridx = 1;
        JTextField dataAnuidadeField = new JTextField(socio.getDataAnuidade().toString(), 20);
        dataAnuidadeField.setEditable(false);
        panel.add(dataAnuidadeField, gbc);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Voltar");
        JButton editButton = new JButton("Editar");
        JButton removeButton = new JButton("Remover Sócio");
        JButton emprestimosReservasButton = new JButton("Empréstimos/Reservas");
        JButton pagarAnuidadeButton = new JButton("Pagar Anuidade");

        backButton.addActionListener(e -> {
            ListaSocio listaSocio = new ListaSocio(utilizador);
            listaSocio.abrirPaginaListaSocio();
            frame.dispose();
        });

        editButton.addActionListener(e -> {
            EditarSocio editarSocio = new EditarSocio(socio, utilizador);
            editarSocio.abrirPaginaEditarSocio();
            frame.dispose();
        });

        removeButton.addActionListener(e -> {
            if (!socio.getEmprestimos().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Sócio não pode ser removido, pois possui empréstimos pendentes.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else if (!socio.getReservas().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Sócio não pode ser removido, pois possui reservas pendentes.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else if (!socio.getMultas().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Sócio não pode ser removido, pois possui multas pendentes.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else if (socio.getStatusAnuidade() == EstadoAnuidade.POR_PAGAR) {
                JOptionPane.showMessageDialog(frame, "Sócio não pode ser removido, pois a anuidade não está paga.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                int response = JOptionPane.showConfirmDialog(frame, "Tem certeza que deseja remover este sócio?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    utilizador.removeSocio(socio);
                    JOptionPane.showMessageDialog(frame, "Sócio removido com sucesso.");
                    ListaSocio listaSocio = new ListaSocio(utilizador);
                    listaSocio.abrirPaginaListaSocio();
                    frame.dispose();
                }
            }
        });

        emprestimosReservasButton.addActionListener(e -> {
            EmprestimosEReservasSocio emprestimosEReservasSocio = new EmprestimosEReservasSocio(socio, utilizador);
            emprestimosEReservasSocio.abrirPaginaEmprestimosEReservasSocio();
            frame.dispose();
        });

        pagarAnuidadeButton.addActionListener(e -> {
            socio.pagarAnuidade();
            JOptionPane.showMessageDialog(frame, "Anuidade paga com sucesso.");
            ListaSocio listaSocio = new ListaSocio(utilizador);
            listaSocio.abrirPaginaListaSocio();
            frame.dispose();
        });

        buttonsPanel.add(backButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(removeButton);
        buttonsPanel.add(emprestimosReservasButton);
        buttonsPanel.add(pagarAnuidadeButton);
        panel.add(buttonsPanel, gbc);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonsPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
