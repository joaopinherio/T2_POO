package joaoPinheiro.rafaelLedur.T2POO.dados;

public class PIX extends FormaPagamento{
    private String chave;

    public PIX(int cod, int diaVencimento, String chave){
        super(cod, diaVencimento);
        this.chave = chave;
    }

    @Override
    public String descrever(){
        return super.descrever() + ";" + getChave();
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }
}
