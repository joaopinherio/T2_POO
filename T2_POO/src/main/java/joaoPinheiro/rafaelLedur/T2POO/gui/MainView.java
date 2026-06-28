package joaoPinheiro.rafaelLedur.T2POO.gui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import joaoPinheiro.rafaelLedur.T2POO.dados.*;

@PageTitle("T2 POO")
@Route("")
public class MainView extends VerticalLayout {
    private final Clientela clientela;
    private final Catalogo catalogo;
    private final LogPagamentos logPagamentos;
    private final QuadroContrato quadroContrato;
    public MainView(){
        clientela = Clientela.getInstance();
        catalogo = Catalogo.getInstance();
        logPagamentos = LogPagamentos.getInstance();
        quadroContrato = QuadroContrato.getInstance();

        if(clientela.isEmpty())
            clientela.inicializaClientes("CLIENTESINICIAL.CSV");

        if(catalogo.isEmpty())
            catalogo.inicializaJogos("JOGOSINICIAL.CSV");

        if (logPagamentos.isEmpty())
            logPagamentos.inicializaPagamentos("FORMASPAGAMENTOINICIAL.CSV", clientela);
        
        if (quadroContrato.isEmpty())
            quadroContrato.inicializaContratos("CONTRATOSINICIAL.CSV", clientela, catalogo, logPagamentos);
        
        Button telaCadastro = new Button("Opcoes de Cadastro");
        Button telaRelatorio= new Button("Opcoes de Relatorio");
        Button telaEdicao  = new Button("Opcoes de Edicao");
        telaCadastro.addClickListener(e -> UI.getCurrent().navigate("telaCadastros"));
        
        add(telaCadastro);
        add(telaRelatorio);
        add(telaEdicao);

    }

}
