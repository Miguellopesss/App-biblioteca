package pt.ipleiria.estg.dei.ei.esoft.socio;

import pt.ipleiria.estg.dei.ei.esoft.Utilizador;
import pt.ipleiria.estg.dei.ei.esoft.reserva.Reserva;

import javax.swing.*;
import java.awt.*;

public class CancelarReserva {

    private Reserva reserva;
    private Utilizador utilizador;
    private Socio socio;

    public CancelarReserva(Reserva reserva, Utilizador utilizador, Socio socio) {
        this.reserva = reserva;
        this.utilizador = utilizador;
        this.socio = socio;
    }

    public void abrirPaginaCancelarReserva() {
        JFrame frame = new JFrame("Biblioteca Patos do Liz - Cancelar Reserva");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1100, 900);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1;
        JTextField tituloField = new JTextField(reserva.getLivro().getTitulo(), 20);
        tituloField.setEditable(false);
        panel.add(tituloField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Autor:"), gbc);
        gbc.gridx = 1;
        JTextField autorField = new JTextField(reserva.getLivro().getAutor(), 20);
        autorField.setEditable(false);
        panel.add(autorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Género:"), gbc);
        gbc.gridx = 1;
        JTextField generoField = new JTextField(reserva.getLivro().getGenero().toString(), 20);
        generoField.setEditable(false);
        panel.add(generoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Subgênero:"), gbc);
        gbc.gridx = 1;
        JTextField subgeneroField = new JTextField(reserva.getLivro().getSubGenero().toString(), 20);
        subgeneroField.setEditable(false);
        panel.add(subgeneroField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Editora:"), gbc);
        gbc.gridx = 1;
        JTextField editoraField = new JTextField(reserva.getLivro().getEditora(), 20);
        editoraField.setEditable(false);
        panel.add(editoraField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Edição:"), gbc);
        gbc.gridx = 1;
        JTextField edicaoField = new JTextField(reserva.getLivro().getEdicao(), 20);
        edicaoField.setEditable(false);
        panel.add(edicaoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Ano:"), gbc);
        gbc.gridx = 1;
        JTextField anoField = new JTextField(String.valueOf(reserva.getLivro().getAno()), 20);
        anoField.setEditable(false);
        panel.add(anoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        JTextField isbnField = new JTextField(reserva.getLivro().getIsbn(), 20);
        isbnField.setEditable(false);
        panel.add(isbnField, gbc);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Voltar");
        JButton cancelButton = new JButton("Cancelar Reserva");

        backButton.addActionListener(e -> {
            EmprestimosEReservasSocio emprestimosEReservasSocio = new EmprestimosEReservasSocio(socio, utilizador);
            emprestimosEReservasSocio.abrirPaginaEmprestimosEReservasSocio();
            frame.dispose();
        });

        cancelButton.addActionListener(e -> {
            socio.removeReserva(reserva);
            JOptionPane.showMessageDialog(frame, "Reserva cancelada com sucesso.");
            EmprestimosEReservasSocio emprestimosEReservasSocio = new EmprestimosEReservasSocio(socio, utilizador);
            emprestimosEReservasSocio.abrirPaginaEmprestimosEReservasSocio();
            frame.dispose();
        });

        buttonsPanel.add(backButton);
        buttonsPanel.add(cancelButton);
        panel.add(buttonsPanel, gbc);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonsPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
