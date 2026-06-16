package dados;

public class Jogo {

    private int codigo;
    private String nome;
    private int ano;
    //no T1 era valor minuto, passou a ser valor diario no novo diagrama (T2)
    //private double valorMinuto;
    private double valorDiario;
    private Categoria categoria;
    private Contrato contrato; // veio apos a 8

    public Jogo(int codigo, String nome, int ano, double valorDiario) {
        this.codigo = codigo;
        this.nome = nome;
        this.ano = ano;
        this.valorDiario = valorDiario;
    }

    public String descrever() {
        return codigo + ";" + nome + ";" + ano + ";" + valorDiario + ";" + categoria;
    }

    public String descreverRedux() {
        return categoria + ";" + codigo + ";" + nome;
    }

    public String descreverRedux5() {
        return codigo + ";" + nome + ";" + categoria;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public double getValorDiario() {
        return this.valorDiario;
    }

    public String getNome() {
        return this.nome;
    }

    public Categoria getCategoria() {
        return this.categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public boolean categoriaEx(String input) {

        for (Categoria cat_s : Categoria.values()) {
            if (input.equals(cat_s.getExtenso()))
                return true;
        }
        return false;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public Contrato getContrato() {
        return this.contrato;
    }

    public void rmContrato() {
        this.contrato = null;
    }

}