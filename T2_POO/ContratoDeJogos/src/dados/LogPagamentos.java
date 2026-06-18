package dados;

import java.util.*;

public class LogPagamentos {
     private ArrayList<FormaPagamento> pagamentos;

     public LogPagamentos(){
        pagamentos = new ArrayList<>();
     }

     public boolean addPagamento(FormaPagamento f){
        return pagamentos.add(f);
     }

     

}
