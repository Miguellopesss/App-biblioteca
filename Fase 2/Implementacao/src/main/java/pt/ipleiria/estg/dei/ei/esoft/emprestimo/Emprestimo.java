package pt.ipleiria.estg.dei.ei.esoft.emprestimo;

import pt.ipleiria.estg.dei.ei.esoft.data.Data;
import pt.ipleiria.estg.dei.ei.esoft.livro.Livro;
import pt.ipleiria.estg.dei.ei.esoft.socio.Socio;
import pt.ipleiria.estg.dei.ei.esoft.socio.TipoSocio;

import static pt.ipleiria.estg.dei.ei.esoft.livro.EstadoLivro.DISPONIVEL;
import static pt.ipleiria.estg.dei.ei.esoft.livro.EstadoLivro.INDISPONIVEL;
import static pt.ipleiria.estg.dei.ei.esoft.socio.EstadoAnuidade.PAGO;
import static pt.ipleiria.estg.dei.ei.esoft.socio.TipoSocio.STANDARD;

public class Emprestimo {
    private Socio socio;
    private Livro livro;
    private Data dataEmprestimo;
    private Data dataPrevistaDevolucao;

    public Emprestimo(Socio socio, Livro livro) {
        this(socio, livro, Data.today());
    }

    public Emprestimo(Socio socio, Livro livro, Data dataEmprestimo) {
        if (socio == null) {
            System.out.println("Socio não pode ser nulo.");
            return;
        }
        if (livro == null) {
            System.out.println("Livro não pode ser nulo.");
            return;
        }
        if (!socio.getMultas().isEmpty()) {
            System.out.println("Emprestimo não pode ser criado porque o sócio tem multas pendentes.");
            return;
        }
        if (socio.getStatusAnuidade() != PAGO) {
            System.out.println("Emprestimo não pode ser criado porque o sócio tem a anuidade em atraso.");
            return;
        }
        int maxEmprestimos = (socio.getTipoSocio() == STANDARD) ? 3 : 5;
        if (socio.getEmprestimos().size() >= maxEmprestimos) {
            System.out.println("Emprestimo não pode ser criado porque o sócio atingiu o limite de emprestimos.");
            return;
        }
        if (!livro.getEstado().equals(DISPONIVEL)) {
            System.out.println("Emprestimo não pode ser criádo porque o livro está indisponivel");
            return;
        }
        this.socio = socio;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        if (socio.getTipoSocio() == STANDARD) {
            this.dataPrevistaDevolucao = new Data(this.dataEmprestimo.addDays(10).toString());
        } else if (socio.getTipoSocio() == TipoSocio.PREMIUM) {
            this.dataPrevistaDevolucao = new Data(this.dataEmprestimo.addDays(20).toString());
        }
        livro.setEstado(INDISPONIVEL);
    }

    public Socio getSocio() {
        return socio;
    }
    public Livro getLivro() {
        return livro;
    }
    public Data getDataEmprestimo() {
        return dataEmprestimo;
    }
    public Data getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }
    public void setSocio(Socio socio) {
        this.socio = socio;
    }
    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "socio=" + (socio != null ? "Socio Numero: " + socio.getNumeroSocio() : "null") +
                ", livro=" + livro +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataDevolucao=" + dataPrevistaDevolucao +
                '}';
    }
}
