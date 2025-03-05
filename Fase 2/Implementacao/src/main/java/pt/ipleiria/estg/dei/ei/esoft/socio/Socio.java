package pt.ipleiria.estg.dei.ei.esoft.socio;

import pt.ipleiria.estg.dei.ei.esoft.devolucao.Devolucao;
import pt.ipleiria.estg.dei.ei.esoft.emprestimo.Emprestimo;
import pt.ipleiria.estg.dei.ei.esoft.multa.EstadoMulta;
import pt.ipleiria.estg.dei.ei.esoft.multa.Multa;
import pt.ipleiria.estg.dei.ei.esoft.data.Data;
import pt.ipleiria.estg.dei.ei.esoft.reserva.Reserva;

import java.util.ArrayList;
import java.util.List;

import static pt.ipleiria.estg.dei.ei.esoft.socio.EstadoAnuidade.PAGO;
import static pt.ipleiria.estg.dei.ei.esoft.socio.EstadoAnuidade.POR_PAGAR;
import static pt.ipleiria.estg.dei.ei.esoft.socio.TipoSocio.PREMIUM;
import static pt.ipleiria.estg.dei.ei.esoft.socio.TipoSocio.STANDARD;

public class Socio {
    private static int contador = 0;

    private int numeroSocio;
    private String nome;
    private String nif;
    private String morada;
    private String telefone;
    private String email;
    private String notificacaoPor;
    private EstadoSocio estadoSocio;
    private List<Multa> multas;
    private List<Emprestimo> emprestimos;
    private List<Reserva> reservas;
    private List<Devolucao> devolucoes;
    private TipoSocio tipoSocio;
    private Data dataAnuidade;
    private EstadoAnuidade estadoAnuidade; // Use the enum for status

    public Socio(String nome, String nif, String morada, String telefone, String email, String notificacaoPor, TipoSocio tipoSocio){
        this(nome,nif,morada,telefone,email,notificacaoPor,tipoSocio,Data.today());
    }

    public Socio(String nome, String nif, String morada, String telefone, String email, String notificacaoPor, TipoSocio tipoSocio, Data dataAnuidade) {
        this.numeroSocio = ++contador;
        this.nome = nome;
        setNif(nif);
        this.morada = morada;
        setTelefone(telefone);
        setEmail(email);
        this.notificacaoPor = notificacaoPor;
        this.tipoSocio = tipoSocio;
        this.dataAnuidade = dataAnuidade;
        estadoAnuidade = PAGO;
        estadoSocio = EstadoSocio.ATIVO;
        this.multas = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.devolucoes = new ArrayList<>();
        this.pagarAnuidade();
    }

    public EstadoSocio getEstadoSocio() {
        return estadoSocio;
    }
    public void setEstadoSocio(EstadoSocio estadoSocio) {
        this.estadoSocio = estadoSocio;
    }
    public List<Devolucao> getDevolucoes() {
        return devolucoes;
    }
    public void addDevolucao(Devolucao devolucao) {
        if (devolucao != null) {
            this.devolucoes.add(devolucao);
        } else {
            System.out.println("Devolucao cannot be null");
        }
    }
    public void setEmail(String email) {
        if (isValidEmail(email)) {
            this.email = email;
        } else {
            System.out.println("Email inválido");
        }
    }
    public String getEmail() {
        return email;
    }
    public EstadoAnuidade getStatusAnuidade() {
        return estadoAnuidade;
    }
    public void pagarAnuidade() {

        this.dataAnuidade = this.dataAnuidade.addDays(365); // Add 365 days to the annuity date
        if (dataAnuidade.isBefore(Data.today())) {
            this.estadoAnuidade = POR_PAGAR;
        }else{
            this.estadoAnuidade = PAGO;
        }
    }
    public void setStatusAnuidade(EstadoAnuidade estadoAnuidade) {
        if (dataAnuidade.isBefore(Data.today())) {
            this.estadoAnuidade = POR_PAGAR;
        }
    }
    public Data getDataAnuidade() {
        return dataAnuidade;
    }
    public TipoSocio getTipoSocio() {
        return tipoSocio;
    }
    public void setTipoSocio(TipoSocio tipoSocio) {
        this.tipoSocio = tipoSocio;
    }
    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }
    public void addEmprestimo(Emprestimo emprestimo) {
        if (emprestimo != null) {
            this.emprestimos.add(emprestimo);
        } else {
            System.out.println("Emprestimo cannot be null");
        }
    }
    public void removeEmprestimo(Emprestimo emprestimo) {
        this.emprestimos.remove(emprestimo);
    }
    public void setNif(String nif) {
        if (isValidNIF(nif)) {
            this.nif = nif;
        } else {
            System.out.println("Invalid NIF provided.");
        }
    }
    public List<Reserva> getReservas() {
        return reservas;
    }
    public void addReservas(Reserva reserva) {
        if (reserva != null) {
            this.reservas.add(reserva);
        } else {
            System.out.println ("Reserva cannot be null");
        }
    }
    public void removeReserva(Reserva reserva) {
        if (this.reservas.contains(reserva)) {
            this.reservas.remove(reserva);
        } else {
            System.out.println("Erro: Reserva não encontrada na lista.");
        }
    }
    private boolean isValidNIF(String nif) {
        return nif != null && nif.matches("\\d{9}");
    }
    public void setTelefone(String telefone) {
        if (isValidTelefone(telefone)) {
            this.telefone = telefone;
        } else {
            System.out.println("Numero de telefone invalido.");
        }
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }
    private boolean isValidTelefone(String telefone) {
        return telefone != null && telefone.matches("\\d{9}");
    }
    public int getNumeroSocio() {
        return numeroSocio;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNif() {
        return nif;
    }
    public String getMorada() {
        return morada;
    }
    public void setMorada(String morada) {
        this.morada = morada;
    }
    public String getTelefone() {
        return telefone;
    }
    public String getNotificacaoPor() {
        return notificacaoPor;
    }
    public void setNotificacaoPor(String notificacaoPor) {
        this.notificacaoPor = notificacaoPor;
    }
    public List<Multa> getMultas() {
        return multas;
    }
    public void addMulta(Multa multa) {
        this.multas.add(multa);
    }
    public void removeMulta(Multa multa) {
        if (this.multas.contains(multa)) {
            multa.setEstadoMulta(EstadoMulta.PAGA);
        } else {
            System.out.println("Erro: Multa não encontrada na lista.");
        }
    }


    @Override
    public String toString() {
        return "Socio{" +
                "numeroSocio=" + numeroSocio +
                ", nome='" + nome + '\'' +
                ", nif='" + nif + '\'' +
                ", morada='" + morada + '\'' +
                ", telefone='" + telefone + '\'' +
                ", notificacaoPor='" + notificacaoPor + '\'' +
                ", tipoSocio='" + tipoSocio + '\'' +
                ", dataAnuidade=" + dataAnuidade +
                ", statusAnuidade='" + estadoAnuidade + '\'' +
                ", multas count=" + multas.size() +
                ", emprestimos count=" + emprestimos.size() +
                '}';
    }
}
