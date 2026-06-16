package dados;

public class Contrato {
    private int id;
    private int periodo;
    private Cliente cliente;
    private Jogo jogo;

    public Contrato(int id, int periodo) {
        this.id = id;
        this.periodo = periodo;
    }

    public String descrever() {
        return id + ";" + periodo + ";" + cliente.getNumero() + ";" + jogo.getCodigo();
    }

    public String descreverRedux() {
        return id + ";" + periodo + ";" + cliente.getNumero() + ";" + jogo.getCodigo();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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

    /*O método calculaValorFinal() da classe Contrato calcula o valor final do contrato depende
da categoria do jogo e da forma de pagamento:*/

    //public void calculaValorFinal(){
        //double result = this.periodo * jogo.getValorDiario();
    //}

}