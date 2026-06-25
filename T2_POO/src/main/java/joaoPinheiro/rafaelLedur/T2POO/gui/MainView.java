package joaoPinheiro.rafaelLedur.T2POO.gui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.textfield.*;

@PageTitle("T2 POO")
@Route("")
public class MainView extends VerticalLayout {
    public MainView(){
        TextField teste = new TextField("teste");
        teste.setValue("teste");

        add(teste);
    }

}
