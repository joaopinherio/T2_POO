package joaoPinheiro.rafaelLedur.T2POO.gui;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
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
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import joaoPinheiro.rafaelLedur.T2POO.dados.*;

@PageTitle("Cadastra Jogos")
@Route("cadastroJogo")
public class TelaJogoRegView extends VerticalLayout{
    private final Catalogo catalogo;
    private final Clientela clientela;
    private final IntegerField codigo;
    private final IntegerField ano;
    private final NumberField valorDiario;
    private final TextField nome;

    private final ComboBox<String> categoria;
    //private final IntegerField contratoNum;
    private final Grid<Jogo> grid;


    public TelaJogoRegView() {
        clientela = Clientela.getInstance();
        catalogo = Catalogo.getInstance();

        if(catalogo.isEmpty())
        catalogo.inicializaJogos("JOGOSINICIAL.CSV");

        codigo= new IntegerField("Codigo");
        nome = new TextField("Nome");
        ano = new IntegerField("Ano");
        valorDiario = new NumberField("Valor Diario");

        categoria = new ComboBox<>("Categoria");
        categoria.setItems("AVENTURA","ESTRATEGIA", "CORRIDA");

        grid = new Grid<>(Jogo.class);

        setSpacing(true);
        setPadding(true);

        add(new H2("Menu de Cadastro de Jogos"));
        
        FormLayout formLayout = new FormLayout(codigo, nome, ano, valorDiario, categoria);

        // Definição dos botões de ação
        Button salvarButton = new Button("Inserir", VaadinIcon.CHECK.create());
        salvarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        salvarButton.addClickShortcut(Key.ENTER);
        salvarButton.addClickListener(click -> this.inserirFormulario());

        Button cancelarButton = new Button("Cancelar");
        Dialog dialogoCancelamento = criaDialogoDeCancelamento();
        cancelarButton.addClickListener(click -> dialogoCancelamento.open());

        HorizontalLayout botoesLayout = new HorizontalLayout(salvarButton, cancelarButton);

        grid.setItems(catalogo.getLista());
        grid.setColumns("codigo", "nome", "categoria");

        add(formLayout, botoesLayout, new H2("Usuários Cadastrados"), grid);
        add(new Hr());

        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate("telaCadastros"));
        add(backButton);
    }

    private void inserirFormulario() {
            if (codigo.getValue().equals("") || nome.getValue().equals("")|| ano.getValue().equals("") ||
                valorDiario.getValue() == null || categoria.getValue() == null) {
                Notification.show("Erro! Campo vazio.", 3000, Notification.Position.BOTTOM_STRETCH);
            }
            if(catalogo.isRepetido(codigo.getValue()))
                Notification.show("Erro! Numero de jogo ja existe! Favor codigo diferente", 3000, Notification.Position.BOTTOM_STRETCH);
            else {
                Jogo j = new Jogo(codigo.getValue(), 
                nome.getValue(), 
                ano.getValue(), 
                valorDiario.getValue());
                j.setCategoria(Categoria.valueOf(categoria.getValue()));

                catalogo.addJogo(j);
                String mensagem = "Jogo" + j.getNome() + " salvo com sucesso!";
                Notification.show(mensagem, 3000, Notification.Position.BOTTOM_STRETCH);
            }
            grid.getDataProvider().refreshAll();
            limparFormulario();
    }

    private void limparFormulario() {
        codigo.clear();
        nome.clear();
        ano.clear();
        valorDiario.clear();
        categoria.clear();
        codigo.focus();
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
