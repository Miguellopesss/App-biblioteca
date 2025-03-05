package pt.ipleiria.estg.dei.ei.esoft;

import pt.ipleiria.estg.dei.ei.esoft.data.Data;
import pt.ipleiria.estg.dei.ei.esoft.devolucao.Devolucao;
import pt.ipleiria.estg.dei.ei.esoft.emprestimo.Emprestimo;
import pt.ipleiria.estg.dei.ei.esoft.fornecedor.Fornecedor;
import pt.ipleiria.estg.dei.ei.esoft.livro.Livro;
import pt.ipleiria.estg.dei.ei.esoft.reserva.Reserva;
import pt.ipleiria.estg.dei.ei.esoft.socio.Socio;
import pt.ipleiria.estg.dei.ei.esoft.socio.TipoSocio;

import javax.swing.*;

import static pt.ipleiria.estg.dei.ei.esoft.livro.Genero.*;
import static pt.ipleiria.estg.dei.ei.esoft.livro.Subgenero.*;


/*
O sócio 1 tem 1 emprestimo realizado do livro 3.
O sócio 2 tem multas pendentes, logo não consegue realizar emprestimos.
O sócio 3 tem 1 emprestimo realizado do livro 7.
O sócio 4 tem 1 reserva do livro 1 e 1 emprestimo do livro 4.
O sócio 5 tem a anuidade em atraso, logo não consegue realizar emprestimos.

*/

public class Main {
    public static void main(String[] args) {
        Utilizador utilizador = new Utilizador();
        // Criar livros
        Livro livro1 = new Livro("A Jornada", "Carlos Silva", COMEDIA, AVENTURA, "Editora 1", "1ª Edição", 2020, "ISBN001");
        Livro livro2 = new Livro("Vento do Norte", "Ana Costa", ACAO, COMEDIA_ROMANTICA, "Editora 2", "1ª Edição", 2019, "ISBN002");
        Livro livro3 = new Livro("Caminhos", "João Oliveira", ACAO, AVENTURA, "Editora 3", "1ª Edição", 2021, "ISBN003");
        Livro livro4 = new Livro("Noite Sombria", "Mariana Sousa", SUSPENSE, TERROR, "Editora 4", "3ª Edição", 2020, "ISBN004");
        Livro livro5 = new Livro("Sombras do Medo", "Ricardo Mendes", SUSPENSE, TERROR, "Editora 5", "1ª Edição", 2022, "ISBN005");
        Livro livro6 = new Livro("Horizontes", "Beatriz Martins", ACAO, AVENTURA, "Editora 1", "1ª Edição", 2021, "ISBN006");
        Livro livro7 = new Livro("Dias Azuis", "João Oliveira", ACAO, AVENTURA, "Editora 1", "1ª Edição", 2021, "ISBN007");

        // Adicionar livros ao utilizador
        utilizador.addLivro(livro1);
        utilizador.addLivro(livro2);
        utilizador.addLivro(livro3);
        utilizador.addLivro(livro4);
        utilizador.addLivro(livro5);
        utilizador.addLivro(livro6);
        utilizador.addLivro(livro7);

        // Criar sócios
        Socio socio1 = new Socio("Socio 1", "123456789", "Morada 1", "912345678", "socio1@example.com", "EMAIL", TipoSocio.STANDARD);
        Socio socio2 = new Socio("Socio 2", "223456789", "Morada 2", "922345678", "socio2@example.com", "EMAIL", TipoSocio.STANDARD);
        Socio socio3 = new Socio("Socio 3", "323456789", "Morada 3", "932345678", "socio3@example.com", "SMS", TipoSocio.PREMIUM);
        Socio socio4 = new Socio("Socio 4", "423456789", "Morada 4", "942345678", "socio4@example.com", "EMAIL", TipoSocio.PREMIUM);
        Socio socio5 = new Socio("Socio 5", "523456789", "Morada 5", "952345678", "socio5@example.com", "SMS", TipoSocio.PREMIUM, new Data(2023,5,25));

        // Adicionar sócios ao utilizador
        utilizador.addSocio(socio1);
        utilizador.addSocio(socio2);
        utilizador.addSocio(socio3);
        utilizador.addSocio(socio4);
        utilizador.addSocio(socio5);

        // Criar fornecedores
        Fornecedor fornecedor1 = new Fornecedor("Editora 1", "Distribuidora 1");
        Fornecedor fornecedor2 = new Fornecedor("Editora 2", "Distribuidora 2");
        Fornecedor fornecedor3 = new Fornecedor("Editora 3", "Distribuidora 3");

        // Adicionar livros aos fornecedores
        fornecedor1.addLivro(livro1);
        fornecedor2.addLivro(livro2);
        fornecedor3.addLivro(livro3);

        // Adicionar fornecedores ao utilizador
        utilizador.addFornecedor(fornecedor1);
        utilizador.addFornecedor(fornecedor2);
        utilizador.addFornecedor(fornecedor3);

        // Criar um empréstimo
        Emprestimo emprestimo = new Emprestimo(socio2, livro1,new Data(2024,5,24));
        socio2.addEmprestimo(emprestimo);
        Emprestimo emprestimo1 = new Emprestimo(socio3, livro2);
        socio3.addEmprestimo(emprestimo1);
        Emprestimo emprestimo2 = new Emprestimo(socio1, livro3);
        socio1.addEmprestimo(emprestimo2);
        Emprestimo emprestimo4 = new Emprestimo(socio3, livro7);
        socio1.addEmprestimo(emprestimo4);

        Reserva reserva = new Reserva(socio4,livro1);
        socio4.addReservas(reserva);

        Devolucao devolucao = new Devolucao(socio2,livro1,emprestimo);
        Devolucao devolucao1 = new Devolucao(socio3,livro2,emprestimo1);

        Emprestimo emprestimo3 = new Emprestimo(socio4, livro4);
        socio4.addEmprestimo(emprestimo3);

        System.out.println("Livros:");
        for (Livro livro : utilizador.getLivros()) {
            System.out.println(livro);
        }

        System.out.println("\nSócios:");
        for (Socio socio : utilizador.getSocios()) {
            System.out.println(socio);
        }

        System.out.println("\nFornecedores:");
        for (Fornecedor fornecedor : utilizador.getFornecedores()) {
            System.out.println(fornecedor);
        }

        SwingUtilities.invokeLater(() -> new PaginaPrincipal(utilizador).abrirPaginaPrincipal());
    }
}