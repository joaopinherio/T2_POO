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

    public void inicializaCorporativo(Path arq) {
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
            System.out.println(data[7]);

            // LOOP LEITURA/CADASTROS
            //int count = 0;
            for (int i = 0; i > data.length; i++) {
                if (i > 5) { // 6 eh o indice em que comecam os valores
                    int count = i;
                    int tipo = count + 3;
                    if(tipo == 1){
                        int numero = Integer.parseInt(data[count]); count++;
                        String nome = data[count];count++; 
                        String email = data[count];count++;
                        String cpf = data[count];count += 2;

                        Individual ind = new Individual(numero, nome, email, cpf);
                        cadastro.add(ind);
                    }
                    if(tipo == 2){
                        int numero = Integer.parseInt(data[count]); count++;
                        String nome = data[count];count++; 
                        String email = data[count];count++;
                        String cnpj = data[count];count += 2;
                        String nomeFantasia = data[count];count++;
                        
                        Corporativo corp = new Corporativo(numero, nome, email, cnpj, nomeFantasia);
                        cadastro.add(corp);
                    }
                    i += count;
                }
            }

            int numero = Integer.parseInt(data[6]);
            String nome = data[7], email = data[8],
                    cnpj = data[9], nomeFantasia = data[10];

            System.out.println("teste");
            System.out.println(numero + " " + nome + " " + email + " " + cnpj + " " + nomeFantasia);


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