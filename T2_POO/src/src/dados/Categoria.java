package dados;

public enum Categoria {
    // declaracao de constantes
    AVENTURA("AVENTURA"),
    ESTRATEGIA("ESTRATEGIA"),
    CORRIDA("CORRIDA");

    private final String extenso;

    private Categoria(String extenso) {
        this.extenso = extenso;
    }

    public String getExtenso() {
        return extenso;
    }

}
