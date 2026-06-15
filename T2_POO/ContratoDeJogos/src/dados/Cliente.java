package dados;

abstract class Cliente {
    private int numero;
    private String nome;
    private String email;

    public Cliente(int numero, String nome, String email) {
        this.numero = numero;
        this.nome = nome;
        this.email = email;
    }

    abstract String descrever();

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
}