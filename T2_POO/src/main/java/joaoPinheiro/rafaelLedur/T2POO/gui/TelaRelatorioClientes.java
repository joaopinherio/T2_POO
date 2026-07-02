package joaoPinheiro.rafaelLedur.T2POO.gui;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import joaoPinheiro.rafaelLedur.T2POO.dados.*;

@PageTitle("Relatorio de Clientes")
@Route("relatorioClientes")
public class TelaRelatorioClientes extends VerticalLayout {

    private final Clientela clientela;
    private final LogPagamentos logPagamentos;

    
    private final Grid<Cliente> gridCliente;

    private final TextField campoNumero;
    private final TextField campoTipo;
    private final TextField campoNome;
    private final TextField campoEmail;
    private final TextField campoGovId;

    
    private final Button salvarButton;
    private final Button cancelarButton;
    private Cliente clienteSelecionado;

    public TelaRelatorioClientes() {
        clientela = Clientela.getInstance();
        logPagamentos = LogPagamentos.getInstance();

        gridCliente = new Grid<>();

        campoNumero = new TextField("Número");
        campoNumero.setReadOnly(true);
        campoTipo = new TextField("Tipo");
        campoTipo.setReadOnly(true);
        campoGovId = new TextField("CPF/CNPJ");
        campoGovId.setReadOnly(true);
        campoNome  = new TextField("Nome");
        campoEmail = new TextField("Email");

        setSpacing(true);
        setPadding(true);

        gridCliente.addColumn(Cliente::getTipo).setHeader("Tipo");
        gridCliente.addColumn(Cliente::getNumero).setHeader("Numero");
        gridCliente.addColumn(Cliente::getNome).setHeader("Nome");
        gridCliente.addColumn(Cliente::getEmail).setHeader("Email");
        gridCliente.addColumn(Cliente::getGovId).setHeader("CPF/CNPJ");
        gridCliente.addColumn(c -> c instanceof Corporativo ? c.getNomeFantasia() : "---").setHeader("Nome Fantasia");
        gridCliente.addColumn(c -> logPagamentos.getPagamentosByClienteToString(c)).setHeader("Pagamentos do Cliente");

        gridCliente.setItems(clientela.getLista());

       
        gridCliente.asSingleSelect().addValueChangeListener(event -> preparaEdicaoCliente(event));

      
        FormLayout formLayout = new FormLayout(campoNumero, campoTipo, campoNome, campoEmail, campoGovId);

        salvarButton = new Button("Atualizar", VaadinIcon.CHECK.create());
        salvarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        salvarButton.addClickShortcut(Key.ENTER);
        salvarButton.addClickListener(click -> atualizarFormulario());

        cancelarButton = new Button("Cancelar");
        Dialog dialogoCancelamento = criaDialogoDeCancelamento();
        cancelarButton.addClickListener(click -> dialogoCancelamento.open());

        HorizontalLayout botoesLayout = new HorizontalLayout(salvarButton, cancelarButton);

        if (clientela.isEmpty()) {
            Notification.show("Erro! Nenhum cliente cadastrado.", 3000, Notification.Position.BOTTOM_STRETCH);
        } else {
            add(new H2("Relatorio de Clientes Cadastrados"));
            add(gridCliente);
            add(new Hr());
            add(new H2("Alterar Cliente Selecionado"));
            add(formLayout, botoesLayout);
        }

        add(new Hr());
        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate("telaRelatorios"));
       
        habilitarFormulario(false);
    }

    private void atualizarFormulario() {
        if (campoNome.getValue().isEmpty() || campoEmail.getValue().isEmpty()) {
            Notification.show("Erro! Campo vazio.", 3000, Notification.Position.BOTTOM_STRETCH);
        } else {
            clienteSelecionado.setNome(campoNome.getValue());
            clienteSelecionado.setEmail(campoEmail.getValue());

            Notification.show("Cliente " + clienteSelecionado.getNome() + " atualizado com sucesso!",
                    3000, Notification.Position.BOTTOM_STRETCH);
        }
        gridCliente.getDataProvider().refreshAll();
        limparFormulario();
        habilitarFormulario(false);
    }

    private void atualizarFormularioEdicao(Cliente cliente) {
        campoNumero.setValue(String.valueOf(cliente.getNumero()));
        campoTipo.setValue(cliente.getTipo());
        campoNome.setValue(cliente.getNome());
        campoEmail.setValue(cliente.getEmail());
        campoGovId.setValue(cliente.getGovId());
    }

    private void habilitarFormulario(boolean opcao) {
        campoNome.setEnabled(opcao);
        campoEmail.setEnabled(opcao);
        salvarButton.setEnabled(opcao);
        cancelarButton.setEnabled(opcao);
    }

    private void preparaEdicaoCliente(ComponentValueChangeEvent<Grid<Cliente>, Cliente> event) {
        clienteSelecionado = event.getValue();

        if (clienteSelecionado != null) {
            atualizarFormularioEdicao(clienteSelecionado);
            habilitarFormulario(true);
        } else {
            limparFormulario();
            habilitarFormulario(false);
        }
    }

    private void limparFormulario() {
        gridCliente.asSingleSelect().clear();
        campoNumero.clear();
        campoTipo.clear();
        campoNome.clear();
        campoEmail.clear();
        campoGovId.clear();
        campoNome.focus();
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
}