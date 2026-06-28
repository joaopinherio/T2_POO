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

    private final Grid<Cliente> gridCliente;

    public TelaRelatorioClientes(){
        clientela = Clientela.getInstance();

        gridCliente = new Grid<>(Cliente.class);

        setSpacing(true);
        setSpacing(true);

        add(new H2("Relatorio de Clientes Cadastrados"));

        gridCliente.setItems(clientela.getLista());
        gridCliente.setColumns("nome");
    }
}
