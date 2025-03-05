package pt.ipleiria.estg.dei.ei.esoft.devolucao;

import pt.ipleiria.estg.dei.ei.esoft.Utilizador;
import pt.ipleiria.estg.dei.ei.esoft.socio.Socio;
import pt.ipleiria.estg.dei.ei.esoft.emprestimo.Emprestimo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class EmprestimosSocio {

    private Utilizador utilizador;
    private Socio socio;

    public EmprestimosSocio(Utilizador utilizador, Socio socio) {
        this.utilizador = utilizador;
        this.socio = socio;
    }

    public void abrirPaginaEmprestimosSocio() {
        JFrame frame = new JFrame("Biblioteca Patos do Liz - Empréstimos do Sócio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 900);

        JPanel panel = new JPanel(new BorderLayout());

        List<Emprestimo> emprestimos = socio.getEmprestimos();
        String[] columnNames = {"Título", "Autor", "Data de Devolução Prevista"};
        Object[][] data = new Object[emprestimos.size()][3];
        for (int i = 0; i < emprestimos.size(); i++) {
            data[i][0] = emprestimos.get(i).getLivro().getTitulo();
            data[i][1] = emprestimos.get(i).getLivro().getAutor();
            data[i][2] = emprestimos.get(i).getDataPrevistaDevolucao().toString();
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna todas as células não editáveis
            }
        };

        JTable table = new JTable(model);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable)e.getSource();
                    int row = target.getSelectedRow();
                    Emprestimo emprestimoSelecionado = emprestimos.get(row);
                    DetalhesDevolucao detalhesDevolucao = new DetalhesDevolucao(utilizador,socio);
                    detalhesDevolucao.abrirDetalhesDevolucao(emprestimoSelecionado);
                }
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(table);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Voltar");

        backButton.addActionListener(e -> {
            DevolucaoPagina devolucaoPagina = new DevolucaoPagina(utilizador);
            devolucaoPagina.abrirPaginaDevolucao();
            frame.dispose();
        });

        buttonsPanel.add(backButton);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

}
