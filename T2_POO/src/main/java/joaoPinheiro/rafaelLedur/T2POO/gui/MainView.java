package joaoPinheiro.rafaelLedur.T2POO.gui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.textfield.*;

@PageTitle("T2 POO")
@Route("")
public class MainView extends VerticalLayout {
    public MainView(){
        Button telaCadastro = new Button("Opcoes de Cadastro");
        Button telaRelatorio= new Button("Opcoes de Relatorio");
        Button telaEdicao  = new Button("Opcoes de Edicao");
        telaCadastro.addClickListener(e -> UI.getCurrent().navigate("telaCadastros"));
        
        add(telaCadastro);
        add(telaRelatorio);
        add(telaEdicao);
    }

}
