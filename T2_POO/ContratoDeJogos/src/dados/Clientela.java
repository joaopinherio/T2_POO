package dados;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Clientela {
    private ArrayList<Cliente> cadastro;

    public Clientela() {
        // Polimorfismo de classe
        cadastro = new ArrayList<>();
    }

    public boolean addCliente(Cliente c) {
        return cadastro.add(c);
    }

    // CLIENTESINICIAL.CSV
    public void inicializaIndividual() {
        Individual ind1 = null;
        Path arq1 = Paths.get("CLIENTESINICIAL.CSV");

        try (ObjectInputStream iarq = new ObjectInputStream(Files.newInputStream(arq1))) {
            ind1 = (Individual) iarq.readObject();
            addCliente(ind1);
        } catch (ClassNotFoundException e) {
            System.out.println("1Problema na leitura do arquivo" + e.getMessage());
        } catch (IOException e) {
            System.out.println("2Problema na leitura do arquivo" + e.getMessage());
        } catch (Exception e) {
            System.out.println("3Problema na leitura do arquivo" + e.getMessage());
        }
    }
    
    public void inicializaCorporativo() {
        Corporativo corp1 = null;
        Path arq1 = Paths.get("CLIENTESINICIAL.CSV");

        try (ObjectInputStream iarq = new ObjectInputStream(Files.newInputStream(arq1))) {
            corp1 = (Corporativo) iarq.readObject();
            addCliente(corp1);
        } catch (ClassNotFoundException e) {
            System.out.println("1Problema na leitura do arquivo" + e.getMessage());
        } catch (IOException e) {
            System.out.println("2Problema na leitura do arquivo" + e.getMessage());
        } catch (Exception e) {
            System.out.println("3Problema na leitura do arquivo" + e.getMessage());
        }
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