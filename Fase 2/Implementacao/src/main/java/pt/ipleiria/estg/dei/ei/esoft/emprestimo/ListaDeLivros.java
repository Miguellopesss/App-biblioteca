package pt.ipleiria.estg.dei.ei.esoft.emprestimo;

import pt.ipleiria.estg.dei.ei.esoft.Utilizador;
import pt.ipleiria.estg.dei.ei.esoft.livro.Livro;
import pt.ipleiria.estg.dei.ei.esoft.reserva.Reserva;
import pt.ipleiria.estg.dei.ei.esoft.socio.Socio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ListaDeLivros {

    private Utilizador utilizador;
    private Socio socio;
    public ListaDeLivros(Utilizador utilizador,Socio socio) {
        this.utilizador = utilizador;
        this.socio = socio;
    }

    public void abrirPaginaListaDeLivros() {
        JFrame frame = new JFrame("Biblioteca Patos do Liz - Lista de Livros Disponíveis");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 900);

        JPanel panel = new JPanel(new BorderLayout());

        List<Livro> livrosDisponiveis = utilizador.getLivros();

        String[] columnNames = {"Título", "Autor", "Género", "Disponibilidade"};
        Object[][] data = new Object[livrosDisponiveis.size()][4];
        for (int i = 0; i < livrosDisponiveis.size(); i++) {
            data[i][0] = livrosDisponiveis.get(i).getTitulo();
            data[i][1] = livrosDisponiveis.get(i).getAutor();
            data[i][2] = livrosDisponiveis.get(i).getGenero().toString();
            data[i][3] = livrosDisponiveis.get(i).getEstado();
        }

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JScrollPane tableScrollPane = new JScrollPane(table);


        for (Reserva socio1 : socio.getReservas()) {
            System.out.println(socio1);
        }

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    Livro livroSelecionado = livrosDisponiveis.get(row);
                    abrirDetalhesEmprestimo(livroSelecionado);
                    frame.dispose();
                }
            }
        });

        panel.add(tableScrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Voltar");

        backButton.addActionListener(e -> {
            EmprestimoReservaPagina emprestimoReservaPagina = new EmprestimoReservaPagina(utilizador);
            emprestimoReservaPagina.abrirPaginaEmprestimoReserva();
            frame.dispose();
        });

        buttonsPanel.add(backButton);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void abrirDetalhesEmprestimo(Livro livro) {
        DetalhesEmprestimoReserva detalhesEmprestimoReserva = new DetalhesEmprestimoReserva(livro, utilizador, socio);
        detalhesEmprestimoReserva.abrirPaginaDetalhesEmprestimo();
    }
}
