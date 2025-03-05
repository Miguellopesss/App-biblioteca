import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipleiria.estg.dei.ei.esoft.Utilizador;
import pt.ipleiria.estg.dei.ei.esoft.data.Data;
import pt.ipleiria.estg.dei.ei.esoft.emprestimo.Emprestimo;
import pt.ipleiria.estg.dei.ei.esoft.fornecedor.EstadoFornecedor;
import pt.ipleiria.estg.dei.ei.esoft.fornecedor.Fornecedor;
import pt.ipleiria.estg.dei.ei.esoft.livro.EstadoLivro;
import pt.ipleiria.estg.dei.ei.esoft.livro.Livro;
import pt.ipleiria.estg.dei.ei.esoft.socio.EstadoSocio;
import pt.ipleiria.estg.dei.ei.esoft.socio.Socio;
import pt.ipleiria.estg.dei.ei.esoft.socio.TipoSocio;


import static org.junit.jupiter.api.Assertions.*;
import static pt.ipleiria.estg.dei.ei.esoft.livro.Genero.ACAO;
import static pt.ipleiria.estg.dei.ei.esoft.livro.Subgenero.AVENTURA;

public class JorgeTest {

    private Utilizador utilizador;
    private Fornecedor fornecedor;
    private Socio socio;
    private Livro livro;
    private Socio socioComAnuidadePorPagar;


    @BeforeEach
    public void setUp() {
        utilizador = new Utilizador();
        socio = new Socio("Socio 1", "123456789", "Morada 1", "912345678", "socio1@example.com", "EMAIL", TipoSocio.PREMIUM);
        fornecedor = new Fornecedor("Editora 1", "Distribuidora 1");
        livro = new Livro("Livro 1", "Autor 1", ACAO, AVENTURA, "Editora 1", "1ª Edição", 2021, "ISBN001");
        utilizador.addLivro(livro);

        utilizador.addFornecedor(fornecedor);
        utilizador.addSocio(socio);

    }

    @Test
    public void testAdicionarSocio() {
        Socio socio = new Socio("Socio 1", "123456780", "Morada 1", "912345670", "socio1@example.com", "EMAIL", TipoSocio.STANDARD);
        utilizador.addSocio(socio);

        assertTrue(utilizador.getSocios().contains(socio), "O sócio deve ser adicionado com sucesso.");
    }

    @Test
    public void testRemoverSocio() {
        utilizador.removeSocio(socio);

        assertEquals(EstadoSocio.INATIVO, socio.getEstadoSocio(), "O sócio deve ser removido com sucesso.");
    }


    @Test
    public void testAdicionarFornecedor() {
        Fornecedor fornecedor = new Fornecedor("Editora 1", "Distribuidora 1");
        utilizador.addFornecedor(fornecedor);

        assertTrue(utilizador.getFornecedores().contains(fornecedor), "O fornecedor deve ser adicionado com sucesso.");
    }

    @Test
    public void testRemoverFornecedor() {
        utilizador.removeFornecedor(fornecedor);
        assertEquals(EstadoFornecedor.INATIVO, fornecedor.getEstadoFornecedor(), "O fornecedor deve ser removido com sucesso.");
    }

    @Test
    public void testEditarSocio() {
        socio.setNome("Novo Nome");
        socio.setMorada("Nova Morada");
        socio.setTelefone("987654321");
        socio.setEmail("novoemail@example.com");
        socio.setNotificacaoPor("SMS");

        assertEquals("Novo Nome", socio.getNome(), "O nome do sócio deve ser atualizado.");
        assertEquals("Nova Morada", socio.getMorada(), "A morada do sócio deve ser atualizada.");
        assertEquals("987654321", socio.getTelefone(), "O telefone do sócio deve ser atualizado.");
        assertEquals("novoemail@example.com", socio.getEmail(), "O email do sócio deve ser atualizado.");
        assertEquals("SMS", socio.getNotificacaoPor(), "O tipo de notificação do sócio deve ser atualizado.");
    }

    @Test
    public void testDataPrevistaParaSocioPremium() {

        Emprestimo emprestimo = new Emprestimo(socio, livro);
        Data dataPrevistaEsperada = Data.today().addDays(20);
        assertEquals(String.valueOf(dataPrevistaEsperada), String.valueOf(emprestimo.getDataPrevistaDevolucao()), "A data de devolução prevista para sócio PREMIUM deve ser 20 dias após a data do empréstimo");
    }

    @Test
    public void testSocioComAnuidadePorPagarNaoPodeRealizarEmprestimo() {

        socioComAnuidadePorPagar = new Socio("Socio 2", "223456789", "Morada 2", "922345678", "socio2@example.com", "EMAIL", TipoSocio.STANDARD,new Data(2023,5,1));
        utilizador.addSocio(socioComAnuidadePorPagar);

        Emprestimo emprestimo = new Emprestimo(socioComAnuidadePorPagar, livro);

        assertNull(emprestimo.getSocio(), "O sócio com anuidade por pagar não deve conseguir realizar um empréstimo");
        assertEquals(EstadoLivro.DISPONIVEL, livro.getEstado(), "O estado do livro deve permanecer DISPONIVEL");
    }

    @Test
    public void testNumeroTelefoneInvalido() {

        int numeroInicialSocios = utilizador.getSocios().size();
        Socio socio = new Socio("Socio 1", "123456789", "Morada 1", "91234", "socio1@example.com", "EMAIL", TipoSocio.STANDARD);
        utilizador.addSocio(socio);
        assertEquals(numeroInicialSocios, utilizador.getSocios().size(), "Não deve ser possível adicionar um sócio com número de telefone inválido");
    }

}