package dados;

public class Corporativo extends Cliente {
    private String cnpj;
    private String nomeFantasia;

    public Corporativo(int numero, String nome, String email, String cnpj, String nomeFantasia) {
        super(numero, nome, email);
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
    }

    @Override
    public int getNumero() {
        return super.getNumero();
    }

    @Override
    public String descrever() {
        return getNumero() + ";" + getNome() + ";" + getEmail() + ";" + cnpj;
    }

    public String descreverComplete() {
        return getNumero() + ";" + getNome() + ";" + getEmail() + ";" + cnpj + ";" + nomeFantasia;
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
    }

}
