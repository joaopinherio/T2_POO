package joaoPinheiro.rafaelLedur.T2POO.gui;
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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Menu de Cadastros")
@Route("telaCadastros")
public class TelaCadastroView extends VerticalLayout {

    public TelaCadastroView(){
        Button cadCliente = new Button("Menu Cadastro Cliente");
        Button cadJogo = new Button("Menu Cadastro Jogo");
        Button cadPagamento = new Button("Menu Selecao de Pagamento");
        
        cadCliente.addClickListener(e -> UI.getCurrent().navigate("cadastroCliente"));
        cadJogo.addClickListener(e -> UI.getCurrent().navigate("cadastroJogo"));
        cadPagamento.addClickListener(e -> UI.getCurrent().navigate("cadastroPagamento"));
        
        add(cadCliente);
        add(cadJogo);
        add(cadPagamento);
    }
}