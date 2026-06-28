package joaoPinheiro.rafaelLedur.T2POO.dados;

public class Individual extends Cliente {
    private String cpf;

    public Individual(int numero, String nome, String email, String cpf) {
        super(numero, nome, email);
        this.cpf = cpf;
    }

    @Override
    public int getNumero() {
        return super.getNumero();
    }

    @Override
    public String descrever() {
        return getNumero() + ";" + getNome() + ";" + getEmail() + ";" + cpf;
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
    }
    
    @Override
    public String getGovId() {
        return cpf;
    }

    @Override
    public String getTipo(){
        return "Individual";
    }
}
