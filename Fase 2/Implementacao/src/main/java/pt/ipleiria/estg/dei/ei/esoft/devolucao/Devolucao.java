package pt.ipleiria.estg.dei.ei.esoft.devolucao;

import pt.ipleiria.estg.dei.ei.esoft.multa.Multa;
import pt.ipleiria.estg.dei.ei.esoft.socio.Socio;
import pt.ipleiria.estg.dei.ei.esoft.livro.Livro;
import pt.ipleiria.estg.dei.ei.esoft.emprestimo.Emprestimo;
import pt.ipleiria.estg.dei.ei.esoft.data.Data;

import java.time.temporal.ChronoUnit;

import static pt.ipleiria.estg.dei.ei.esoft.livro.EstadoLivro.DISPONIVEL;

public class Devolucao {
    private Socio socio;
    private Livro livro;
    private Emprestimo emprestimo;
    private Data dataDevolucao;

    public Devolucao(Socio socio, Livro livro, Emprestimo emprestimo) {
        if (socio == null) {
            System.out.println("Socio cannot be null.");
            return;
        }
        if (livro == null) {
            System.out.println("Livro cannot be null.");
            return;
        }
        if (emprestimo == null) {
            System.out.println("Emprestimo cannot be null.");
            return;
        }
        if (socio.getNumeroSocio() != emprestimo.getSocio().getNumeroSocio()) {
            System.out.println("Socio does not match the socio associated with the emprestimo.");
            return;
        }
        if (livro.getCodigo() != emprestimo.getLivro().getCodigo()) {
            System.out.println("Livro does not match the livro associated with the emprestimo.");
            return;
        }
        this.socio = socio;
        this.livro = livro;
        this.emprestimo = emprestimo;
        this.dataDevolucao = Data.today();
        livro.setEstado(DISPONIVEL);
        socio.removeEmprestimo(emprestimo);
        socio.addDevolucao(this);
        if (emprestimo.getDataPrevistaDevolucao().isBefore(this.dataDevolucao)) {
            long daysLate = ChronoUnit.DAYS.between(emprestimo.getDataPrevistaDevolucao().getDate(), this.dataDevolucao.getDate());
            double valorMulta = daysLate * 2.0; // 2 euros per day
            Multa multa = new Multa(socio, livro, this, valorMulta);
            socio.addMulta(multa);
            System.out.println("Multa adicionada: " + multa);
        }
    }

    public Socio getSocio() {
        return socio;
    }
    public Livro getLivro() {
        return livro;
    }
    public Emprestimo getEmprestimo() {
        return emprestimo;
    }
    public Data getDataDevolucao() {
        return dataDevolucao;
    }
    public void setSocio(Socio socio) {
        this.socio = socio;
    }
    public void setLivro(Livro livro) {
        this.livro = livro;
    }
    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    @Override
    public String toString() {
        return "Devolucao{" +
                "socio=" + socio +
                ", livro=" + livro +
                ", emprestimo=" + emprestimo +
                ", dataDevolucao=" + dataDevolucao +
                '}';
    }
}
