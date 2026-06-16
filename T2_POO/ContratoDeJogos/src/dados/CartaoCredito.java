package dados;
import java.util.*;

public class CartaoCredito extends FormaPagamento{
    private String numero;
    private Date validade;
    
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public Date getValidade() {
        return validade;
    }
    public void setValidade(Date validade) {
        this.validade = validade;
    }
}
