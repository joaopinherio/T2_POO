package dados;

import java.io.BufferedReader;
import java.io.FileReader;
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
        BufferedReader reader = null;

        try (ObjectInputStream iarq = new ObjectInputStream(Files.newInputStream(arq1))) {
            ind1 = (Individual) iarq.readObject();
            addCliente(ind1);
            iarq.close();
            // arq1.close();
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
        BufferedReader reader = null;
        String line = "";
        StringBuilder sb = new StringBuilder();

        try {
            reader = new BufferedReader(new FileReader(arq1.toFile()));
            while ((line = reader.readLine()) != null) {
                String[] rowSplit = line.split(";");
                for (String s : rowSplit) {
                    sb.append(s);
                    sb.append(',');
                }
            }
            System.out.println("haha");
            System.out.println(sb);
            String[] sbArray = sb.toString().split(",");
            for (String s : sbArray) {
                System.out.println(s);
            }
            System.out.println("INDICE 6");
            System.out.println(sbArray[6]);

            reader.close();
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