package pt.ipleiria.estg.dei.ei.esoft.multa;

import pt.ipleiria.estg.dei.ei.esoft.socio.Socio;
import pt.ipleiria.estg.dei.ei.esoft.livro.Livro;
import pt.ipleiria.estg.dei.ei.esoft.devolucao.Devolucao;

public class Multa {
    private static int contador = 0;

    private Socio socio;
    private Livro livro;
    private Devolucao devolucao;
    private int numeroMulta;
    private double valorMulta;
    private EstadoMulta estadoMulta;

    public Multa(Socio socio, Livro livro, Devolucao devolucao, double valorMulta) {
        this.socio = socio;
        this.livro = livro;
        this.devolucao = devolucao;
        this.valorMulta = valorMulta;
        estadoMulta = EstadoMulta.POR_PAGAR;
        this.numeroMulta = ++contador;
    }

    public Socio getSocio() {
        return socio;
    }
    public Livro getLivro() {
        return livro;
    }
    public Devolucao getDevolucao() {
        return devolucao;
    }
    public int getNumeroMulta() {
        return numeroMulta;
    }
    public double getValorMulta() {
        return valorMulta;
    }
    public EstadoMulta getEstadoMulta() {
        return estadoMulta;
    }
    public void setSocio(Socio socio) {
        this.socio = socio;
    }
    public void setLivro(Livro livro) {
        this.livro = livro;
    }
    public void setDevolucao(Devolucao devolucao) {
        this.devolucao = devolucao;
    }
    public void setEstadoMulta(EstadoMulta estadoMulta) {
        this.estadoMulta = estadoMulta;
    }

    @Override
    public String toString() {
        return "Multa{" +
                "socio=" + socio +
                ", livro=" + livro +
                ", devolucao=" + devolucao +
                ", numeroMulta=" + numeroMulta +
                ", valorMulta=" + valorMulta +
                '}';
    }
}
