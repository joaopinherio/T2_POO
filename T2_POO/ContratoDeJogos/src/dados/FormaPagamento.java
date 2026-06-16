package dados;

import java.util.*;

public class FormaPagamento {
    private String numero;
    private Date validade;
    
    public FormaPagamento(String numero, Date validade) {
        this.numero = numero;
        this.validade = validade;
    }

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