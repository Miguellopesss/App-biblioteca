package pt.ipleiria.estg.dei.ei.esoft.socio;

import pt.ipleiria.estg.dei.ei.esoft.Utilizador;

import javax.swing.*;
import java.awt.*;

public class AdicionarSocio {

    private Utilizador utilizador;

    public AdicionarSocio(Utilizador utilizador) {
        this.utilizador = utilizador;
    }

    public void abrirPaginaAdicionarSocio() {
        JFrame frame = new JFrame("Biblioteca Patos do Liz - Adicionar Sócio");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1100, 900);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        JTextField nomeField = new JTextField(20);
        panel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("NIF:"), gbc);
        gbc.gridx = 1;
        JTextField nifField = new JTextField(20);
        panel.add(nifField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        JTextField telefoneField = new JTextField(20);
        panel.add(telefoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Morada:"), gbc);
        gbc.gridx = 1;
        JTextField moradaField = new JTextField(20);
        panel.add(moradaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        JTextField emailField = new JTextField(20);
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Tipo de Notificação:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> notificacaoComboBox = new JComboBox<>(new String[]{"SMS", "Email"});
        panel.add(notificacaoComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Tipo de Sócio:"), gbc);
        gbc.gridx = 1;
        JComboBox<TipoSocio> tipoSocioComboBox = new JComboBox<>(TipoSocio.values());
        panel.add(tipoSocioComboBox, gbc);

        JButton saveButton = new JButton("Adicionar Sócio");
        JButton backButton = new JButton("Voltar");

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(backButton);
        buttonsPanel.add(saveButton);

        backButton.addActionListener(e -> {
            ListaSocio listaSocio = new ListaSocio(utilizador);
            listaSocio.abrirPaginaListaSocio();
            frame.dispose();
        });

        saveButton.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String nif = nifField.getText().trim();
            String telefone = telefoneField.getText().trim();
            String morada = moradaField.getText().trim();
            String email = emailField.getText().trim();
            String notificacao = (String) notificacaoComboBox.getSelectedItem();
            TipoSocio tipoSocio = (TipoSocio) tipoSocioComboBox.getSelectedItem();

            if (nome.isEmpty() || nif.isEmpty() || telefone.isEmpty() || morada.isEmpty() || email.isEmpty() || notificacao.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Todos os campos são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else if (!isValidNIF(nif)) {
                JOptionPane.showMessageDialog(frame, "NIF inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else if (!isValidTelefone(telefone)) {
                JOptionPane.showMessageDialog(frame, "Número de telefone inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(frame, "Email inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else if (utilizador.getSocios().stream().anyMatch(s -> s.getNif().equals(nif))) {
                JOptionPane.showMessageDialog(frame, "Já existe um sócio com este NIF.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                Socio socio = new Socio(nome, nif, morada, telefone, email, notificacao, tipoSocio);
                utilizador.addSocio(socio);
                JOptionPane.showMessageDialog(frame, "Sócio adicionado com sucesso.");
                ListaSocio listaSocio = new ListaSocio(utilizador);
                listaSocio.abrirPaginaListaSocio();
                frame.dispose();
            }
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonsPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private boolean isValidNIF(String nif) {
        return nif != null && nif.matches("\\d{9}");
    }
    private boolean isValidTelefone(String telefone) {
        return telefone != null && telefone.matches("\\d{9}");
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }
}
