package joaoPinheiro.rafaelLedur.T2POO.gui;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import joaoPinheiro.rafaelLedur.T2POO.dados.Cliente;
import joaoPinheiro.rafaelLedur.T2POO.dados.Clientela;
import joaoPinheiro.rafaelLedur.T2POO.dados.Corporativo;
import joaoPinheiro.rafaelLedur.T2POO.dados.LogPagamentos;
import joaoPinheiro.rafaelLedur.T2POO.dados.QuadroContrato;

@PageTitle("Relatorio de Clientes")
@Route("relatorioClientes")
public class TelaRelatorioClientesView extends VerticalLayout{
    private final Clientela clientela;
    private final QuadroContrato quadroContrato;
    private final LogPagamentos logPagamentos;

    private final Button relatorioMode;
    private final Button consultaMode;

    private final Grid<Cliente> gridCliente;

    public TelaRelatorioClientesView(){
        clientela = Clientela.getInstance();
        quadroContrato = QuadroContrato.getInstance();
        logPagamentos = LogPagamentos.getInstance();
        

        gridCliente = new Grid<>();

        setSpacing(true);
        setSpacing(true);

        gridCliente.addColumn(Cliente::getTipo).setHeader("Tipo");
        gridCliente.addColumn(Cliente::getNumero).setHeader("Numero");
        gridCliente.addColumn(Cliente::getNome).setHeader("Nome");
        gridCliente.addColumn(Cliente::getEmail).setHeader("Email");
        gridCliente.addColumn(Cliente::getGovId).setHeader("CPF/CNPJ");

        gridCliente.addColumn(c  -> c instanceof Corporativo?
        c.getNomeFantasia() : "---").setHeader("Nome Fantasia");
        
        gridCliente.addColumn(c -> logPagamentos.getPagamentosByClienteToString(c))
        .setHeader("Pagamentos do Cliente");

        gridCliente.addColumn(Cliente::getValorMontante).setHeader("Valor montante (Contratos)");

        add(new H2("Gerenciador de Clientes Cadastrados"));

        relatorioMode = new Button("Modo relatorio (padrao)");
        relatorioMode.addClickListener(click -> this.relatorioClientes());
        
        consultaMode = new Button("Modo Consulta (Filtra Cliente com maior valor montante de contratos)");
        consultaMode.addClickListener(click -> this.consultaCliente());
        
        add(relatorioMode, consultaMode);
        
        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate("telaRelatorios"));
        add(backButton);

        relatorioClientes();
    }

    private void relatorioClientes(){
        Notification.show("Modo Relatorio (ativado)", 1000, Notification.Position.BOTTOM_CENTER);
        gridCliente.setItems(clientela.getLista());
        
        if(clientela.isEmpty())
            Notification.show("Erro! Nenhum cliente cadastrado.", 3000, Notification.Position.BOTTOM_STRETCH);
        else{
            quadroContrato.calculaMontanteEachCliente();

            gridCliente.getDataProvider().refreshAll();
            add(gridCliente);
        }
    }

    private void consultaCliente(){
        Notification.show("Modo Consulta (ativado)", 1000, Notification.Position.BOTTOM_CENTER);
        gridCliente.setItems(clientela.getLista());

        if(clientela.isEmpty())
            Notification.show("Erro! Nenhum cliente cadastrado.", 3000, Notification.Position.BOTTOM_STRETCH);
        else{
            quadroContrato.calculaMontanteEachCliente();
            Cliente clienteMaioral = clientela.getCLienteMaiorMontante();
            
            if(clienteMaioral == null)
                gridCliente.setItems(clientela.getLista());
            else
                gridCliente.setItems(clienteMaioral);
        
            add(gridCliente);
        }
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
