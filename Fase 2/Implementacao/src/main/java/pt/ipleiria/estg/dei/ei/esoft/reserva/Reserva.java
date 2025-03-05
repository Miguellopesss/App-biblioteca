package pt.ipleiria.estg.dei.ei.esoft.reserva;

import pt.ipleiria.estg.dei.ei.esoft.socio.Socio;
import pt.ipleiria.estg.dei.ei.esoft.livro.Livro;

import static pt.ipleiria.estg.dei.ei.esoft.livro.EstadoLivro.INDISPONIVEL;
import static pt.ipleiria.estg.dei.ei.esoft.socio.EstadoAnuidade.PAGO;

public class Reserva {
    private static int contador = 0;

    private Socio socio;
    private Livro livro;
    private int numeroReserva;

    public Reserva(Socio socio, Livro livro) {

        if (socio == null) {
            System.out.println("Socio cannot be null.");
            return;
        }
        if (livro == null) {
            System.out.println("Livro cannot be null.");
            return;
        }
        if (!socio.getMultas().isEmpty()) {
            System.out.println("Reserva cannot be created because the socio has outstanding fines.");
            return;
        }
        if (!socio.getStatusAnuidade().equals(PAGO)) {
            System.out.println("Reserva cannot be created because the annuity status is not paid.");
            return;
        }
        if (livro.getEstado() != INDISPONIVEL) {
            System.out.println("Reserva não pode ser criada porque o livro está disponivel.");
            return;
        }
        this.socio = socio;
        this.livro = livro;
        this.numeroReserva = ++contador;  // Increment and assign the next reservation number
    }


    public Socio getSocio() {
        return socio;
    }
    public Livro getLivro() {
        return livro;
    }
    public int getNumeroReserva() {
        return numeroReserva;
    }
    public void setSocio(Socio socio) {
        this.socio = socio;
    }
    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "socio=" + socio +
                ", livro=" + livro +
                ", numeroReserva=" + numeroReserva +
                '}';
    }
}
