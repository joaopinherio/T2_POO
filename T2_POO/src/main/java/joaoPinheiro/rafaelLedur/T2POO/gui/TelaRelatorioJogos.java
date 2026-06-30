package joaoPinheiro.rafaelLedur.T2POO.gui;
import java.sql.Date;
import java.util.List;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.copilot.shaded.commons.configuration2.resolver.CatalogResolver.Catalog;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import joaoPinheiro.rafaelLedur.T2POO.dados.*;

import java.util.List;

@PageTitle("Relatório de Jogos")
@Route("relatoriojogos")
public class TelaRelatorioJogos extends VerticalLayout{
    private final Catalogo catalogo;
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


        gridCatalogo.setItems(catalogo.getLista());

         if(catalogo.isEmpty())
            Notification.show("Erro! Nenhum jogo cadastrado.", 3000, Notification.Position.BOTTOM_STRETCH);
        else{
            gridCatalogo.getDataProvider().refreshAll();
            add(new H2("Relatorio de Jogos Cadastrados"));

            add(gridCatalogo);
        }

        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate("telaRelatorios"));
        add(backButton);

    }

}
