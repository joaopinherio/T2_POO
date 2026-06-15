package dados;

import java.util.ArrayList;

public class Clientela {
    private ArrayList<Cliente> cadastro;

    public Clientela() {
        // Polimorfismo de classe
        cadastro = new ArrayList<>();
    }

    public boolean addCliente(Cliente c) {
        return cadastro.add(c);
    }

    public Cliente pesquisaNum(int num) {
        for (Cliente c : cadastro) {
            if (c.getNumero() == num)
                return c;
        }
        return null;
    }

    public Cliente pesquisaNumTrocaNome(int num, String newNome) {
        for (Cliente c : cadastro) {
            if (c.getNumero() == num) {
                c.setNome(newNome);
                return c;
            }
        }
        return null;
    }

    public String pesquisaNumDescreve(int num) {
        for (Cliente c : cadastro) {
            if (c.getNumero() == num)
                return c.descrever();
        }
        return null;
    }

}