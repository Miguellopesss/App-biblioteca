package pt.ipleiria.estg.dei.ei.esoft.socio;

import pt.ipleiria.estg.dei.ei.esoft.Utilizador;
import pt.ipleiria.estg.dei.ei.esoft.emprestimo.Emprestimo;
import pt.ipleiria.estg.dei.ei.esoft.reserva.Reserva;
import pt.ipleiria.estg.dei.ei.esoft.devolucao.DetalhesDevolucao;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class EmprestimosEReservasSocio {

    private Socio socio;
    private Utilizador utilizador;

    public EmprestimosEReservasSocio(Socio socio, Utilizador utilizador) {
        this.socio = socio;
        this.utilizador = utilizador;
    }

    public void abrirPaginaEmprestimosEReservasSocio() {
        JFrame frame = new JFrame("Biblioteca Patos do Liz - Empréstimos e Reservas do Sócio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 900);

        JPanel panel = new JPanel(new BorderLayout());

        List<Emprestimo> emprestimos = socio.getEmprestimos();
        List<Reserva> reservas = socio.getReservas();

        String[] columnNames = {"Tipo", "Título", "Autor", "Data"};
        Object[][] data = new Object[emprestimos.size() + reservas.size()][4];

        int i = 0;
        for (Emprestimo emprestimo : emprestimos) {
            data[i][0] = "Empréstimo";
            data[i][1] = emprestimo.getLivro().getTitulo();
            data[i][2] = emprestimo.getLivro().getAutor();
            data[i][3] = emprestimo.getDataEmprestimo().toString();
            i++;
        }

        for (Reserva reserva : reservas) {
            data[i][0] = "Reserva";
            data[i][1] = reserva.getLivro().getTitulo();
            data[i][2] = reserva.getLivro().getAutor();
            i++;
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
                        String tipo = (String) table.getValueAt(selectedRow, 0);
                        String titulo = (String) table.getValueAt(selectedRow, 1);

                        if ("Empréstimo".equals(tipo)) {
                            Emprestimo emprestimoSelecionado = null;
                            for (Emprestimo emprestimo : emprestimos) {
                                if (emprestimo.getLivro().getTitulo().equals(titulo)) {
                                    emprestimoSelecionado = emprestimo;
                                    break;
                                }
                            }
                            if (emprestimoSelecionado != null) {
                                DetalhesDevolucao detalhesDevolucao = new DetalhesDevolucao(utilizador, socio);
                                detalhesDevolucao.abrirDetalhesDevolucao(emprestimoSelecionado);
                                frame.dispose();
                            }
                        } else if ("Reserva".equals(tipo)) {
                            Reserva reservaSelecionada = null;
                            for (Reserva reserva : reservas) {
                                if (reserva.getLivro().getTitulo().equals(titulo)) {
                                    reservaSelecionada = reserva;
                                    break;
                                }
                            }
                            if (reservaSelecionada != null) {
                                CancelarReserva cancelarReserva = new CancelarReserva(reservaSelecionada, utilizador, socio);
                                cancelarReserva.abrirPaginaCancelarReserva();
                                frame.dispose();
                            }
                        }
                    }
                }
            }
        });

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Voltar");

        backButton.addActionListener(e -> {
            DetalhesSocio detalhesSocio = new DetalhesSocio(socio, utilizador);
            detalhesSocio.abrirPaginaDetalhesSocio();
            frame.dispose();
        });

        buttonsPanel.add(backButton);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }
}
