package pt.ipleiria.estg.dei.ei.esoft.fornecedor;

import pt.ipleiria.estg.dei.ei.esoft.Utilizador;
import pt.ipleiria.estg.dei.ei.esoft.PaginaPrincipal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FornecedoresPagina {

    private Utilizador utilizador;

    public FornecedoresPagina(Utilizador utilizador) {
        this.utilizador = utilizador;
    }

    public void abrirPaginaFornecedores() {
        JFrame frame = new JFrame("Biblioteca Patos do Liz - Fornecedores");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 900);

        JPanel panel = new JPanel(new BorderLayout());

        List<Fornecedor> fornecedores = utilizador.getFornecedoresAtivos();
        String[] columnNames = {"Número Fornecedor", "Editora", "Distribuidora"};
        Object[][] data = new Object[fornecedores.size()][3];
        for (int i = 0; i < fornecedores.size(); i++) {
            data[i][0] = fornecedores.get(i).getNumeroFornecedor();
            data[i][1] = fornecedores.get(i).getDistribuidora();
            data[i][2] = fornecedores.get(i).getEditora();
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
                            int numeroFornecedor = (int) table.getValueAt(selectedRow, 0);
                            Fornecedor fornecedor = utilizador.getNumeroFornecedor(numeroFornecedor);
                            if (fornecedor != null) {
                                CatalogoFornecedor catalogoFornecedor = new CatalogoFornecedor(fornecedor, utilizador);
                                catalogoFornecedor.abrirPaginaCatalogoFornecedor();
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Erro ao converter o número do fornecedor. Verifique os dados e tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frame, "Ocorreu um erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Voltar");
        JButton addButton = new JButton("Adicionar Fornecedor");


        backButton.addActionListener(e -> {
            PaginaPrincipal paginaPrincipal = new PaginaPrincipal(utilizador);
            paginaPrincipal.abrirPaginaPrincipal();
            frame.dispose();
        });

        addButton.addActionListener(e -> {
            AdicionarFornecedor adicionarFornecedor = new AdicionarFornecedor(utilizador);
            adicionarFornecedor.abrirPaginaAdicionarFornecedor();
            frame.dispose();
        });


        buttonsPanel.add(backButton);
        buttonsPanel.add(addButton);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }
}
