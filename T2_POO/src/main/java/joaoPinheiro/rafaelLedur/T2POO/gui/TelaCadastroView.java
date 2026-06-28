package joaoPinheiro.rafaelLedur.T2POO.gui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Menu de Cadastros")
@Route("telaCadastros")
public class TelaCadastroView extends VerticalLayout {

    public TelaCadastroView(){
        Button cadCliente = new Button("Menu Cadastro Cliente");
        Button cadJogo = new Button("Menu Cadastro Jogo");
        Button cadPagamento = new Button("Menu Selecao de Pagamento");
        Button cadContrato = new Button("Menu de Cadastro Contrato");

        cadCliente.addClickListener(e -> UI.getCurrent().navigate("cadastroCliente"));
        cadJogo.addClickListener(e -> UI.getCurrent().navigate("cadastroJogo"));
        cadPagamento.addClickListener(e -> UI.getCurrent().navigate("cadastroPagamento"));
        cadContrato.addClickListener(e -> UI.getCurrent().navigate("cadastroContrato"));
        
        add(cadCliente);
        add(cadJogo);
        add(cadPagamento);
        add(cadContrato);
    }
}