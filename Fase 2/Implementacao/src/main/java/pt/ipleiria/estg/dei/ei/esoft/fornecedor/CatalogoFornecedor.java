package pt.ipleiria.estg.dei.ei.esoft.fornecedor;

import pt.ipleiria.estg.dei.ei.esoft.PaginaPrincipal;
import pt.ipleiria.estg.dei.ei.esoft.Utilizador;
import pt.ipleiria.estg.dei.ei.esoft.livro.Livro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CatalogoFornecedor {

    private Fornecedor fornecedor;
    private Utilizador utilizador;

    public CatalogoFornecedor(Fornecedor fornecedor, Utilizador utilizador) {
        this.fornecedor = fornecedor;
        this.utilizador = utilizador;
    }

    public void abrirPaginaCatalogoFornecedor() {
        JFrame frame = new JFrame("Biblioteca Patos do Liz - Catálogo do Fornecedor");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1100, 900);

        JPanel panel = new JPanel(new BorderLayout());

        List<Livro> livros = fornecedor.getLivros();
        String[] columnNames = {"Título", "Autor", "Género", "ISBN"};
        Object[][] data = new Object[livros.size()][4];
        for (int i = 0; i < livros.size(); i++) {
            data[i][0] = livros.get(i).getTitulo();
            data[i][1] = livros.get(i).getAutor();
            data[i][2] = livros.get(i).getGenero().toString();
            data[i][3] = livros.get(i).getIsbn();
        }

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JScrollPane tableScrollPane = new JScrollPane(table);

        panel.add(tableScrollPane, BorderLayout.CENTER);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        try {
                            Livro livro = livros.get(selectedRow);
                            AdicionarLivro adicionarLivro = new AdicionarLivro(livro, utilizador, fornecedor);
                            adicionarLivro.abrirPaginaAdicionarLivro();
                            frame.dispose();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frame, "Ocorreu um erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Voltar");
        JButton removeButton = new JButton("Remover Fornecedor");

        backButton.addActionListener(e -> frame.dispose());

        removeButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(frame, "Tem certeza que deseja remover este fornecedor?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                utilizador.removeFornecedor(fornecedor);
                JOptionPane.showMessageDialog(frame, "Fornecedor removido com sucesso.");
                PaginaPrincipal paginaPrincipal = new PaginaPrincipal(utilizador);
                paginaPrincipal.abrirPaginaPrincipal();
                frame.dispose();
            }
        });

        buttonsPanel.add(backButton);
        buttonsPanel.add(removeButton);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }
}
