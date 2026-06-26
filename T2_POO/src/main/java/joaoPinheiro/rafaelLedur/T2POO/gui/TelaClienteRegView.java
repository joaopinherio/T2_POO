package joaoPinheiro.rafaelLedur.T2POO.gui;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
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
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import joaoPinheiro.rafaelLedur.T2POO.dados.Cliente;
import joaoPinheiro.rafaelLedur.T2POO.dados.Individual;
import joaoPinheiro.rafaelLedur.T2POO.dados.Corporativo;
import joaoPinheiro.rafaelLedur.T2POO.dados.Clientela;

@PageTitle("Cadastro Clientes")
@Route("cadastroCliente")
public class TelaClienteRegView extends VerticalLayout {
    private final Clientela clientela;

    private final IntegerField numero;
    private final TextField nome;
    private final TextField email;
    private final TextField nomeFantasia;
    private final TextField id;
    private final ComboBox<String> formaPagamento;
    private final ComboBox<String> tipoCliente;

    private final Grid<Cliente> grid;

    public TelaClienteRegView() {
        // Inicializando o cadastro de pessoas
        clientela = new Clientela();
        clientela.inicializaClientes("CLIENTESINICIAL.CSV");

        numero = new IntegerField("Numero");
        nome = new TextField("Nome");
        email = new TextField("E-mail");
        id = new TextField("CPF/CNPJ");
        nomeFantasia = new TextField("Nome Fantasia");

        tipoCliente = new ComboBox<>("Tipo de Cliente");
        tipoCliente.setItems("Individual", "Corporativo");

        formaPagamento = new ComboBox<>("Forma de Pagamento");
        formaPagamento.setItems("PIX", "Cartao de Credito");       
        
        grid = new Grid<>(Cliente.class);

        // Definindo as características do layout básico
        setSpacing(true);
        setPadding(true);

        // Define título do formulário
        add(new H2("Menu de Cadastro de Clientes"));

        // Configuração do formulário
        FormLayout formLayout = new FormLayout(numero, nome, email, tipoCliente, formaPagamento, id, nomeFantasia);

        // Definição dos botões de ação
        Button salvarButton = new Button("Inserir", VaadinIcon.CHECK.create());
        salvarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        salvarButton.addClickShortcut(Key.ENTER);
        salvarButton.addClickListener(click -> this.inserirFormulario());

        Button cancelarButton = new Button("Cancelar");
        Dialog dialogoCancelamento = criaDialogoDeCancelamento();
        cancelarButton.addClickListener(click -> dialogoCancelamento.open());

        // Adiciona botoes de ação em um layout horizontal
        HorizontalLayout botoesLayout = new HorizontalLayout(salvarButton, cancelarButton);

        // Configuração da Grid
        grid.setItems(clientela.getLista());
        // grid.setColumns("nome", "email", "pais", "formattedDataNascimento");
        grid.setColumns("numero","nome", "email");
        // grid.addColumn(Cliente::numero).setHeader("Data nascimento");

        // Monta todos os elementos na janela
        add(formLayout, botoesLayout, new H2("Usuários Cadastrados"), grid);
        add(new Hr());

        // Define o botão de retorno à página principal
        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate(""));
        add(backButton);
        //nome.display(null);
    }

    private void inserirFormulario() {
            if (numero.getValue().equals("") || nome.getValue().equals("")|| email.getValue().equals("") ||
                tipoCliente.getValue() == null || formaPagamento.getValue() == null) {
                Notification.show("Erro! Campo vazio.", 3000, Notification.Position.BOTTOM_STRETCH);
            } else {
                Cliente c;
                if(tipoCliente.getValue() == "Individual"){
                    c = new Individual(numero.getValue(),
                    nome.getValue(),
                    email.getValue(),
                    id.getValue()); 
               } else{
                c = new Corporativo(numero.getValue(),
                        nome.getValue(),
                        email.getValue(),
                        id.getValue(),
                        nomeFantasia.getValue());
                }
                clientela.addCliente(c);
                String mensagem = "Usuário " + c.getNome() + " salvo com sucesso!";
                Notification.show(mensagem, 3000, Notification.Position.BOTTOM_STRETCH);
            }
            grid.getDataProvider().refreshAll();
            limparFormulario();
    }

    private void limparFormulario() {
        numero.clear();
        nome.clear();
        email.clear();
        id.clear();
        nomeFantasia.clear();
        nome.focus(); // Coloca o foco no campo nome
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