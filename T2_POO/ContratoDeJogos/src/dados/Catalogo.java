package dados;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class Catalogo {

    private ArrayList<Jogo> catalogo;

    public Catalogo() {
        // Polimorfismo de classe
        catalogo = new ArrayList<>();
    }

    public void inicializaJogos(Path arq) {
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
            // int count = 0;
            // data.length - 5 -> comprimento total menos 5 (numero de itens no cabecalho do
            // .csv)
            // logo se nao tem mais que 5 numeros de indice sobrando quer dizer que aquela
            // iteracao eh a ultima
            for (int i = 5; i < data.length - 5; i++) {
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
        } catch (Exception e) {
            System.out.println("2Problema na leitura do arquivo" + e.getMessage());
        }
    }

    public boolean addJogo(Jogo j) {
        return catalogo.add(j);
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

}
