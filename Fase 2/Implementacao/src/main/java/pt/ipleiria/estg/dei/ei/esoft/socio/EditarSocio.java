package pt.ipleiria.estg.dei.ei.esoft.socio;

import pt.ipleiria.estg.dei.ei.esoft.Utilizador;

import javax.swing.*;
import java.awt.*;

public class EditarSocio {

    private Socio socio;
    private Utilizador utilizador;

    public EditarSocio(Socio socio, Utilizador utilizador) {
        this.socio = socio;
        this.utilizador = utilizador;
    }

    public void abrirPaginaEditarSocio() {
        JFrame frame = new JFrame("Biblioteca Patos do Liz - Editar Sócio");
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
        JTextField nomeField = new JTextField(socio.getNome(), 20);
        panel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("NIF:"), gbc);
        gbc.gridx = 1;
        JTextField nifField = new JTextField(socio.getNif(), 20);
        panel.add(nifField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        JTextField telefoneField = new JTextField(socio.getTelefone(), 20);
        panel.add(telefoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Morada:"), gbc);
        gbc.gridx = 1;
        JTextField moradaField = new JTextField(socio.getMorada(), 20);
        panel.add(moradaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        JTextField emailField = new JTextField(socio.getEmail(), 20);
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Tipo de Notificação:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> tipoNotificacaoComboBox = new JComboBox<>(new String[]{"EMAIL", "SMS"});
        tipoNotificacaoComboBox.setSelectedItem(socio.getNotificacaoPor());
        panel.add(tipoNotificacaoComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Tipo de Sócio:"), gbc);
        gbc.gridx = 1;
        JComboBox<TipoSocio> tipoSocioComboBox = new JComboBox<>(TipoSocio.values());
        tipoSocioComboBox.setSelectedItem(socio.getTipoSocio());
        panel.add(tipoSocioComboBox, gbc);

        JButton saveButton = new JButton("Guardar");
        JButton backButton = new JButton("Voltar");

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(backButton);
        buttonsPanel.add(saveButton);

        backButton.addActionListener(e -> {
            DetalhesSocio detalhesSocio = new DetalhesSocio(socio, utilizador);
            detalhesSocio.abrirPaginaDetalhesSocio();
            frame.dispose();
        });

        saveButton.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String nif = nifField.getText().trim();
            String telefone = telefoneField.getText().trim();
            String morada = moradaField.getText().trim();
            String email = emailField.getText().trim();
            String tipoNotificacao = (String) tipoNotificacaoComboBox.getSelectedItem();
            TipoSocio tipoSocio = (TipoSocio) tipoSocioComboBox.getSelectedItem();

            if (nome.isEmpty() || nif.isEmpty() || telefone.isEmpty() || morada.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Todos os campos são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else if (!isValidNIF(nif)) {
                JOptionPane.showMessageDialog(frame, "NIF inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else if (!isValidTelefone(telefone)) {
                JOptionPane.showMessageDialog(frame, "Número de telefone inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(frame, "Email inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                socio.setNome(nome);
                socio.setNif(nif);
                socio.setTelefone(telefone);
                socio.setMorada(morada);
                socio.setEmail(email);
                socio.setNotificacaoPor(tipoNotificacao);
                socio.setTipoSocio(tipoSocio);
                JOptionPane.showMessageDialog(frame, "Sócio atualizado com sucesso.");
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
