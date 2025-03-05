package pt.ipleiria.estg.dei.ei.esoft.socio;

import pt.ipleiria.estg.dei.ei.esoft.PaginaPrincipal;
import pt.ipleiria.estg.dei.ei.esoft.Utilizador;
import pt.ipleiria.estg.dei.ei.esoft.multa.EstadoMulta;
import pt.ipleiria.estg.dei.ei.esoft.multa.Multa;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ListaSocioMulta {

    private Utilizador utilizador;

    public ListaSocioMulta(Utilizador utilizador) {
        this.utilizador = utilizador;
    }

    public void abrirPaginaListaSocioMulta() {
        JFrame frame = new JFrame("Biblioteca Patos do Liz - Sócios com Multas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 900);

        JPanel panel = new JPanel(new BorderLayout());

        List<Socio> sociosComMultas = utilizador.getSocios().stream()
                .filter(socio -> socio.getMultas().stream().anyMatch(multa -> multa.getEstadoMulta() == EstadoMulta.POR_PAGAR))
                .toList();

        String[] columnNames = {"Número Sócio", "Nome", "Valor da Multa", "Notificação"};
        List<Object[]> dataList = new ArrayList<>();

        for (Socio socio : sociosComMultas) {
            List<Multa> multasPorPagar = socio.getMultas().stream()
                    .filter(multa -> multa.getEstadoMulta() == EstadoMulta.POR_PAGAR)
                    .collect(Collectors.toList());

            for (Multa multa : multasPorPagar) {
                Object[] rowData = new Object[4];
                rowData[0] = socio.getNumeroSocio();
                rowData[1] = socio.getNome();
                rowData[2] = multa.getValorMulta();
                rowData[3] = socio.getNotificacaoPor();
                dataList.add(rowData);
            }
        }

        Object[][] data = dataList.toArray(new Object[0][]);

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int numeroSocio = (int) table.getValueAt(selectedRow, 0);
                        Socio socio = utilizador.getSocioByNumeroSocio(numeroSocio);
                        double valorMulta = (double) table.getValueAt(selectedRow, 2);
                        Multa multa = socio.getMultas().stream()
                                .filter(m -> m.getValorMulta() == valorMulta && m.getEstadoMulta() == EstadoMulta.POR_PAGAR)
                                .findFirst()
                                .orElse(null);

                        if (socio != null && multa != null) {
                            DetalhesMulta detalhesMulta = new DetalhesMulta(socio, multa, utilizador);
                            detalhesMulta.abrirPaginaDetalhesMulta();
                            frame.dispose();
                        }
                    }
                }
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(table);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Voltar");
        JButton notifyButton = new JButton("Notificar Sócios");

        backButton.addActionListener(e -> {
            PaginaPrincipal paginaPrincipal = new PaginaPrincipal(utilizador);
            paginaPrincipal.abrirPaginaPrincipal();
            frame.dispose();
        });

        notifyButton.addActionListener(e -> {
            for (Socio socio : sociosComMultas) {
                System.out.println("Notificando Sócio: " + socio.getNome());
            }
            JOptionPane.showMessageDialog(frame, "Sócios notificados com sucesso.");
        });

        buttonsPanel.add(backButton);
        buttonsPanel.add(notifyButton);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }
}
