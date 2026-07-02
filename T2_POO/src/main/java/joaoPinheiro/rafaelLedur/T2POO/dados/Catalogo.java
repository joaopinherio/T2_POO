package joaoPinheiro.rafaelLedur.T2POO.dados;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Catalogo {
    private static Catalogo instance;

    public static Catalogo getInstance() {
        if (instance == null) {
            instance = new Catalogo();
        }
        return instance;
    }

    private ArrayList<Jogo> catalogo;

    public Catalogo() {
        // Polimorfismo de classe
        catalogo = new ArrayList<>();
    }

    public boolean inicializaJogos(String pathS) {
        Path arq = Paths.get(pathS.concat(".CSV"));
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
            for (int i = 5; i < data.length - 4; i++) {
                int count = 0;

                int codigo = Integer.parseInt(data[i]);
                count++;
                String nome = data[i + count];
                count++;
                int ano = Integer.parseInt(data[i + count]);
                count++;
                double valorDiario = Double.parseDouble(data[i + count]);
                count++;
                Categoria categoria = Categoria.valueOf(data[i + count]);

                Jogo jogo = new Jogo(codigo, nome, ano, valorDiario);

                jogo.setCategoria(categoria);
                addJogo(jogo);

                // soma o numero de indices percorridos nessa iteracao do loop para que o indice
                // esteja correto na proxima
                i += count;
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("1Problema na leitura do arquivo" + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("2Problema na leitura do arquivo" + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean salvaJogos(String pathS){
        Path arq = Paths.get(pathS.concat("JOGOS.CSV"));
        BufferedWriter writer = null;

        try{
            writer = new BufferedWriter(new FileWriter(arq.toFile()));
            writer.write("codigo" + ";" + "nome" + ";" + "ano" + ";" + "valorMensal"  + ";" + "categoria" + "\n");
            for (Jogo j : catalogo) {
                writer.write(j.getCsv());
                
                if(j != catalogo.getLast())
                    writer.write("\n");
            }

            writer.close();
        } catch (IOException e){
            System.out.println("1 Problema na escrita do arquivo" + e);
            return false;
        } catch (Exception e){
            System.out.println("2 Problema na escrita do arquivo" + e);
            return false;
        }
        return true;
    }

    public void addJogo(Jogo j) {
        if (catalogo.add(j) && !(isRepetido(j.getCodigo())))
            catalogo.sort(Comparator.comparing(Jogo::getCodigo));
    }

    public Jogo pesquisaCod(int cod) {
        for (Jogo j : catalogo) {
            if (j.getCodigo() == cod)
                return j;
        }
        return null;
    }

    public Jogo pesquisaCategoria(Categoria cat) {
        for (Jogo j : catalogo) {
            if (j.getCategoria().equals(cat))
                return j;
        }
        return null;
    }

    public void printJogosByCategoria(Categoria cat) {
        for (Jogo j : catalogo) {
            if (j.getCategoria().equals(cat))
                System.out.println("6:" + j.descreverRedux());
        }
    }

    public boolean categoriaEx(String input) {

        for (Categoria cat_s : Categoria.values()) {
            if (input.equals(cat_s.getExtenso()))
                return true;
        }
        return false;
    }

    public void pesquisaContrato(int codigo) {
        if (pesquisaCod(codigo) != null) {
            System.out.println(pesquisaCod(codigo).getContrato().descrever());
        } else {
            System.out.println("nao tem");
        }
    }

    // queria que fosse uma funcao contrato rm
    public String rmContratoJogoByCodigo(int codigoInput) {
        Contrato contratoOut;
        for (Jogo j : catalogo) {
            if (j.getCodigo() == codigoInput) {
                if (j.getContrato() != null) {
                    contratoOut = j.getContrato();
                    j.rmContrato();
                    System.out.println("8:contrato removido: id." + contratoOut.getId());
                } else {
                    System.out.println("8:nenhum contrato encontrado.");
                }
            }
        }
        return null;
    }

    public void printJogos() {
        for (Jogo j : catalogo) {
            System.out.println(j.descrever());
        }
    }

    public ArrayList<Jogo> getLista() {
        return catalogo;
    }

    public boolean isRepetido(int cod) {
        Jogo j = pesquisaCod(cod);
        return catalogo.contains(j);
    }

    public boolean isEmpty() {
        return catalogo.isEmpty();
    }

    private Jogo getJogoByValor(double valor) {
        for (Jogo j : catalogo) {
            if (j.getValorDiario() == valor)
                return j;
        }
        return null;
    }

    public Jogo getJogoMaiorValor(){
        List<Jogo> auxList = catalogo
        .stream()
        .sorted(Comparator.comparing(Jogo::getValorDiario))
        .toList();

        if (auxList.getLast() != auxList.get(auxList.size() - 2))
            return auxList.getLast();

        return null;
    }

    public String toString(){
        String result = "";
        for (Jogo j : catalogo) {
            result.concat(j.descrever() + "\n");
        }
        return result;
    }
}