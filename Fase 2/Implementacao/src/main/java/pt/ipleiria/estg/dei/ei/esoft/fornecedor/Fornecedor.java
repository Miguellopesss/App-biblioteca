package pt.ipleiria.estg.dei.ei.esoft.fornecedor;

import pt.ipleiria.estg.dei.ei.esoft.livro.Livro;

import java.util.ArrayList;
import java.util.List;

import static pt.ipleiria.estg.dei.ei.esoft.fornecedor.EstadoFornecedor.ATIVO;

public class Fornecedor {
    private static int contador = 0;

    private String editora;
    private String distribuidora;
    private int numeroFornecedor;
    private List<Livro> livros;
    private EstadoFornecedor estadoFornecedor;

    public Fornecedor(String editora, String distribuidora) {
        this.editora = editora;
        this.distribuidora = distribuidora;
        this.numeroFornecedor = ++contador;
        this.livros = new ArrayList<>();
        estadoFornecedor = ATIVO;
    }

    public String getEditora() {
        return editora;
    }
    public String getDistribuidora() {
        return distribuidora;
    }
    public int getNumeroFornecedor() {
        return numeroFornecedor;
    }
    public List<Livro> getLivros() {
        return livros;
    }
    public void setEditora(String editora) {
        this.editora = editora;
    }
    public void addLivro(Livro livro) {
        if (livro == null) {
            System.out.println("Livro não pode ser null.");
            return;
        }
        this.livros.add(livro);
    }
    public EstadoFornecedor getEstadoFornecedor() {
        return estadoFornecedor;
    }
    public void setEstadoFornecedor(EstadoFornecedor estadoFornecedor) {
        this.estadoFornecedor = estadoFornecedor;
    }

     @Override
    public String toString() {
        return "Fornecedor{" +
                "editora='" + editora + '\'' +
                ", distribuidora='" + distribuidora + '\'' +
                ", numeroFornecedor=" + numeroFornecedor +
                ", livros=" + livros +
                '}';
    }
}
