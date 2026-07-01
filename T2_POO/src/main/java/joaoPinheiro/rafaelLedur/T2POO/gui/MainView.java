package joaoPinheiro.rafaelLedur.T2POO.gui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import joaoPinheiro.rafaelLedur.T2POO.dados.*;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.copilot.shaded.commons.configuration2.resolver.CatalogResolver.Catalog;
import com.vaadin.copilot.shaded.javaparser.symbolsolver.javaparsermodel.contexts.FieldAccessContext;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dialog.*;
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

@PageTitle("T2 POO")
@Route("")
public class MainView extends VerticalLayout {
    private final Clientela clientela;
    private final Catalogo catalogo;
    private final LogPagamentos logPagamentos;
    private final QuadroContrato quadroContrato;
    
    private final Dialog salvarDialog;
    private final TextField fileNameField;


    public MainView(){
        clientela = Clientela.getInstance();
        catalogo = Catalogo.getInstance();
        logPagamentos = LogPagamentos.getInstance();
        quadroContrato = QuadroContrato.getInstance();

        inicializaDados();

        salvarDialog = new Dialog();
        fileNameField = new TextField();

        Button telaCadastro = new Button("Opcoes de Cadastro");
        telaCadastro.addClickListener(e -> UI.getCurrent().navigate("telaCadastros"));
        
        Button telaRelatorio= new Button("Opcoes de Relatorio");
        telaRelatorio.addClickListener(e -> UI.getCurrent().navigate("telaRelatorios"));

        Button salvarButton = new Button("Salvar dados");
        salvarButton.addClickListener(click -> this.salvarDadosUser());
        
        add(salvarButton);

        add(telaCadastro);
        add(telaRelatorio);
    }

    public void inicializaDados(){
        if(clientela.isEmpty())
            clientela.inicializaClientes("CLIENTESINICIAL.CSV");

        if(catalogo.isEmpty())
            catalogo.inicializaJogos("JOGOSINICIAL.CSV");

        if (logPagamentos.isEmpty())
            logPagamentos.inicializaPagamentos("FORMASPAGAMENTOINICIAL.CSV", clientela);
        
        if (quadroContrato.isEmpty())
            quadroContrato.inicializaContratos("CONTRATOSINICIAL.CSV", clientela, catalogo, logPagamentos);
    }

    public void salvarDadosUser(){
        fileNameField.setLabel("Digite o nome do arquivo em que os dados serao salvos");
        Button confirmarButton = new Button("Confirmar");
        salvarDialog.add(fileNameField, confirmarButton);

        salvarDialog.open();
        
        confirmarButton.addClickListener(click -> {
            
            if(!(catalogo.salvaJogos(fileNameField.getValue()))||
            !(clientela.salvaClientes(fileNameField.getValue())))
                Notification.show("Erro de leitura!", 3000, Notification.Position.BOTTOM_STRETCH);
            else{
                Notification.show("Dados salvos com sucesso!", 3000, Notification.Position.BOTTOM_STRETCH);
                
                salvarDialog.close();
            }
        });
    }

}
