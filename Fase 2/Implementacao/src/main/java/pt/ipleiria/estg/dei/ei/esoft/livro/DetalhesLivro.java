package pt.ipleiria.estg.dei.ei.esoft.livro;

import pt.ipleiria.estg.dei.ei.esoft.Utilizador;

import javax.swing.*;
import java.awt.*;

public class DetalhesLivro {

    private Livro livro;
    private Utilizador utilizador;

    public DetalhesLivro(Livro livro, Utilizador utilizador) {
        this.livro = livro;
        this.utilizador = utilizador;
    }

    public void abrirPaginaDetalhesLivro() {
        JFrame frame = new JFrame("Biblioteca Patos do Liz - Detalhes do Livro");
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
        JTextField tituloField = new JTextField(livro.getTitulo(), 20);
        tituloField.setEditable(false);
        panel.add(tituloField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Autor:"), gbc);
        gbc.gridx = 1;
        JTextField autorField = new JTextField(livro.getAutor(), 20);
        autorField.setEditable(false);
        panel.add(autorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Gênero:"), gbc);
        gbc.gridx = 1;
        JTextField generoField = new JTextField(livro.getGenero().toString(), 20);
        generoField.setEditable(false);
        panel.add(generoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Subgênero:"), gbc);
        gbc.gridx = 1;
        JTextField subgeneroField = new JTextField(livro.getSubGenero().toString(), 20);
        subgeneroField.setEditable(false);
        panel.add(subgeneroField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Editora:"), gbc);
        gbc.gridx = 1;
        JTextField editoraField = new JTextField(livro.getEditora(), 20);
        editoraField.setEditable(false);
        panel.add(editoraField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Edição:"), gbc);
        gbc.gridx = 1;
        JTextField edicaoField = new JTextField(livro.getEdicao(), 20);
        edicaoField.setEditable(false);
        panel.add(edicaoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Ano:"), gbc);
        gbc.gridx = 1;
        JTextField anoField = new JTextField(String.valueOf(livro.getAno()), 20);
        anoField.setEditable(false);
        panel.add(anoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        JTextField isbnField = new JTextField(livro.getIsbn(), 20);
        isbnField.setEditable(false);
        panel.add(isbnField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(new JLabel("Disponibilidade:"), gbc);
        gbc.gridx = 1;
        JTextField disponibilidadeField = new JTextField(livro.getEstado().toString(), 20);
        disponibilidadeField.setEditable(false);
        panel.add(disponibilidadeField, gbc);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Voltar");

        backButton.addActionListener(e -> {
            PesquisaLivro pesquisaLivro = new PesquisaLivro(utilizador);
            pesquisaLivro.abrirPaginaPesquisaLivro();
            frame.dispose();
        });

        buttonsPanel.add(backButton);

        if (livro.getEstado() == EstadoLivro.DISPONIVEL) {
            JButton removeButton = new JButton("Remover");

            removeButton.addActionListener(e -> {
                int response = JOptionPane.showConfirmDialog(frame, "Tem certeza que deseja remover este livro?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    utilizador.removeLivro(livro);
                    JOptionPane.showMessageDialog(frame, "Livro removido com sucesso.");
                    PesquisaLivro pesquisaLivro = new PesquisaLivro(utilizador);
                    pesquisaLivro.abrirPaginaPesquisaLivro();
                    frame.dispose();
                }
            });

            buttonsPanel.add(removeButton);
        }

        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonsPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
