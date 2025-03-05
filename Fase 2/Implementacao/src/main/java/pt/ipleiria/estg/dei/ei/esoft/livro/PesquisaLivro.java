package pt.ipleiria.estg.dei.ei.esoft.livro;

import pt.ipleiria.estg.dei.ei.esoft.PaginaPrincipal;
import pt.ipleiria.estg.dei.ei.esoft.Utilizador;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

public class PesquisaLivro {

    private Utilizador utilizador;
    private List<Livro> livros;

    public PesquisaLivro(Utilizador utilizador) {
        this.utilizador = utilizador;
        this.livros = utilizador.getLivros();
    }

    public void abrirPaginaPesquisaLivro() {
        JFrame frame = new JFrame("Biblioteca Patos do Liz - Pesquisa de Livros");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 900);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel filtersPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField autorField = new JTextField(10);
        JTextField tituloField = new JTextField(10);
        JTextField generoField = new JTextField(10);
        JTextField subGeneroField = new JTextField(10);
        JTextField isbnField = new JTextField(10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        filtersPanel.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1;
        filtersPanel.add(tituloField, gbc);
        gbc.gridx = 2;
        filtersPanel.add(new JLabel("Autor:"), gbc);
        gbc.gridx = 3;
        filtersPanel.add(autorField, gbc);
        gbc.gridx = 4;
        filtersPanel.add(new JLabel("Gênero:"), gbc);
        gbc.gridx = 5;
        filtersPanel.add(generoField, gbc);
        gbc.gridx = 6;
        filtersPanel.add(new JLabel("Subgênero:"), gbc);
        gbc.gridx = 7;
        filtersPanel.add(subGeneroField, gbc);
        gbc.gridx = 8;
        filtersPanel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 9;
        filtersPanel.add(isbnField, gbc);

        panel.add(filtersPanel, BorderLayout.NORTH);

        String[] columnNames = {"Título", "Autor", "Gênero", "Subgênero", "ISBN", "Disponibilidade"};
        Object[][] data = getData(livros);

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JScrollPane tableScrollPane = new JScrollPane(table);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable();
            }

            private void filterTable() {
                String autor = autorField.getText().toLowerCase();
                String titulo = tituloField.getText().toLowerCase();
                String genero = generoField.getText().toLowerCase();
                String subGenero = subGeneroField.getText().toLowerCase();
                String isbn = isbnField.getText().toLowerCase();

                List<Livro> filteredLivros = livros.stream()
                        .filter(livro -> livro.getAutor().toLowerCase().contains(autor))
                        .filter(livro -> livro.getTitulo().toLowerCase().contains(titulo))
                        .filter(livro -> livro.getGenero().toString().toLowerCase().contains(genero))
                        .filter(livro -> livro.getSubGenero().toString().toLowerCase().contains(subGenero))
                        .filter(livro -> livro.getIsbn().toLowerCase().contains(isbn))
                        .collect(Collectors.toList());

                Object[][] filteredData = getData(filteredLivros);
                table.setModel(new javax.swing.table.DefaultTableModel(
                        filteredData,
                        columnNames
                ));
            }
        };

        autorField.getDocument().addDocumentListener(documentListener);
        tituloField.getDocument().addDocumentListener(documentListener);
        generoField.getDocument().addDocumentListener(documentListener);
        subGeneroField.getDocument().addDocumentListener(documentListener);
        isbnField.getDocument().addDocumentListener(documentListener);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        String titulo = (String) table.getValueAt(selectedRow, 0);
                        Livro livro = livros.stream()
                                .filter(l -> l.getTitulo().equals(titulo))
                                .findFirst()
                                .orElse(null);

                        if (livro != null) {
                            DetalhesLivro detalhesLivro = new DetalhesLivro(livro, utilizador);
                            detalhesLivro.abrirPaginaDetalhesLivro();
                            frame.dispose();
                        }
                    }
                }
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Voltar");
        bottomPanel.add(backButton);

        backButton.addActionListener(e -> {
            PaginaPrincipal paginaPrincipal = new PaginaPrincipal(utilizador);
            paginaPrincipal.abrirPaginaPrincipal();
            frame.dispose();
        });

        panel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private Object[][] getData(List<Livro> livros) {
        Object[][] data = new Object[livros.size()][6];
        for (int i = 0; i < livros.size(); i++) {
            Livro livro = livros.get(i);
            data[i][0] = livro.getTitulo();
            data[i][1] = livro.getAutor();
            data[i][2] = livro.getGenero().toString();
            data[i][3] = livro.getSubGenero().toString();
            data[i][4] = livro.getIsbn();
            data[i][5] = livro.getEstado().toString();
        }
        return data;
    }
}
