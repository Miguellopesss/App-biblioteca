package pt.ipleiria.estg.dei.ei.esoft.livro;

public class Livro {
    private static int contador = 0;
    private String titulo;
    private String autor;
    private Genero genero;
    private Subgenero subGenero;
    private String editora;
    private String edicao;
    private int ano;
    private String isbn;
    private int codigo;
    private EstadoLivro estado;

    public Livro(String titulo, String autor, Genero genero, Subgenero subGenero, String editora, String edicao, int ano, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.subGenero = subGenero;
        this.editora = editora;
        this.edicao = edicao;
        this.ano = ano;
        this.isbn = isbn;
        this.codigo = ++contador;
        this.estado = EstadoLivro.DISPONIVEL;
    }


    public EstadoLivro getEstado() {
        return estado;
    }
    public void setEstado(EstadoLivro estado) {
        this.estado = estado;
    }
    public String getTitulo() {
        return titulo;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public Genero getGenero() {
        return genero;
    }
    public Subgenero getSubGenero() {
        return subGenero;
    }
    public String getEditora() {
        return editora;
    }
    public void setEditora(String editora) {
        this.editora = editora;
    }
    public String getEdicao() {
        return edicao;
    }
    public int getAno() {
        return ano;
    }
    public String getIsbn() {
        return isbn;
    }
    public int getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", genero='" + genero + '\'' +
                ", subGenero='" + subGenero + '\'' +
                ", editora='" + editora + '\'' +
                ", edicao='" + edicao + '\'' +
                ", ano=" + ano +
                ", isbn='" + isbn + '\'' +
                ", codigo=" + codigo +
                ", estado=" + estado +
                '}';
    }
}
