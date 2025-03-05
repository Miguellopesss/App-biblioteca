import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipleiria.estg.dei.ei.esoft.Utilizador;
import pt.ipleiria.estg.dei.ei.esoft.data.Data;
import pt.ipleiria.estg.dei.ei.esoft.devolucao.Devolucao;
import pt.ipleiria.estg.dei.ei.esoft.emprestimo.Emprestimo;
import pt.ipleiria.estg.dei.ei.esoft.livro.Livro;
import pt.ipleiria.estg.dei.ei.esoft.livro.EstadoLivro;
import pt.ipleiria.estg.dei.ei.esoft.socio.Socio;
import pt.ipleiria.estg.dei.ei.esoft.socio.TipoSocio;


import static org.junit.jupiter.api.Assertions.*;
import static pt.ipleiria.estg.dei.ei.esoft.livro.Genero.ACAO;
import static pt.ipleiria.estg.dei.ei.esoft.livro.Subgenero.AVENTURA;

public class JoaoTest {

    private Utilizador utilizador;
    private Socio socio;
    private Socio socio1;
    private Livro livro;

    @BeforeEach
    public void setUp() {
        utilizador = new Utilizador();
        socio = new Socio("João", "123456789", "Morada Rua Liz", "912345678", "joao@example.com", "EMAIL", TipoSocio.STANDARD);
        socio1 = new Socio("Socio 1", "123456780", "Morada 1", "912345670", "socio1@example.com", "EMAIL", TipoSocio.STANDARD);

        livro = new Livro("Livro 1", "Autor 1", ACAO, AVENTURA, "Editora 1", "1ª Edição", 2021, "ISBN001");


        utilizador.addSocio(socio);
        utilizador.addSocio(socio1);
        utilizador.addLivro(livro);
    }

    @Test
    public void testCriarEmprestimo() {
        Emprestimo emprestimo = new Emprestimo(socio, livro);

        assertNotNull(emprestimo, "O empréstimo não deve ser nulo");
        assertEquals(socio, emprestimo.getSocio(), "O sócio do empréstimo deve ser o esperado");
        assertEquals(livro, emprestimo.getLivro(), "O livro do empréstimo deve ser o esperado");
        assertEquals(EstadoLivro.INDISPONIVEL, livro.getEstado(), "O estado do livro deve ser INDISPONIVEL");
    }

    @Test
    public void testEmprestimoComLivroIndisponivel() {
        Emprestimo emprestimo = new Emprestimo(socio, livro);
        Emprestimo emprestimo1 = new Emprestimo(socio1, livro);

        assertNull(emprestimo1.getSocio(), "O sócio não deve conseguir realizar um empréstimo com um livro indisponível");
        assertEquals(EstadoLivro.INDISPONIVEL, livro.getEstado(), "O estado do livro deve permanecer INDISPONIVEL");
    }

    @Test
    public void testAdicionarLivro() {
        utilizador.addLivro(livro);
        assertTrue(utilizador.getLivros().contains(livro), "O livro deve ser adicionado ao utilizador");
    }

    @Test
    public void testRemoverLivro() {
        utilizador.removeLivro(livro);
        assertEquals(EstadoLivro.INATIVO, livro.getEstado(), "O estado do livro deve ser INATIVO");
    }

    @Test
    public void testRegistrarDevolucao() {
        Data dataEmprestimo = new Data(2024, 5, 24);
        Emprestimo emprestimo = new Emprestimo(socio, livro, dataEmprestimo);
        Devolucao devolucao = new Devolucao(socio, livro, emprestimo);


        assertTrue(socio.getDevolucoes().contains(devolucao), "A devolução deve ser registada para o sócio");
        assertEquals(EstadoLivro.DISPONIVEL, livro.getEstado(), "O estado do livro deve ser DISPONIVEL após a devolução");
    }

    @Test
    public void testProcurarSocioPeloNumero() {
        Socio resultado = utilizador.getSocioByNumeroSocio(socio.getNumeroSocio());
        assertNotNull(resultado, "O sócio não deve ser nulo");
        assertEquals(socio, resultado, "O sócio encontrado deve ser o esperado");
    }

    @Test
    public void testDataPrevistaParaSocioStandard() {

        Emprestimo emprestimo = new Emprestimo(socio1, livro);
        Data dataPrevistaEsperada = Data.today().addDays(10);
        assertEquals(String.valueOf(dataPrevistaEsperada), String.valueOf(emprestimo.getDataPrevistaDevolucao()), "A data de devolução prevista para sócio STANDARD deve ser 10 dias após a data do empréstimo");
    }

    @Test
    public void testEmailInvalido() {

        int numeroInicialSocios = utilizador.getSocios().size();
        Socio socio2 = new Socio("Socio 1", "123456700", "Morada 1", "912345600", "socio1example.com", "EMAIL", TipoSocio.STANDARD);
        utilizador.addSocio(socio2);
        assertEquals(numeroInicialSocios, utilizador.getSocios().size(), "Não deve ser possível adicionar um sócio com e-mail inválido");
    }


}