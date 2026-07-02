package joaoPinheiro.rafaelLedur.T2POO.gui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;

import joaoPinheiro.rafaelLedur.T2POO.dados.*;

@PageTitle("T2 POO")
@Route("")
public class MainView extends VerticalLayout {
    private final Clientela clientela;
    private final Catalogo catalogo;
    private final LogPagamentos logPagamentos;
    private final QuadroContrato quadroContrato;

    private final Dialog salvarDialog;
    private final Dialog loadDialog;
    private final TextField fileNameField;
    private final TextField fileNameLoad;

    private final Button confirmarButton2;
    private final Button confirmarButton1;


    public MainView() {
        clientela = Clientela.getInstance();
        catalogo = Catalogo.getInstance();
        logPagamentos = LogPagamentos.getInstance();
        quadroContrato = QuadroContrato.getInstance();

        inicializaDados();

        salvarDialog = new Dialog();
        loadDialog = new Dialog();
        fileNameField = new TextField();
        fileNameLoad = new TextField();

        Button telaCadastro = new Button("Opcoes de Cadastro");
        telaCadastro.addClickListener(e -> UI.getCurrent().navigate("telaCadastros"));

        Button telaRelatorio = new Button("Opcoes de Relatorio");
        telaRelatorio.addClickListener(e -> UI.getCurrent().navigate("telaRelatorios"));

        fileNameField.setLabel("Digite o nome do arquivo em que os dados serao salvos");
        confirmarButton1 = new Button("Confirmar");
        salvarDialog.add(fileNameField, confirmarButton1);
        
        Button salvarButton = new Button("Salvar dados");
        salvarButton.addClickListener(click -> this.salvarDadosUser());
        
        fileNameLoad.setLabel("Digite o nome do arquivo que deseja carregar");
        confirmarButton2 = new Button("Confirmar");
        loadDialog.add(fileNameLoad, confirmarButton2);
        
        Button loadButton = new Button("Carregar dados");
        loadButton.addClickListener(click -> this.loadDados());

        add(salvarButton, loadButton);

        add(telaCadastro);
        add(telaRelatorio);

        Button finalizaButton = new Button ("Finalizar Sistema");
        finalizaButton.addClickListener(click -> System.exit(0));
        add(finalizaButton);
    }

    private void inicializaDados() {
        if (clientela.isEmpty())
            clientela.inicializaClientes("CLIENTESINICIAL");

        if (catalogo.isEmpty())
            catalogo.inicializaJogos("JOGOSINICIAL");

        if (logPagamentos.isEmpty())
            logPagamentos.inicializaPagamentos("FORMASPAGAMENTOINICIAL", clientela);

        if (quadroContrato.isEmpty())
            quadroContrato.inicializaContratos("CONTRATOSINICIAL", clientela, catalogo, logPagamentos);
    }

    private void salvarDadosUser() {
        salvarDialog.open();

        confirmarButton1.addClickListener(click -> {

            if (!(catalogo.salvaJogos(fileNameField.getValue())) ||
                    !(clientela.salvaClientes(fileNameField.getValue())) ||
                    !(quadroContrato.salvaContratos(fileNameField.getValue())) ||
                    !(logPagamentos.salvaFormaPagamentos(fileNameField.getValue())))
                Notification.show("Erro ao salvar!", 3000, Notification.Position.BOTTOM_STRETCH);
            else
                Notification.show("Dados salvos com sucesso!", 3000, Notification.Position.BOTTOM_STRETCH);

            salvarDialog.remove(confirmarButton1);
            salvarDialog.close();
        });
    }

    private void loadDados(){
        loadDialog.open();

        confirmarButton2.addClickListener(click -> {
        
            if (!(catalogo.inicializaJogos(fileNameLoad.getValue().concat("JOGOS"))) ||
                    !(clientela.inicializaClientes(fileNameLoad.getValue().concat("CLIENTES"))) ||
                    !(quadroContrato.inicializaContratos(fileNameLoad.getValue().concat("CONTRATOS"), clientela, catalogo, logPagamentos)) ||
                    !(logPagamentos.inicializaPagamentos(fileNameLoad.getValue().concat("PAGAMENTOS"), clientela)))
                Notification.show("Erro ao carregar dados!", 3000, Notification.Position.BOTTOM_STRETCH);
            else
                Notification.show("Dados salvos com sucesso!", 3000, Notification.Position.BOTTOM_STRETCH);

            loadDialog.close();
            loadDialog.remove(confirmarButton2);
        });
    }
}
