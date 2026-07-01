package joaoPinheiro.rafaelLedur.T2POO.dados;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class QuadroContrato {
    private static QuadroContrato instance;

    public static QuadroContrato getInstance() {
        if (instance == null) {
            instance = new QuadroContrato();
        }
        return instance;
    }

    private Queue<Contrato> quadro;

    public QuadroContrato() {
        quadro = new LinkedList<>();
    }

    public void addContrato(Contrato c) {
        if (quadro.offer(c))
            Collections.sort((List<Contrato>) quadro,
            Comparator.comparing(Contrato::getId));
    }

    public void inicializaContratos(String pathS, Clientela clientela, Catalogo catalogoJogos,
            LogPagamentos historicoPagamentos) {
        Path arq = Paths.get(pathS);
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
            // LOOP LEITURA/CADASTROS
            // data.length - 5 -> comprimento total menos 5 (numero de itens no cabecalho do
            // .csv)
            // logo se nao tem mais que 5 numeros de indice sobrando quer dizer que aquela
            // iteracao eh a ultima
            for (int i = 6; i < data.length - 5; i++) {
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

                Contrato contrato = new Contrato(id, periodo);
                
                contrato.setData(dataContrato);
                contrato.setCliente(clientela.pesquisaNum(numeroCliente));
                contrato.setJogo(catalogoJogos.pesquisaCod(codigoJogo));
                contrato.setFormaPagamento(historicoPagamentos.getPagamentoByCodigo(codigoPagamento));

                addContrato(contrato);

                // soma o numero de indices percorridos nessa iteracao do loop para que o indice
                // esteja correto na proxima
                i += count;
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("1Problema na leitura do arquivo" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Contratos: 2Problema na leitura do arquivo" + e.getMessage());
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

    public int numDeContratorPorCliente(Cliente cli) {
        int cont = 0;
        for (Contrato c : quadro) {
            if (c.getCliente().equals(cli)) {
                cont++;
            }
        }
        return cont;
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

    public void printContratos() {
        for (Contrato contrato : quadro) {
            System.out.println(contrato.descrever());
        }
    }

    public void getValoresFinais(QuadroContrato quadroContrato) {
        for (Contrato contrato : quadro) {
            System.out.println(contrato.descrever() + " valor final: " + contrato.calculaValorFinal(quadroContrato));
        }
    }

    public boolean isRepetido(int id) {
        if (pesquisaId(id) != null)
            return true;

        return false;
    }

    public boolean isEmpty() {
        return quadro.isEmpty();
    }

    public String toString(){
        String auxS = "";

        for (Contrato con : quadro) {
            auxS.concat(con.descrever() + "\n");
        }
        return auxS;
    }

    public List<Contrato> getLista(){
        List<Contrato> auxList = quadro
        .stream()
        .toList();

        return auxList;
    }

    public Contrato getContratoMaiorValorFinal(){
        List<Contrato> auxList = quadro
        .stream()
        .sorted(Comparator.comparingDouble(con-> con.calculaValorFinal(instance)))
        .toList();

        Contrato conFinal = auxList.getLast();
        
        if(auxList.get(auxList.size() - 2) == conFinal){
            return null;
        }
        
        return conFinal;
    }
}
