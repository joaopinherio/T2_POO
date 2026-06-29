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
import java.util.List;

import joaoPinheiro.rafaelLedur.T2POO.dados.*;

@PageTitle("Relatorio de Contratos")
@Route("relatorioContratos")
public class TelaRelatorioContratos extends VerticalLayout{
    private final Clientela clientela;
    private final Catalogo catalogo;
    private final LogPagamentos logPagamentos;
    private final QuadroContrato quadroContrato;

    private final Grid<Contrato> gridContrato;

    public TelaRelatorioContratos(){
        clientela = Clientela.getInstance();
        catalogo = Catalogo.getInstance();
        logPagamentos = LogPagamentos.getInstance();
        quadroContrato = QuadroContrato.getInstance();

        gridContrato = new Grid<>();

        setSpacing(true);
        setSpacing(true);

        gridContrato.addColumn(Contrato::getId).setHeader("ID");
        gridContrato.addColumn(Contrato::getData).setHeader("Data de Inicio");
        gridContrato.addColumn(Contrato::getPeriodo).setHeader("Periodo de Contrato");
        gridContrato.addColumn(c -> c.getJogo().descrever()).setHeader("Jogo Contratado");
        gridContrato.addColumn(c -> c.getCliente().descrever()).setHeader("Cliente Contratante");
        gridContrato.addColumn(c -> c.getFormaPagamento().descrever()).setHeader("Forma de Pagamento");

//        gridContrato.addColumn(cliente -> cliente.getTipo() == "Corporativo" ?
//        cliente.getNomeFantasia() : "---").setHeader("Nome Fantasia");

        gridContrato.setItems(quadroContrato.getLista());
        
        if(clientela.isEmpty())
            Notification.show("Erro! Nenhum contrato cadastrado.", 3000, Notification.Position.BOTTOM_STRETCH);
        else{
            gridContrato.getDataProvider().refreshAll();
            add(new H2("Relatorio de Contratos Cadastrados"));

            add(gridContrato);
        }
    
        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate("telaRelatorios"));
        add(backButton);
    }
}