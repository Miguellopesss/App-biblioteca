package pt.ipleiria.estg.dei.ei.esoft.fornecedor;

import pt.ipleiria.estg.dei.ei.esoft.Utilizador;
import pt.ipleiria.estg.dei.ei.esoft.livro.Livro;
import pt.ipleiria.estg.dei.ei.esoft.PaginaPrincipal;

import javax.swing.*;
import java.awt.*;

public class AdicionarLivro {

    private Livro livro;
    private Utilizador utilizador;
    private Fornecedor fornecedor;

    public AdicionarLivro(Livro livro, Utilizador utilizador, Fornecedor fornecedor) {
        this.livro = livro;
        this.utilizador = utilizador;
        this.fornecedor = fornecedor;
    }

    public void abrirPaginaAdicionarLivro() {
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
        panel.add(new JLabel("Género:"), gbc);
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
        panel.add(new JLabel("Quantidade:"), gbc);
        gbc.gridx = 1;
        Integer[] quantidade = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        JComboBox<Integer> quantidadeDropdown = new JComboBox<>(quantidade);
        panel.add(quantidadeDropdown, gbc);

        JButton backButton = new JButton("Voltar");
        JButton addButton = new JButton("Adicionar");

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(backButton);
        buttonsPanel.add(addButton);

        backButton.addActionListener(e -> {
            CatalogoFornecedor catalogoFornecedor = new CatalogoFornecedor(fornecedor, utilizador);
            catalogoFornecedor.abrirPaginaCatalogoFornecedor();
            frame.dispose();
        });

        addButton.addActionListener(e -> {
            int quantidadeAdicionar = (Integer) quantidadeDropdown.getSelectedItem();
            for (int i = 0; i < quantidadeAdicionar; i++) {
                Livro novoLivro = new Livro(livro.getTitulo(), livro.getAutor(), livro.getGenero(), livro.getSubGenero(), livro.getEditora(), livro.getEdicao(), livro.getAno(), livro.getIsbn());
                utilizador.addLivro(novoLivro);
            }
            JOptionPane.showMessageDialog(frame, quantidadeAdicionar + " livros adicionados com sucesso.");
            PaginaPrincipal paginaPrincipal = new PaginaPrincipal(utilizador);
            paginaPrincipal.abrirPaginaPrincipal();
            frame.dispose();
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonsPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
