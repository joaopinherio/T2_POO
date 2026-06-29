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

@PageTitle("Relatorio de Clientes")
@Route("relatorioClientes")
public class TelaRelatorioClientes extends VerticalLayout{
    private final Clientela clientela;
    private final LogPagamentos logPagamentos;

    private final Grid<Cliente> gridCliente;

    public TelaRelatorioClientes(){
        clientela = Clientela.getInstance();
        logPagamentos = LogPagamentos.getInstance();

        gridCliente = new Grid<>();

        setSpacing(true);
        setSpacing(true);

        gridCliente.addColumn(Cliente::getTipo).setHeader("Tipo");
        gridCliente.addColumn(Cliente::getNumero).setHeader("Numero");
        gridCliente.addColumn(Cliente::getNome).setHeader("Nome");
        gridCliente.addColumn(Cliente::getEmail).setHeader("Email");
        gridCliente.addColumn(Cliente::getGovId).setHeader("CPF/CNPJ");

//        gridCliente.addColumn(cliente -> cliente.getTipo() == "Corporativo" ?
//        cliente.getNomeFantasia() : "---").setHeader("Nome Fantasia");

        gridCliente.addColumn(c  -> c instanceof Corporativo?
        c.getNomeFantasia() : "---").setHeader("Nome Fantasia");
        
        gridCliente.addColumn(c -> logPagamentos.getPagamentosByCliente(c).toString())
        .setHeader("Pagamentos do Cliente");

        gridCliente.setItems(clientela.getLista());
        
        if(clientela.isEmpty())
            Notification.show("Erro! Nenhum cliente cadastrado.", 3000, Notification.Position.BOTTOM_STRETCH);
        else{
            gridCliente.getDataProvider().refreshAll();
            add(new H2("Relatorio de Clientes Cadastrados"));

            add(gridCliente);
        }
    }
}
