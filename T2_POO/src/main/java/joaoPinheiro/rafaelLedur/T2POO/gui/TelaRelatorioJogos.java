package joaoPinheiro.rafaelLedur.T2POO.gui;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.copilot.shaded.commons.configuration2.resolver.CatalogResolver.Catalog;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import joaoPinheiro.rafaelLedur.T2POO.dados.*;

@PageTitle("Relatório de Jogos")
@Route("relatorioJogos")
public class TelaRelatorioJogos extends VerticalLayout{
    private final Catalogo catalogo;
    
    private final Button relatorioMode;
    private final Button consultaMode;
    
    private final Grid <Jogo> gridCatalogo;

    public TelaRelatorioJogos(){

        catalogo = Catalogo.getInstance();
        gridCatalogo = new Grid<>();

        setSpacing(true);
        setSpacing(true);

        gridCatalogo.addColumn(Jogo::getCodigo).setHeader("Código");
        gridCatalogo.addColumn(Jogo::getNome).setHeader("Nome");
        gridCatalogo.addColumn(Jogo::getAno).setHeader("Ano");
        gridCatalogo.addColumn(Jogo::getValorDiario).setHeader("Valor diário");
        gridCatalogo.addColumn(c -> c.getCategoria().getExtenso()).setHeader("Categoria");

        add(new H2("Gerenciador de Contratos Cadastrados"));

        relatorioMode = new Button("Modo relatorio (padrao)");
        relatorioMode.addClickListener(click -> this.relatorioJogos());
        
        consultaMode = new Button("Modo Consulta (Filtra Jogo com maior valor diario)");
        consultaMode.addClickListener(click -> this.mostraJogoMaiorValor());
        
        add(relatorioMode, consultaMode);
        
        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate("telaRelatorios"));
        add(backButton);

        relatorioJogos();
    }

    private void relatorioJogos(){
        Notification.show("Modo Relatorio (ativado)", 1000, Notification.Position.BOTTOM_CENTER);
        gridCatalogo.setItems(catalogo.getLista());
        
        if(catalogo.isEmpty())
            Notification.show("Erro! Nenhum contrato cadastrado.", 3000, Notification.Position.BOTTOM_STRETCH);
        else{
            gridCatalogo.getDataProvider().refreshAll();
            add(gridCatalogo);
        }
    }

    private void mostraJogoMaiorValor() {
        Notification.show("Modo Consulta (ativado)", 1000, Notification.Position.BOTTOM_CENTER);
        gridCatalogo.setItems(catalogo.getLista());
        
        if (catalogo.isEmpty())
            Notification.show("Erro! Nenhum jogo cadastrado.", 3000, Notification.Position.BOTTOM_STRETCH);
        else {
            gridCatalogo.getDataProvider().refreshAll();
            gridCatalogo.setAriaLabel("Maior Valor Final (todos se houver empate)");
            
            Jogo jogoSel = catalogo.getJogoMaiorValor();
            if (jogoSel == null)
                gridCatalogo.setItems(catalogo.getLista());
            else
                gridCatalogo.setItems(jogoSel);
            
            add(gridCatalogo);
        }
    }
}