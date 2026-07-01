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

@PageTitle("Menu de Consultas")
@Route("menuConsultas")
public class TelaMenuConsultas extends VerticalLayout {
    private final Clientela clientela;
    private final Catalogo catalogo;
    private final LogPagamentos logPagamentos;
    private final QuadroContrato quadroContrato;

    private final Button consultaJogo;
    private final Button consultaContrato;
    private final Button consultaCliente;

    private final Dialog consultaJogDialog;
    
    public TelaMenuConsultas() {
        clientela = Clientela.getInstance();
        catalogo = Catalogo.getInstance();
        logPagamentos = LogPagamentos.getInstance();
        quadroContrato = QuadroContrato.getInstance();
        
        setSpacing(true);
        setSpacing(true);

        consultaJogo = new Button("Consulta: Jogo com maior valor diario");
        consultaJogo.addClickListener(click -> this.mostraJogoMaiorValor());

        consultaContrato = new Button("Consulta: Contrato com maior valor final");
        consultaContrato.addClickListener(click -> this.mostraContratoMaiorValor());
        consultaCliente = new Button("Consulta: Cliente com maior montante de Contratos");

        add(consultaJogo);
        add(consultaContrato);
        add(consultaCliente);

        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate("telaRelatorios"));
        add(backButton);
    }

    private void mostraJogoMaiorValor() {
        if (catalogo.isEmpty())
            Notification.show("Erro! Nenhum jogo cadastrado.", 3000, Notification.Position.BOTTOM_STRETCH);
        else {
            Jogo jogoSel = null;
            if ((jogoSel = catalogo.getJogoMaiorValor()) == null)
                Notification.show("Empate entre valores: \n" + catalogo.toString(), 3000, Notification.Position.MIDDLE);
            else
                Notification.show("Jogo com maior valor diario: \n" + jogoSel.descrever(), 3000, Notification.Position.MIDDLE);
        }
    }

    private void mostraContratoMaiorValor(){
        if (quadroContrato.isEmpty())
            Notification.show("Erro! Nenhum contrato cadastrado.", 3000, Notification.Position.BOTTOM_STRETCH);
        else {
            Contrato contratoSel = quadroContrato.getContratoMaiorValorFinal();

            if(contratoSel == null){
                
            }
        }
    }
}
