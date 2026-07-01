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

@PageTitle("Menu de Cadastro de Contratos")
@Route("cadastroContrato")
public class TelaContratoRegView extends VerticalLayout{
    private final LogPagamentos logPagamentos;
    private final Clientela clientela;
    private final Catalogo catalogo;
    private final QuadroContrato quadroContrato;

    private IntegerField id;
    private IntegerField periodo;
    private DatePicker data;
    private TextField clienteData;
    private TextField jogoData;
    private TextField formaPagamentoData;

    private Cliente clienteSel;
    private Jogo jogoSel;
    private FormaPagamento formaPagamentoSel;

    private final Grid<Cliente> gridCliente;
    private final Grid<Jogo> gridJogo;
    private final Grid<FormaPagamento> gridFormaPagamentos;

    public TelaContratoRegView() {
        clientela = Clientela.getInstance();
        logPagamentos = LogPagamentos.getInstance();
        catalogo = Catalogo.getInstance();
        quadroContrato = QuadroContrato.getInstance();

        id = new IntegerField("ID de Contrato");
        periodo = new IntegerField("Periodo de contrato");
        data = new DatePicker("Data de Ass do Contrato");
        clienteData = new TextField("Dados do Cliente");
        jogoData = new TextField("Dados do Jogo");
        formaPagamentoData = new TextField("Dados de Forma de Pagamento");
        
        gridCliente = new Grid<>(Cliente.class);
        gridJogo = new Grid<>(Jogo.class);
        gridFormaPagamentos = new Grid<>(FormaPagamento.class);

        setSpacing(true);
        setPadding(true);

        add(new H2("Menu de Registro de Contratos"));

        FormLayout formLayout = new FormLayout(id, periodo, data, clienteData, jogoData, formaPagamentoData);

        Button salvarButton = new Button("Inserir", VaadinIcon.CHECK.create());
        salvarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        salvarButton.addClickShortcut(Key.ENTER);
        salvarButton.addClickListener(click -> this.inserirFormulario());

        Button cancelarButton = new Button("Cancelar");
        Dialog dialogoCancelamento = criaDialogoDeCancelamento();
        cancelarButton.addClickListener(click -> dialogoCancelamento.open());

        HorizontalLayout botoesLayout = new HorizontalLayout(salvarButton, cancelarButton);

        gridCliente.setItems(clientela.getLista());
        gridCliente.setColumns("numero", "nome", "email");
        gridCliente.asSingleSelect().addValueChangeListener(event -> preparaSelecaoCliente(event));

        gridJogo.setItems(catalogo.getLista());
        gridJogo.setColumns("codigo", "nome", "categoria");
        gridJogo.asSingleSelect().addValueChangeListener(event -> preparaSelecaoJogo(event));

        add(formLayout, botoesLayout, new H2("Clientes Cadastrados"), gridCliente,
        new H2("Jogos Cadastrados"), gridJogo, gridFormaPagamentos);
        add(new Hr());
        Button backButton = new Button("Voltar");
        
        backButton.addClickListener(e -> UI.getCurrent().navigate("telaCadastros"));
        add(backButton);
    }


    private void inserirFormulario() {
        if (id.getValue().equals("") || periodo.getValue().equals("") ||
        data.getValue().equals("") || clienteData.getValue().equals("") ||
        jogoData.getValue().equals("") || formaPagamentoData.getValue().equals("")){
            Notification.show("Erro! Campo vazio.", 3000, Notification.Position.BOTTOM_STRETCH);
        }
        if (logPagamentos.isRepetido(id.getValue()))
            Notification.show("Erro! ID de contrato ja existe! Favor inserir ID diferente", 3000,
                    Notification.Position.BOTTOM_STRETCH);
        else {
            Contrato con = new Contrato(id.getValue(), periodo.getValue());
            con.setData(Date.valueOf(data.getValue()));
            con.setCliente(clienteSel);
            con.setFormaPagamento(formaPagamentoSel);
            con.setJogo(jogoSel);

            quadroContrato.addContrato(con);
            String mensagem = "Contrato" + con.getId() + " salvo com sucesso!";
            Notification.show(mensagem, 3000, Notification.Position.BOTTOM_STRETCH);
        }
        gridCliente.getDataProvider().refreshAll();
        gridJogo.getDataProvider().refreshAll();
        limparFormulario();
    }

    private void limparFormulario() {
        id.clear();
        data.clear();
        periodo.clear();
        jogoData.clear();
        clienteData.clear();
        formaPagamentoData.clear();
        id.focus();
    }

    private void habilitarFormulario(boolean op) {
    }

    private Dialog criaDialogoDeCancelamento() {
        Dialog dialogo = new Dialog();
        dialogo.setHeaderTitle("Confirmar cancelamento");
        dialogo.add(new Paragraph("Você tem certeza que deseja cancelar e limpar o formulário?"));
        Button confirmarCancelamento = new Button("Sim, cancelar", e -> {
            limparFormulario();
            dialogo.close();
        });
        confirmarCancelamento.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        Button fecharDialogo = new Button("Não", e -> dialogo.close());
        dialogo.getFooter().add(fecharDialogo, confirmarCancelamento);
        return dialogo;
    }

    private void montaGridPagamento(Cliente c){
        List<FormaPagamento> cFormaPagamentos = logPagamentos.getPagamentosByCliente(c);
        gridFormaPagamentos.setItems(cFormaPagamentos);
        gridFormaPagamentos.setColumns("cod", "diaVencimento");
        gridFormaPagamentos.asSingleSelect().addValueChangeListener(event -> preparaSelecaoPagamento(event));
    }

    private void preparaSelecaoPagamento(ComponentValueChangeEvent<Grid<FormaPagamento>, FormaPagamento> event){
        formaPagamentoSel = event.getValue();

        if(formaPagamentoSel != null)
            formaPagamentoData.setValue(formaPagamentoSel.descrever());
        else
            limparFormulario();
    }

    private void preparaSelecaoCliente(ComponentValueChangeEvent<Grid<Cliente>, Cliente> event) {
        clienteSel = event.getValue();

        if (clienteSel != null) {
            clienteData.setValue(clienteSel.descrever());
            montaGridPagamento(clienteSel);
        } else {
            limparFormulario();
        }
    }
    
    private void preparaSelecaoJogo(ComponentValueChangeEvent<Grid<Jogo>, Jogo> event) {
        jogoSel = event.getValue();

        if (jogoSel != null) {
            jogoData.setValue(jogoSel.descrever());
        } else {
            limparFormulario();
        }
    }

}