package joaoPinheiro.rafaelLedur.T2POO.dados;

public class FormaPagamento {
    private int cod;
    private int diaVencimento;
    private Cliente cliente;

    public FormaPagamento(int cod, int diaVencimento) {
        this.cod = cod;
        this.diaVencimento = diaVencimento;
    }

    public String descrever() {
        return getCod() + ";" + getDiaVencimento();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(int diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public String getCsv(){
        return cod + ";" + diaVencimento + ";" + cliente.getNumero();
    }
}