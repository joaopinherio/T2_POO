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
           System.out.println("TESTE IND PRE");
            System.out.println(data[11]);

            // LOOP LEITURA/CADASTROS
            //int count = 0;
            for (int i = 0; i < data.length; i++) {
                //System.out.println("HA");
                if (i > 5) { // 6 eh o indice em que comecam os valores
                    int count = 0;
                    int tipo = Integer.parseInt(data[i + 3]);
                    System.out.println("tipo:" + tipo);
                    if(tipo == 1){
                        int numero = Integer.parseInt(data[i]); count++;
                        System.out.println(numero);
                        String nome = data[i+count];count++; 
                        System.out.println(nome);
                        String email = data[i+count];count+=2;
                        System.out.println(email);
                        String cpf = data[i+count];count++;
                        System.out.println("cpf");
                        System.out.println(cpf);

                        Individual ind = new Individual(numero, nome, email, cpf);
                        System.out.println("teste ind");
                        System.out.println(ind.descrever());
                        cadastro.add(ind);
                    }
                    if(tipo == 2){
                        System.out.println("2corp");
                        System.out.println(i);
                        int numero = Integer.parseInt(data[i]); count++;
                        String nome = data[i+count];count++; 
                        System.out.println(numero);
                        String email = data[i+count];count++;
                        System.out.println(nome);
                        String cnpj = data[i+count];count++;
                        String nomeFantasia = data[i+count];count++;
                        
                        Corporativo corp = new Corporativo(numero, nome, email, cnpj, nomeFantasia);
                        System.out.println("teste corp");
                        corp.descreverRedux();
                        cadastro.add(corp);
                    }
                    System.out.println(count + "count");
                    System.out.println(i);
                    i += count -1;
                    System.out.println("indice");
                    System.out.println(i);
                }
            }

            int numero = Integer.parseInt(data[6]);
            String nome = data[7], email = data[8],
                    cnpj = data[9], nomeFantasia = data[10];

            //System.out.println("teste");
            //System.out.println(numero + " " + nome + " " + email + " " + cnpj + " " + nomeFantasia);


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

    public void printClientela(){
        for(Cliente c : cadastro){
            System.out.println(c.descreverRedux());
        }
    }

}