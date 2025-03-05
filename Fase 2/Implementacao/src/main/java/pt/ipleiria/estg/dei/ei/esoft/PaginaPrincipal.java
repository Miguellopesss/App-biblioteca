package pt.ipleiria.estg.dei.ei.esoft;

import pt.ipleiria.estg.dei.ei.esoft.devolucao.DevolucaoPagina;
import pt.ipleiria.estg.dei.ei.esoft.emprestimo.EmprestimoReservaPagina;
import pt.ipleiria.estg.dei.ei.esoft.fornecedor.FornecedoresPagina;
import pt.ipleiria.estg.dei.ei.esoft.livro.Livro;
import pt.ipleiria.estg.dei.ei.esoft.livro.PesquisaLivro;
import pt.ipleiria.estg.dei.ei.esoft.socio.ListaSocio;
import pt.ipleiria.estg.dei.ei.esoft.socio.ListaSocioMulta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Vector;


public class PaginaPrincipal {

    private Utilizador utilizador;
    private JTable table;

    public PaginaPrincipal(Utilizador utilizador) {
        this.utilizador = utilizador;
        verificarAnuidades();
    }

    public void abrirPaginaPrincipal() {
        JFrame frame = new JFrame("Biblioteca Patos do Liz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 900);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));

        Dimension buttonSize = new Dimension(250, 100);

        JButton registerLoanButton = new JButton("Registar empréstimo/reserva");
        JButton registerReturnButton = new JButton("Registar devolução");
        JButton acquireBooksButton = new JButton("Aquisição de livros");
        JButton searchBookButton = new JButton("Pesquisar livro");
        JButton memberListButton = new JButton("Lista de sócios");
        JButton listMembersWithFinesButton = new JButton("Listar sócios com multas");

        setButtonSize(registerLoanButton, buttonSize);
        setButtonSize(registerReturnButton, buttonSize);
        setButtonSize(acquireBooksButton, buttonSize);
        setButtonSize(searchBookButton, buttonSize);
        setButtonSize(memberListButton, buttonSize);
        setButtonSize(listMembersWithFinesButton, buttonSize);

        registerLoanButton.addActionListener(e -> {
            EmprestimoReservaPagina emprestimoReservaPagina = new EmprestimoReservaPagina(utilizador);
            emprestimoReservaPagina.abrirPaginaEmprestimoReserva();
            frame.dispose();
        });

        registerReturnButton.addActionListener(e -> {
            DevolucaoPagina devolucaoPagina = new DevolucaoPagina(utilizador);
            devolucaoPagina.abrirPaginaDevolucao();
            frame.dispose();
        });

        searchBookButton.addActionListener(e -> {
            PesquisaLivro pesquisaLivro = new PesquisaLivro(utilizador);
            pesquisaLivro.abrirPaginaPesquisaLivro();
            frame.dispose();
        });

        acquireBooksButton.addActionListener(e -> {
            FornecedoresPagina fornecedoresPagina = new FornecedoresPagina(utilizador);
            fornecedoresPagina.abrirPaginaFornecedores();
            frame.dispose();
        });

        memberListButton.addActionListener(e -> {
            ListaSocio listaSocio = new ListaSocio(utilizador);
            listaSocio.abrirPaginaListaSocio();
            frame.dispose();
        });

        listMembersWithFinesButton.addActionListener(e -> {
            ListaSocioMulta listaSocioMulta = new ListaSocioMulta(utilizador);
            listaSocioMulta.abrirPaginaListaSocioMulta();
            frame.dispose();
        });

        sidePanel.add(registerLoanButton);
        sidePanel.add(Box.createVerticalStrut(25));
        sidePanel.add(registerReturnButton);
        sidePanel.add(Box.createVerticalStrut(25));
        sidePanel.add(acquireBooksButton);
        sidePanel.add(Box.createVerticalStrut(25));
        sidePanel.add(searchBookButton);
        sidePanel.add(Box.createVerticalStrut(25));
        sidePanel.add(memberListButton);
        sidePanel.add(Box.createVerticalStrut(25));
        sidePanel.add(listMembersWithFinesButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.weightx = 0.2;
        gbc.weighty = 1.0;
        panel.add(sidePanel, gbc);

        JPanel topBooksPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Top livros requisitados", SwingConstants.CENTER);
        topBooksPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        Dimension topButtonSize = new Dimension(150, 50);

        JButton geralButton = new JButton("Geral");
        JButton autorButton = new JButton("Autor");
        JButton generoButton = new JButton("Género");
        JButton subgeneroButton = new JButton("Subgénero");

        setButtonSize(geralButton, topButtonSize);
        setButtonSize(autorButton, topButtonSize);
        setButtonSize(generoButton, topButtonSize);
        setButtonSize(subgeneroButton, topButtonSize);

        buttonsPanel.add(geralButton);
        buttonsPanel.add(autorButton);
        buttonsPanel.add(generoButton);
        buttonsPanel.add(subgeneroButton);
        topBooksPanel.add(buttonsPanel, BorderLayout.CENTER);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 0.8;
        gbc.weighty = 0.1;
        panel.add(topBooksPanel, gbc);

        String[] columnNames = {"Título", "Autor", "Gênero", "Quantidade"};
        table = new JTable(new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        JScrollPane tableScrollPane = new JScrollPane(table);

        updateTableDataGeral();

        geralButton.addActionListener(e -> updateTableDataGeral());

        autorButton.addActionListener(e -> updateTableDataPorAutor());

        generoButton.addActionListener(e -> updateTableDataPorGenero());

        subgeneroButton.addActionListener(e -> updateTableDataPorSubGenero());

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.8;
        gbc.weighty = 0.9;
        panel.add(tableScrollPane, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void setButtonSize(JButton button, Dimension size) {
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);
    }
    private void verificarAnuidades() {
        utilizador.getSocios().forEach(socio -> {
            socio.setStatusAnuidade(socio.getStatusAnuidade());
        });
    }
    public void updateTableDataGeral() {
        String[] columnNames = {"Título", "Autor", "Gênero", "Quantidade"};
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(columnNames);
        model.setRowCount(0);

        Map<Livro, Long> livroCounts = new HashMap<>();


        utilizador.getSocios().forEach(socio -> {
            socio.getEmprestimos().forEach(emprestimo -> {
                livroCounts.put(emprestimo.getLivro(), livroCounts.getOrDefault(emprestimo.getLivro(), 0L) + 1);
            });
            socio.getDevolucoes().forEach(devolucao -> {
                livroCounts.put(devolucao.getLivro(), livroCounts.getOrDefault(devolucao.getLivro(), 0L) + 1);
            });
        });

        livroCounts.forEach((livro, count) -> {
            model.addRow(new Object[]{livro.getTitulo(), livro.getAutor(), livro.getGenero().toString(), count});
        });

        model.getDataVector().sort((a, b) -> ((Long) ((Vector) b).get(3)).compareTo((Long) ((Vector) a).get(3)));
    }
    public void updateTableDataPorAutor() {
        String[] columnNames = {"Autor", "Quantidade"};
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(columnNames);
        model.setRowCount(0);

        Map<String, Long> autorCounts = new HashMap<>();

        // Contar empréstimos e devoluções
        utilizador.getSocios().forEach(socio -> {
            socio.getEmprestimos().forEach(emprestimo -> {
                String autor = emprestimo.getLivro().getAutor();
                autorCounts.put(autor, autorCounts.getOrDefault(autor, 0L) + 1);
            });
            socio.getDevolucoes().forEach(devolucao -> {
                String autor = devolucao.getLivro().getAutor();
                autorCounts.put(autor, autorCounts.getOrDefault(autor, 0L) + 1);
            });
        });

        autorCounts.forEach((autor, count) -> {
            model.addRow(new Object[]{autor, count});
        });

        model.getDataVector().sort((a, b) -> ((Long) ((Vector) b).get(1)).compareTo((Long) ((Vector) a).get(1))); // Sort by count descending
    }
    public void updateTableDataPorGenero() {
        String[] columnNames = {"Gênero", "Quantidade"};
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(columnNames);
        model.setRowCount(0);

        Map<String, Long> generoCounts = new HashMap<>();

        utilizador.getSocios().forEach(socio -> {
            socio.getEmprestimos().forEach(emprestimo -> {
                String genero = emprestimo.getLivro().getGenero().toString();
                generoCounts.put(genero, generoCounts.getOrDefault(genero, 0L) + 1);
            });
            socio.getDevolucoes().forEach(devolucao -> {
                String genero = devolucao.getLivro().getGenero().toString();
                generoCounts.put(genero, generoCounts.getOrDefault(genero, 0L) + 1);
            });
        });

        generoCounts.forEach((genero, count) -> {
            model.addRow(new Object[]{genero, count});
        });

        model.getDataVector().sort((a, b) -> ((Long) ((Vector) b).get(1)).compareTo((Long) ((Vector) a).get(1))); // Sort by count descending
    }
    public void updateTableDataPorSubGenero() {
        String[] columnNames = {"Subgênero", "Quantidade"};
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(columnNames);
        model.setRowCount(0);

        Map<String, Long> subgeneroCounts = new HashMap<>();

        // Contar empréstimos e devoluções
        utilizador.getSocios().forEach(socio -> {
            socio.getEmprestimos().forEach(emprestimo -> {
                String subgenero = emprestimo.getLivro().getSubGenero().toString();
                subgeneroCounts.put(subgenero, subgeneroCounts.getOrDefault(subgenero, 0L) + 1);
            });
            socio.getDevolucoes().forEach(devolucao -> {
                String subgenero = devolucao.getLivro().getSubGenero().toString();
                subgeneroCounts.put(subgenero, subgeneroCounts.getOrDefault(subgenero, 0L) + 1);
            });
        });

        subgeneroCounts.forEach((subgenero, count) -> {
            model.addRow(new Object[]{subgenero, count});
        });

        model.getDataVector().sort((a, b) -> ((Long) ((Vector) b).get(1)).compareTo((Long) ((Vector) a).get(1))); // Sort by count descending
    }

}
