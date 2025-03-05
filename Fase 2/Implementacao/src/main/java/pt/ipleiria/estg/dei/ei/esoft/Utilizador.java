package pt.ipleiria.estg.dei.ei.esoft;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.ei.esoft.fornecedor.EstadoFornecedor;
import pt.ipleiria.estg.dei.ei.esoft.socio.EstadoSocio;
import pt.ipleiria.estg.dei.ei.esoft.socio.Socio;
import pt.ipleiria.estg.dei.ei.esoft.livro.Livro;
import pt.ipleiria.estg.dei.ei.esoft.fornecedor.Fornecedor;

import java.util.List;
import java.util.stream.Collectors;

import static pt.ipleiria.estg.dei.ei.esoft.livro.EstadoLivro.INATIVO;

public class Utilizador {
    private List<Socio> socios;
    private List<Fornecedor> fornecedores;
    private List<Livro> livros;


    public Utilizador() {
        this.socios = new ArrayList<>();
        this.fornecedores = new ArrayList<>();
        this.livros = new ArrayList<>();
    }

    public Socio getSocioByNumeroSocio(int numeroSocio) {
        for (Socio s : socios) {
            if (s.getNumeroSocio() == numeroSocio) {
                return s;
            }
        }
        System.out.println("No Socio found with numeroSocio: " + numeroSocio);
        return null;
    }
    public Fornecedor getNumeroFornecedor(int numeroFornecedor) {
        for (Fornecedor f : fornecedores) {
            if (f.getNumeroFornecedor() == numeroFornecedor) {
                return f;
            }
        }
        System.out.println("No Fornecedor found with numeroFornecedor: " + numeroFornecedor);
        return null;
    }
    public void addSocio(Socio socio) {
        if (socio.getNif() == null || socio.getTelefone() == null || socio.getEmail() == null) {
            return;
        }

        for (Socio s : socios) {
            if (s.getNif().equals(socio.getNif())) {
                System.out.println("Socio com NIF " + socio.getNif() + " já existe.");
                return;
            }
            if (s.getTelefone().equals(socio.getTelefone())) {
                System.out.println("Socio com telefone " + socio.getTelefone() + " já existe.");
                return;
            }
        }

        socios.add(socio);
    }
    public void removeSocio(Socio socio) {
        if (!socio.getMultas().isEmpty()) {
            System.out.println("Socio with numeroSocio " + socio.getNumeroSocio() + " cannot be removed because they have outstanding multas.");
            return;
        }
        socio.setEstadoSocio(EstadoSocio.INATIVO);
    }
    public List<Socio> getSocios() {
        return socios;
    }
    public void addFornecedor(Fornecedor fornecedor) {
        if (fornecedor == null) {
            System.out.println("Fornecedor não pode ser null.");
            return;
        }
        fornecedores.add(fornecedor);
    }
    public void removeFornecedor(Fornecedor fornecedor) {
        fornecedor.setEstadoFornecedor(EstadoFornecedor.INATIVO);
    }
    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }
    public List<Fornecedor> getFornecedoresAtivos() {
        return fornecedores.stream()
                .filter(fornecedor -> fornecedor.getEstadoFornecedor() == EstadoFornecedor.ATIVO)
                .collect(Collectors.toList());
    }
    public void addLivro(Livro livro) {
        livros.add(livro);
    }
    public void removeLivro(Livro livro) {
        if (livro != null) {
            livro.setEstado(INATIVO);
        } else {
            System.out.println("Livro não pode ser null.");
        }
    }
    public List<Livro> getLivros() {
        return livros;
    }

}
