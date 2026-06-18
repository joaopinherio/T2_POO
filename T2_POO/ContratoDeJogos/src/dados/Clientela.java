package dados;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    // CLIENTESINICIAL.CSV
    public void inicializaClientes(Path arq) {
        Corporativo corp1 = null;
        BufferedReader reader = null;
        String line = "";
        StringBuilder sb = new StringBuilder();

        try {
            reader = new BufferedReader(new FileReader(arq.toFile()));
            while ((line = reader.readLine()) != null) {
                String[] rowSplit = line.split(";");
                for (String s : rowSplit) {
                    sb.append(s);
                    sb.append(',');
                }
            }
            String[] data = sb.toString().split(",");
            /*
             * TESTES DOS DADOS SENDO REPASSADOS
             * for (String s : data) {
             * System.out.println(s);
             * }
             * System.out.println("INDICE 6");
             */

            // LOOP LEITURA/CADASTROS
            // int count = 0;
            // data.length - 5 -> comprimento total menos 5 (numero de itens no cabecalho do
            // .csv)
            // logo se nao tem mais que 5 numeros de indice sobrando quer dizer que aquela
            // iteracao eh a ultima
            // 6 eh o indice em que comecam os valores
            for (int i = 6; i < data.length - 5; i++) {
                int count = 0;
                int tipo = Integer.parseInt(data[i + 3]);
                if (tipo == 1) {
                    int numero = Integer.parseInt(data[i]);
                    count++;
                    String nome = data[i + count];
                    count++;
                    String email = data[i + count];
                    count += 2;
                    String cpf = data[i + count];

                    Individual ind = new Individual(numero, nome, email, cpf);
                    cadastro.add(ind);
                }
                if (tipo == 2) {
                    int numero = Integer.parseInt(data[i]);
                    count++;
                    String nome = data[i + count];
                    count++;
                    String email = data[i + count];
                    count++;
                    String cnpj = data[i + count];
                    count++;
                    String nomeFantasia = data[i + count];

                    Corporativo corp = new Corporativo(numero, nome, email, cnpj, nomeFantasia);
                    addCliente(corp);
                }

                // soma o numero de indices percorridos nessa iteracao do loop para que o indice
                // esteja correto na proxima
                i += count;
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("1Problema na leitura do arquivo" + e.getMessage());
        } catch (Exception e) {
            System.out.println("2Problema na leitura do arquivo" + e.getMessage());
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

    public void printClientela() {
        for (Cliente c : cadastro) {
            System.out.println(c.descreverRedux());
        }
    }

}