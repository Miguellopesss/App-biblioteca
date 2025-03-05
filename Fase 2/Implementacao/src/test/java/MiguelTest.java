import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipleiria.estg.dei.ei.esoft.Utilizador;
import pt.ipleiria.estg.dei.ei.esoft.data.Data;
import pt.ipleiria.estg.dei.ei.esoft.devolucao.Devolucao;
import pt.ipleiria.estg.dei.ei.esoft.emprestimo.Emprestimo;
import pt.ipleiria.estg.dei.ei.esoft.livro.EstadoLivro;
import pt.ipleiria.estg.dei.ei.esoft.livro.Livro;
import pt.ipleiria.estg.dei.ei.esoft.multa.EstadoMulta;
import pt.ipleiria.estg.dei.ei.esoft.multa.Multa;
import pt.ipleiria.estg.dei.ei.esoft.reserva.Reserva;
import pt.ipleiria.estg.dei.ei.esoft.socio.Socio;
import pt.ipleiria.estg.dei.ei.esoft.socio.EstadoAnuidade;
import pt.ipleiria.estg.dei.ei.esoft.socio.TipoSocio;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static pt.ipleiria.estg.dei.ei.esoft.livro.Genero.ACAO;
import static pt.ipleiria.estg.dei.ei.esoft.livro.Subgenero.AVENTURA;

public class MiguelTest {

    private Utilizador utilizador;
    private Socio socio;
    private Livro livro;
    private Livro livro1;
    private Reserva reserva;
    private Emprestimo emprestimo;
    private Devolucao devolucao;

    @BeforeEach
    public void setUp() {
        utilizador = new Utilizador();
        socio = new Socio("Socio 1", "123456789", "Morada 1", "912345678", "socio1@example.com", "EMAIL", TipoSocio.STANDARD);
        livro = new Livro("Livro 1", "Autor 1", ACAO, AVENTURA, "Editora 1", "1ª Edição", 2021, "ISBN001");
        livro1 = new Livro("Livro 2", "Autor 2", ACAO, AVENTURA, "Editora 1", "1ª Edição", 2021, "ISBN000");


        utilizador.addSocio(socio);
        utilizador.addLivro(livro);
        utilizador.addLivro(livro1);

        Data dataEmprestimo = new Data(2024, 5, 24);
        emprestimo = new Emprestimo(socio, livro, dataEmprestimo);

        reserva = new Reserva(socio, livro);
    }

    @Test
    public void testPagarAnuidade() {
        socio.pagarAnuidade();
        assertEquals(EstadoAnuidade.PAGO, socio.getStatusAnuidade(), "O status da anuidade deve ser PAGO");
    }

    @Test
    public void testRegistrarReserva() {

        assertNotNull(reserva, "A reserva não deve ser nula");
        assertEquals(socio, reserva.getSocio(), "O sócio da reserva deve ser o esperado");
        assertEquals(livro, reserva.getLivro(), "O livro da reserva deve ser o esperado");
    }

    @Test
    public void testReservaComLivroDisponivel() {
        Reserva reserva = new Reserva(socio, livro1);

        assertNull(reserva.getSocio(), "O sócio não deve conseguir realizar uma reserva com um livro disponível");
        assertEquals(EstadoLivro.DISPONIVEL, livro1.getEstado(), "O estado do livro deve permanecer DISPONIVEL");
    }

    @Test
    public void testCancelarReserva() {

        socio.removeReserva(reserva);
        assertFalse(socio.getReservas().contains(reserva), "A lista de reservas não deve conter a reserva cancelada");
    }

    @Test
    public void testVerMultas() {
        devolucao = new Devolucao(socio, livro, emprestimo);
        List<Multa> multas = new ArrayList<>();
        multas = socio.getMultas();
        Multa multa = socio.getMultas().stream()
                .filter(m -> m.getDevolucao().equals(devolucao))
                .findFirst()
                .orElse(null);

        assertNotNull(multas, "A lista de multas não deve ser nula");
        assertFalse(multas.isEmpty(), "A lista de multas não deve estar vazia");
        assertTrue(multas.contains(multa), "A lista de multas deve conter a multa adicionada");
    }

    @Test
    public void testPagarMulta() {
        devolucao = new Devolucao(socio, livro, emprestimo);
        Multa multa = socio.getMultas().stream()
                .filter(m -> m.getDevolucao().equals(devolucao))
                .findFirst()
                .orElse(null);

        assertNotNull(multa, "A multa não deve ser nula");
        socio.removeMulta(multa);
        assertEquals(EstadoMulta.PAGA, multa.getEstadoMulta(), "O estado da multa deve ser PAGO");
    }

    @Test
    public void testSocioComMultaNaoPodeFazerEmprestimo() {
        devolucao = new Devolucao(socio, livro, emprestimo);
        Emprestimo emprestimo = new Emprestimo(socio, livro);

        assertNull(emprestimo.getSocio(), "Sócio com multas não deve conseguir fazer empréstimo");
        assertNull(emprestimo.getLivro(), "Livro não deve ser emprestado a sócio com multas");
    }

    @Test
    public void testNifIndisponivel() {


        int numeroInicialSocios = utilizador.getSocios().size();
        Socio socio2 = new Socio("Socio 2", "123456789", "Morada 2", "922345678", "socio2@example.com", "EMAIL", TipoSocio.STANDARD);
        utilizador.addSocio(socio2);
        assertEquals(numeroInicialSocios, utilizador.getSocios().size(), "Não deve ser possível adicionar um sócio com NIF já existente");
    }

}