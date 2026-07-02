package joaoPinheiro.rafaelLedur.T2POO.gui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Menu de Relatorio")
@Route("telaRelatorios")
public class TelaRelatorioView extends VerticalLayout {

    public TelaRelatorioView(){
        Button relCliente = new Button("Gera Relatorio de Cliente");
        Button relContrato = new Button("Gera Relatorio de Contratos");
        Button relJogos = new Button("Gera Relatorio de Jogos");
        Button menuConsulta = new Button("Menu de Consultas");


        relCliente.addClickListener(e -> UI.getCurrent().navigate("relatorioClientes"));
        relContrato.addClickListener(e -> UI.getCurrent().navigate("relatorioContratos"));
        relJogos.addClickListener(e -> UI.getCurrent().navigate("relatorioJogos"));
        menuConsulta.addClickListener(e -> UI.getCurrent().navigate("menuConsultas"));
        
        add(relCliente);
        add(relContrato);
        add(relJogos);
        add(menuConsulta);

        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate(""));
        add(backButton);
    }
}