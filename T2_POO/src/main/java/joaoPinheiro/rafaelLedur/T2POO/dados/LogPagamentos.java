package joaoPinheiro.rafaelLedur.T2POO.dados;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Date;

public class LogPagamentos {
    private static LogPagamentos instance;

    public static LogPagamentos getInstance(){
        if(instance == null){
            instance = new LogPagamentos();
        }
        return instance;
    }

    private ArrayList<FormaPagamento> pagamentos;

    public LogPagamentos() {
        pagamentos = new ArrayList<>();
    }

    public void addPagamento(FormaPagamento f) {
        if(pagamentos.add(f));
            pagamentos.sort(Comparator.comparing(FormaPagamento::getCod));
    }

    // CLIENTESINICIAL.CSV
    public void inicializaPagamentos(String pathS, Clientela clientela) {
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
                    // por enquanto isso ta no limbo, mas depois sera inserido com um treeMap
                    // [Int][String] -> [codigo][numeroCliente]
                    String numeroCartao = data[i + count];
                    count++;
                    Date validade = new Date(data[i + count]);

                    CartaoCredito cartao = new CartaoCredito(codigo, diaVencimento, numeroCartao, validade);
                    cartao.setCliente(clientela.pesquisaNum(numeroCliente));
                    addPagamento(cartao);
                }
                if (tipo == 2) {
                    int codigo = Integer.parseInt(data[i]);
                    count++;
                    int diaVencimento = Integer.parseInt(data[i + count]);
                    count++;
                    int numeroCliente = Integer.parseInt(data[i + count]);
                    count += 2;
                    // por enquanto isso ta no limbo, mas depois sera inserido com um treeMap
                    // [Int][String] -> [codigo][numeroCliente]
                    String chavePix = data[i + count];
                    count += 2;

                    PIX pix = new PIX(codigo, diaVencimento, chavePix);
                    pix.setCliente(clientela.pesquisaNum(numeroCliente));
                    addPagamento(pix);
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
        for (FormaPagamento formaPagamento : pagamentos) {
            System.out.println(formaPagamento);
        }
    }

    public FormaPagamento getPagamentoByCodigo(int codigo) {
        return pagamentos.get(codigo);
    }

    public boolean isEmpty(){
        return pagamentos.isEmpty();
    }

    public FormaPagamento procuraByCod(int codigo){
        for (FormaPagamento f : pagamentos) {
            if(f.getCod() == codigo)
                return f;
        }
        return null;
    }

    public boolean isRepetido(int codigo){
        if(procuraByCod(codigo) != null)
            return true;

        return false;
    }

    public List<FormaPagamento> getPagamentosByCliente(Cliente c){
        List<FormaPagamento> aux = pagamentos.stream()
        .filter(p -> p.getCliente() == c)
        .collect(Collectors.toList());

        return aux;
    }
}
