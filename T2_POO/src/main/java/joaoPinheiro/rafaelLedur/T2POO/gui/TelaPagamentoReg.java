package joaoPinheiro.rafaelLedur.T2POO.gui;

import java.sql.Date;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
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

import joaoPinheiro.rafaelLedur.T2POO.dados.CartaoCredito;
import joaoPinheiro.rafaelLedur.T2POO.dados.Cliente;
import joaoPinheiro.rafaelLedur.T2POO.dados.Clientela;
import joaoPinheiro.rafaelLedur.T2POO.dados.FormaPagamento;
import joaoPinheiro.rafaelLedur.T2POO.dados.LogPagamentos;
import joaoPinheiro.rafaelLedur.T2POO.dados.PIX;

@PageTitle("Cadastra Forma de Pagamento")
@Route("cadastroPagamento")
public class TelaPagamentoReg extends VerticalLayout {
    private final LogPagamentos logPagamentos;
    private final Clientela clientela;

    private IntegerField cod;
    private IntegerField diaVencimento;
    private TextField numeroCartao;
    private TextField chave;
    private DatePicker validade;

    private TextField nomeCliente;

    private ComboBox<String> tipoPagamento;
    private Cliente clienteSel;

    private final Grid<Cliente> grid;

    public TelaPagamentoReg() {
        clientela = Clientela.getInstance();
        logPagamentos = LogPagamentos.getInstance();

        if (logPagamentos.isEmpty())
            logPagamentos.inicializaPagamentos("FORMASPAGAMENTOINICIAL.CSV", clientela);

        cod = new IntegerField("Codigo de Pagamento");
        diaVencimento = new IntegerField("Dia de Vencimento");
        numeroCartao = new TextField("Numero do Cartao");
        numeroCartao.setVisible(false);
        chave = new TextField("Chave PIX");
        chave.setVisible(false);
        validade = new DatePicker("Data de Vencimento");
        validade.setVisible(false);

        tipoPagamento = new ComboBox<>("Forma de Pagamento");
        tipoPagamento.setItems("PIX", "Cartao de Credito");

        nomeCliente = new TextField("Cliente Pagante");

        grid = new Grid<>(Cliente.class);

        setSpacing(true);
        setPadding(true);

        add(new H2("Menu de Registro de Pagamentos"));

        Button tipoPagamentoButton = new Button("Selecionar", VaadinIcon.CHECK.create());
        tipoPagamentoButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        tipoPagamentoButton.addClickListener(click -> this.cadHighlight());

        FormLayout formLayout = new FormLayout(cod, diaVencimento, numeroCartao,
        chave, validade, nomeCliente);

        // Definição dos botões de ação
        Button salvarButton = new Button("Inserir", VaadinIcon.CHECK.create());
        salvarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        salvarButton.addClickShortcut(Key.ENTER);
        salvarButton.addClickListener(click -> this.inserirFormulario());

        Button cancelarButton = new Button("Cancelar");
        Dialog dialogoCancelamento = criaDialogoDeCancelamento();
        cancelarButton.addClickListener(click -> dialogoCancelamento.open());

        HorizontalLayout botoesLayout = new HorizontalLayout(salvarButton, cancelarButton);

        grid.setItems(clientela.getLista());
        grid.setColumns("numero", "nome", "email");
        grid.asSingleSelect().addValueChangeListener(event -> preparaSelecaoCliente(event));

        add(tipoPagamento);
        add(tipoPagamentoButton);
        add(formLayout, botoesLayout, new H2("Usuários Cadastrados"), grid);
        add(new Hr());

        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate("telaCadastros"));
        add(backButton);
    }

    private void cadHighlight() {
        if (tipoPagamento.getValue().equals("PIX")) {
            numeroCartao.setVisible(false);
            validade.setVisible(false);
            chave.setVisible(true);

        } else {
            chave.setVisible(false);
            numeroCartao.setVisible(true);
            validade.setVisible(true);
        }
    }

    private void inserirFormulario() {
        if (cod.getValue().equals("") || diaVencimento.getValue().equals("")) {
            Notification.show("Erro! Campo vazio.", 3000, Notification.Position.BOTTOM_STRETCH);
        }
        if (logPagamentos.isRepetido(cod.getValue()))
            Notification.show("Erro! Codigo de pagametno ja existe! Favor inserir codigo diferente", 3000,
                    Notification.Position.BOTTOM_STRETCH);
        else {
            FormaPagamento f = null;
            if (tipoPagamento.getValue() == "PIX") {
                f = new PIX(cod.getValue(),
                        diaVencimento.getValue(),
                        chave.getValue());
            } else {
                f = new CartaoCredito(cod.getValue(),
                        diaVencimento.getValue(),
                        numeroCartao.getValue(),
                        Date.valueOf(validade.getValue()));
            }
            f.setCliente(clienteSel);
            clienteSel.setFormaPagamento(f);
            logPagamentos.addPagamento(f);
            
            String mensagem = "Pagamento" + tipoPagamento.getValue() + " salvo com sucesso!";
            Notification.show(mensagem, 3000, Notification.Position.BOTTOM_STRETCH);
        }
        grid.getDataProvider().refreshAll();
        limparFormulario();
    }

    private void limparFormulario() {
        cod.clear();
        validade.clear();
        diaVencimento.clear();
        numeroCartao.clear();
        chave.clear();
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

    private void preparaSelecaoCliente(ComponentValueChangeEvent<Grid<Cliente>, Cliente> event) {
        clienteSel = event.getValue();

        if (clienteSel != null) {
            nomeCliente.setValue(clienteSel.getNome());
        } else {
            limparFormulario();
        }
    }

}
