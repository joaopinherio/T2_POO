package joaoPinheiro.rafaelLedur.T2POO.dados;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.TreeMap;

public class LogPagamentos {
     private TreeMap<Integer, FormaPagamento> pagamentos;

     public LogPagamentos(){
        pagamentos = new TreeMap<>();
     }

     public void addPagamento(Integer numeroCliente, FormaPagamento f){
        pagamentos.putIfAbsent(numeroCliente, f);
     }

    // CLIENTESINICIAL.CSV
    public void inicializaPagamentos(Path arq, Clientela clientela) {
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
            // data.length - 5 -> comprimento total menos 5 (numero de itens no cabecalho do
            // .csv)
            // logo se nao tem mais que 5 numeros de indice sobrando quer dizer que aquela
            // iteracao eh a ultima
            // 6 eh o indice em que comecam os valores
            for (int i = 6; i < data.length - 4; i++) {
                int count = 0;
                int tipo = Integer.parseInt(data[i + 3]);
                if (tipo == 1) {
                    int codigo = Integer.parseInt(data[i]);
                    count++;
                    int diaVencimento = Integer.parseInt(data[i + count]);
                    count++;
                    int numeroCliente = Integer.parseInt(data[i + count]);
                    count += 2;
                    //por enquanto isso ta no limbo, mas depois sera inserido com um treeMap [Int][String] -> [codigo][numeroCliente]
                    String numeroCartao = data[i + count];
                    count++;
                    Date validade = new Date(data[i + count]);

                    CartaoCredito cartao = new CartaoCredito(codigo, diaVencimento, numeroCartao, validade);
                    cartao.setCliente(clientela.pesquisaNum(numeroCliente));
                    addPagamento(codigo, cartao);
                }
                if (tipo == 2) {
                    int codigo = Integer.parseInt(data[i]);
                    count++;
                    int diaVencimento = Integer.parseInt(data[i + count]);
                    count++;
                    int numeroCliente = Integer.parseInt(data[i + count]);
                    count += 2;
                    //por enquanto isso ta no limbo, mas depois sera inserido com um treeMap [Int][String] -> [codigo][numeroCliente]
                    String chavePix = data[i + count];
                    count += 2;

                    PIX pix = new PIX(codigo, diaVencimento, chavePix);
                    pix.setCliente(clientela.pesquisaNum(numeroCliente));
                    addPagamento(codigo, pix);
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

    public void printPagamentos() {
        for (FormaPagamento p : pagamentos.values()) {
            System.out.println(p.descrever());
        }
    }

    public FormaPagamento getPagamentoByCodigo(int codigo){
        return pagamentos.get(codigo);
    }


}
