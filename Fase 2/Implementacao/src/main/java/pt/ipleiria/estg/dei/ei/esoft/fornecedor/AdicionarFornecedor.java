package pt.ipleiria.estg.dei.ei.esoft.fornecedor;

import pt.ipleiria.estg.dei.ei.esoft.Utilizador;

import javax.swing.*;
import java.awt.*;

public class AdicionarFornecedor {

    private Utilizador utilizador;

    public AdicionarFornecedor(Utilizador utilizador) {
        this.utilizador = utilizador;
    }

    public void abrirPaginaAdicionarFornecedor() {
        JFrame addFrame = new JFrame("Adicionar Fornecedor");
        addFrame.setSize(1100, 900);
        addFrame.setLayout(new GridBagLayout());
        addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField editoraField = new JTextField(20);
        JTextField distribuidoraField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        addFrame.add(new JLabel("Editora:"), gbc);

        gbc.gridx = 1;
        addFrame.add(editoraField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        addFrame.add(new JLabel("Distribuidora:"), gbc);

        gbc.gridx = 1;
        addFrame.add(distribuidoraField, gbc);

        JButton saveButton = new JButton("Adicionar fornecedor");
        JButton backButton = new JButton("Voltar");

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(backButton);
        buttonPanel.add(saveButton);
        addFrame.add(buttonPanel, gbc);

        saveButton.addActionListener(e -> {
            String editora = editoraField.getText().trim();
            String distribuidora = distribuidoraField.getText().trim();

            if (editora.isEmpty() || distribuidora.isEmpty()) {
                JOptionPane.showMessageDialog(addFrame, "Todos os campos são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                Fornecedor fornecedor = new Fornecedor(editora, distribuidora);
                utilizador.addFornecedor(fornecedor);
                JOptionPane.showMessageDialog(addFrame, "Fornecedor adicionado com sucesso.");
                addFrame.dispose();
                FornecedoresPagina fornecedoresPagina = new FornecedoresPagina(utilizador);
                fornecedoresPagina.abrirPaginaFornecedores();
            }
        });

        backButton.addActionListener(e -> {
            FornecedoresPagina fornecedoresPagina = new FornecedoresPagina(utilizador);
            fornecedoresPagina.abrirPaginaFornecedores();
            addFrame.dispose();
        });

        addFrame.setVisible(true);
    }
}
