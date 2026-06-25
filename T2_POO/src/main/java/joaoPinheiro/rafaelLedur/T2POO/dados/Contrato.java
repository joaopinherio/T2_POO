package joaoPinheiro.rafaelLedur.T2POO.dados;

import java.util.Date;

public class Contrato {
    private int id;
    private Date data;
    private int periodo;
    private Cliente cliente;
    private Jogo jogo;
    private FormaPagamento formaPagamento;
    private PIX pix;
    private CartaoCredito cartaoCredito;

    public Contrato(int id, int periodo) {
        this.id = id;
        this.periodo = periodo;
    }

    public String descrever() {
        return id + ";" + periodo + ";" + cliente.getNumero() + ";" + jogo.getCodigo();
    }

    public String descreverRedux() {
        return id + ";" + periodo;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getPeriodo() {
        return this.periodo;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public Jogo getJogo() {
        return this.jogo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    /*O método calculaValorFinal() da classe Contrato calcula o valor final do contrato depende
da categoria do jogo e da forma de pagamento:*/

    public double calculaValorFinal(QuadroContrato quadroContrato){
        double valorDiario = jogo.getValorDiario();
        Categoria categoriaCon = jogo.getCategoria();

        if (categoriaCon.getExtenso().equals("AVENTURA")) {
            valorDiario += valorDiario * 0.05;
        }

        if(categoriaCon.getExtenso().equals("ESTRATEGIA")){
            valorDiario += valorDiario * 0.10;
        }

        if (categoriaCon.getExtenso().equals("CORRIDA")) {
            valorDiario += valorDiario * 0.15;
        }
        //Vamos usar atributos pois nao podemos puxar os metodos das subclases de FormaPagamento
        if(formaPagamento instanceof CartaoCredito){
            valorDiario += valorDiario * 0.05;
        }

        if(formaPagamento instanceof PIX){
            valorDiario -= valorDiario * 0.05;
        }

        //
        if (quadroContrato.numDeContratorPorCliente(cliente) >= 3) {
            valorDiario -= valorDiario * 0.03;
        }
        
        return periodo * valorDiario;
    }

}