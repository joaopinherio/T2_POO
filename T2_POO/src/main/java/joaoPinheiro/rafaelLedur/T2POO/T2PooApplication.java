package joaoPinheiro.rafaelLedur.T2POO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class T2PooApplication extends VerticalLayout{

	public static void main(String[] args) {
		SpringApplication.run(T2PooApplication.class, args);

		TextField teste = new TextField("teste");

		add(teste);
	}

}
