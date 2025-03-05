package pt.ipleiria.estg.dei.ei.esoft.emprestimo;

import pt.ipleiria.estg.dei.ei.esoft.PaginaPrincipal;
import pt.ipleiria.estg.dei.ei.esoft.Utilizador;
import pt.ipleiria.estg.dei.ei.esoft.livro.Livro;
import pt.ipleiria.estg.dei.ei.esoft.reserva.Reserva;
import pt.ipleiria.estg.dei.ei.esoft.socio.Socio;
import pt.ipleiria.estg.dei.ei.esoft.data.Data;
import pt.ipleiria.estg.dei.ei.esoft.socio.TipoSocio;

import javax.swing.*;
import java.awt.*;

import static pt.ipleiria.estg.dei.ei.esoft.livro.EstadoLivro.DISPONIVEL;
import static pt.ipleiria.estg.dei.ei.esoft.livro.EstadoLivro.INDISPONIVEL;
import static pt.ipleiria.estg.dei.ei.esoft.socio.TipoSocio.STANDARD;

public class DetalhesEmprestimoReserva {

    private Utilizador utilizador;
    private Livro livro;
    private Socio socio;
    private Emprestimo emprestimo;
    private Reserva reserva;
    private Data dataPrevistaDevolucao;

    public DetalhesEmprestimoReserva(Livro livro, Utilizador utilizador, Socio socio) {
        this.livro = livro;
        this.utilizador = utilizador;
        this.socio = socio;
    }

    public void abrirPaginaDetalhesEmprestimo() {
        JFrame frame = new JFrame("Biblioteca Patos do Liz - Detalhes do Empréstimo/Reserva");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 900);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Número de sócio:"));
        JTextField socioField = new JTextField(String.valueOf(socio.getNumeroSocio()));
        socioField.setEditable(false);
        panel.add(socioField);

        panel.add(new JLabel("Nome do livro:"));
        JTextField nomeLivroField = new JTextField(livro.getTitulo());
        nomeLivroField.setEditable(false);
        panel.add(nomeLivroField);

        panel.add(new JLabel("Nome do Autor:"));
        JTextField nomeAutorField = new JTextField(livro.getAutor());
        nomeAutorField.setEditable(false);
        panel.add(nomeAutorField);

        panel.add(new JLabel("Código do livro:"));
        JTextField codigoLivroField = new JTextField(String.valueOf(livro.getCodigo()));
        codigoLivroField.setEditable(false);
        panel.add(codigoLivroField);

        if (livro.getEstado() == DISPONIVEL) {
            if (socio.getTipoSocio() == STANDARD) {
                this.dataPrevistaDevolucao = new Data(Data.today().addDays(10).toString());
            } else if (socio.getTipoSocio() == TipoSocio.PREMIUM) {
                this.dataPrevistaDevolucao = new Data(Data.today().addDays(20).toString());
            }

            panel.add(new JLabel("Data do empréstimo:"));
            JTextField dataEmprestimoField = new JTextField(String.valueOf(Data.today()));
            dataEmprestimoField.setEditable(false);
            panel.add(dataEmprestimoField);

            panel.add(new JLabel("Data de devolução prevista:"));
            JTextField dataDevolucaoField = new JTextField(String.valueOf(dataPrevistaDevolucao));
            dataDevolucaoField.setEditable(false);
            panel.add(dataDevolucaoField);
        } else if (livro.getEstado() == INDISPONIVEL) {
            reserva = new Reserva(socio, livro);

            panel.add(new JLabel("Número da reserva:"));
            JTextField numeroReservaField = new JTextField(String.valueOf(reserva.getNumeroReserva()));
            numeroReservaField.setEditable(false);
            panel.add(numeroReservaField);
        }

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Voltar");

        backButton.addActionListener(e -> {
            ListaDeLivros listaDeLivros = new ListaDeLivros(utilizador, socio);
            listaDeLivros.abrirPaginaListaDeLivros();
            frame.dispose();
        });

        buttonsPanel.add(backButton);


        if (livro.getEstado() == DISPONIVEL) {
            JButton loanButton = new JButton("Registar emprestimo");
            loanButton.addActionListener(e -> {
                int maxEmprestimos = (socio.getTipoSocio() == STANDARD) ? 3 : 5;
                if (socio.getEmprestimos().size() >= maxEmprestimos) {
                    JOptionPane.showMessageDialog(frame,"Número máximo de emprestimos alcançado.");
                    PaginaPrincipal paginaPrincipal = new PaginaPrincipal(utilizador);
                    paginaPrincipal.abrirPaginaPrincipal();
                    frame.dispose();
                    return;
                }else {
                    emprestimo = new Emprestimo(socio, livro);
                    socio.addEmprestimo(emprestimo);
                    JOptionPane.showMessageDialog(frame, "Livro emprestado com sucesso.");

                    PaginaPrincipal paginaPrincipal = new PaginaPrincipal(utilizador);
                    paginaPrincipal.abrirPaginaPrincipal();
                    frame.dispose();
                }
            });
            buttonsPanel.add(loanButton);
        } else if (livro.getEstado() == INDISPONIVEL) {
            JButton reserveButton = new JButton("Reservar");
            reserveButton.addActionListener(e -> {
                socio.addReservas(reserva);
                JOptionPane.showMessageDialog(frame, "Livro reservado com sucesso.");

                PaginaPrincipal paginaPrincipal = new PaginaPrincipal(utilizador);
                paginaPrincipal.abrirPaginaPrincipal();
                frame.dispose();
            });
            buttonsPanel.add(reserveButton);
        }

        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonsPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
