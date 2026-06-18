package dados;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class QuadroContrato {
    private Queue<Contrato> quadro;

    public QuadroContrato() {
        // Polimorfismo de classe
        quadro = new LinkedList<>();
    }

    public boolean addContrato(Contrato c) {
        return quadro.offer(c);
    }

    public void inicializaContratos(Path arq) {
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
            System.out.println("INDICE");
            System.out.println(data[5]);
             */

            // LOOP LEITURA/CADASTROS
            // data.length - 5 -> comprimento total menos 5 (numero de itens no cabecalho do
            // .csv)
            // logo se nao tem mais que 5 numeros de indice sobrando quer dizer que aquela
            // iteracao eh a ultima
            for (int i = 6; i < data.length - 4; i++) {
                int count = 0;

                int id = Integer.parseInt(data[i]);
                count++;
                Date dataContrato = new Date(data[i + count]);
                count++;
                int periodo = Integer.parseInt(data[i + count]);
                count++;
                int numeroCliente = Integer.parseInt(data[i + count]);
                count++;
                int codigoJogo = Integer.parseInt(data[i + count]);
                count++;
                int codigoPagamento = Integer.parseInt(data[i + count]);
                count++;

                Contrato contrato = new Contrato(id, periodo);
                
                //contrato.setCategoria(categoria);
                addContrato(contrato);

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

    public boolean rmContrato(Contrato c) {
        return quadro.remove(c);
    }

    public Contrato pesquisaId(int id) {
        for (Contrato c : quadro) {
            if (c.getId() == id)
                return c;
        }
        return null;
    }

    public Contrato pesquisaContratoByJogo(Jogo j) {
        for (Contrato c : quadro) {
            if (c.getJogo() == j) {
                return c;
            }
        }
        return null;
    }

    public boolean exContratos() {
        return quadro.isEmpty();
    }

    public void descreveTodosContratos() {
        for (Contrato c : quadro) {
            if (c.getCliente() != null && c.getJogo() != null) {
                System.out.println("9:" + c.descrever());
            }
        }
    }

    public Queue pesquisaTodosContratantes() {
        Queue arrayC = new LinkedList<Cliente>();
        for (Contrato c : quadro) {
            if (arrayC.contains(c.getCliente()) == false) {
                arrayC.offer(c.getCliente());
            }
        }
        return arrayC;
    }

    // 10
    public String getClienteMaiorValor() {
        Queue<Cliente> arrayC = pesquisaTodosContratantes();
        Cliente maioral = null;
        double maior = 0;

        for (Cliente c : arrayC) {
            double somatorio = 0;
            for (Contrato con : quadro) {
                if (con.getCliente() != null && con.getJogo() != null) {
                    if (con.getCliente() == c) {
                        somatorio += con.getJogo().getValorDiario();
                    }
                }
            }
            if (somatorio > maior) {
                maior = somatorio;
                maioral = c;
            }
        }
        return maioral.descreverRedux() + ";" + maior;
    }

}