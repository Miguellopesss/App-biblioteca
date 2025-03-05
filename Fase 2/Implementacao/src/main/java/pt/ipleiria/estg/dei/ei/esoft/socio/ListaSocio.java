package pt.ipleiria.estg.dei.ei.esoft.socio;

import pt.ipleiria.estg.dei.ei.esoft.Utilizador;
import pt.ipleiria.estg.dei.ei.esoft.PaginaPrincipal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

public class ListaSocio {

    private Utilizador utilizador;

    public ListaSocio(Utilizador utilizador) {
        this.utilizador = utilizador;
    }

    public void abrirPaginaListaSocio() {
        JFrame frame = new JFrame("Biblioteca Patos do Liz - Lista de Sócios");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 900);

        JPanel panel = new JPanel(new BorderLayout());

        List<Socio> sociosAtivos = utilizador.getSocios().stream()
                .filter(socio -> socio.getEstadoSocio() == EstadoSocio.ATIVO)
                .collect(Collectors.toList());

        String[] columnNames = {"Número Sócio", "Nome", "NIF", "Telefone","Anuidade"};
        Object[][] data = new Object[sociosAtivos.size()][5];
        for (int i = 0; i < sociosAtivos.size(); i++) {
            data[i][0] = sociosAtivos.get(i).getNumeroSocio();
            data[i][1] = sociosAtivos.get(i).getNome();
            data[i][2] = sociosAtivos.get(i).getNif();
            data[i][3] = sociosAtivos.get(i).getTelefone();
            data[i][4] = sociosAtivos.get(i).getStatusAnuidade();
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
                            int numeroSocio = (int) table.getValueAt(selectedRow, 0);
                            Socio socio = utilizador.getSocioByNumeroSocio(numeroSocio);
                            if (socio != null) {
                                DetalhesSocio detalhesSocio = new DetalhesSocio(socio, utilizador);
                                detalhesSocio.abrirPaginaDetalhesSocio();
                                frame.dispose();
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Erro ao converter o número do sócio. Verifique os dados e tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frame, "Ocorreu um erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Voltar");
        JButton addButton = new JButton("Adicionar Sócio");

        backButton.addActionListener(e -> {
            PaginaPrincipal paginaPrincipal = new PaginaPrincipal(utilizador);
            paginaPrincipal.abrirPaginaPrincipal();
            frame.dispose();
        });

        addButton.addActionListener(e -> {
            AdicionarSocio adicionarSocio = new AdicionarSocio(utilizador);
            adicionarSocio.abrirPaginaAdicionarSocio();
            frame.dispose();
        });

        buttonsPanel.add(backButton);
        buttonsPanel.add(addButton);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }
}
