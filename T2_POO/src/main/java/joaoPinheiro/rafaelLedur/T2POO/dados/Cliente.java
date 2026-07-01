package joaoPinheiro.rafaelLedur.T2POO.dados;

import java.io.Serializable;

public abstract class Cliente implements Serializable {
    private int numero;
    private String nome;
    private String email;
    private FormaPagamento formaPagamento;
    private double valorMontante;

    public Cliente(int numero, String nome, String email) {
        this.numero = numero;
        this.nome = nome;
        this.email = email;
    }
    
    public abstract String getTipo();

    public abstract String getGovId();

    public abstract String descrever();

    public String getNomeFantasia(){
        return null;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String descreverRedux() {
        return numero + ";" + nome + ";" + email;
    }
    
    public int getNumero() {
        return this.numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setValorMontante(double valorMontante){
        this.valorMontante = valorMontante;
    }

    public double getValorMontante(){
        return this.valorMontante;
    }

}