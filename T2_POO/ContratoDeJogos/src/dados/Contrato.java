package dados;

public class Contrato {
    private int id;
    private int periodo;
    private Cliente cliente;
    private Jogo jogo;
    private PIX pix;
    private CartaoCredito cartaoCredito;
    private QuadroContrato quadro;

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

    /*
     * O método calculaValorFinal() da classe Contrato calcula o valor final do
     * contrato depende
     * da categoria do jogo e da forma de pagamento:
     */

    public double calculaValorFinal() {
        double result = 0;
        double valorDiario = jogo.getValorDiario();
        Categoria categoriaCon = jogo.getCategoria();

        if (categoriaCon.getExtenso().equals("AVENTURA")) {
            valorDiario += valorDiario * 0.05;
            result += this.periodo * valorDiario;
        }

        if (categoriaCon.getExtenso().equals("AVENTURA")) {
            valorDiario += valorDiario * 0.10;
            result += this.periodo * valorDiario;
        }

        if (categoriaCon.getExtenso().equals("CORRIDA")) {
            valorDiario += valorDiario * 0.15;
            result += this.periodo * valorDiario;
        }

        long cont = quadro.pesquisaTodosContratantes().stream().filter(c -> c.equals(cliente)).count();

        if (cont >= 3) {
            valorDiario -= valorDiario * 0.03;
            result += this.periodo * valorDiario;
        }

        // Vamos usar atributos pois nao podemos puxar os metodos das subclases de
        // FormaPagamento
        if (cartaoCredito != null) {
            valorDiario += valorDiario * 0.05;
            result += this.periodo * valorDiario;
        }

        if (pix != null) {
            valorDiario -= valorDiario * 0.05;
            result += this.periodo * valorDiario;
        }

        return result;
    }

}