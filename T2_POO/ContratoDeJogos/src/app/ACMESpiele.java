package app;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Locale;
import dados.*;

public class ACMESpiele {
    private Clientela clientela;
    private Catalogo catalogo;
    private QuadroContrato quadroContrato;

    // Atributos para redirecionamento de E/S
    // private Scanner entrada = new Scanner(System.in); // Atributo para entrada
    // padrao (teclado)
    private PrintStream saidaPadrao = System.out; // Guarda a saida padrao - tela (console)
    private final String nomeArquivoEntrada = "datain.txt"; // Nome do arquivo de entrada de dados
    private final String nomeArquivoSaida = "dataout.txt"; // Nome do arquivo de saida de dados
    private Scanner entrada = null;

    public ACMESpiele() {

        redirecionaEntrada();
        redirecionaSaida();

        catalogo = new Catalogo();
        clientela = new Clientela();
        quadroContrato = new QuadroContrato();
    }

    public void executar() {

        // 1
        cadastraClienteInd();

        // 2
        cadastraClienteCorp();

        // 3

        cadastraJogos();

        // 4
        cadastraContratos();

        // 5
        consultarCodigo();

        // 6

        cosultarJogoCategoria();

        // 7
        mudarNomeCliente();

        // 8

        rmContratoDeJogo();

        // 9

        listaTodosContratos();

        // 10

        clientesMaiorValorContratado();

    }

    // 1
    private void cadastraClienteInd() {
        int numero = 0;
        String nome = null, email = null, cpf = null;

        do {
            numero = entrada.nextInt();
            if (numero != -1) {
                entrada.nextLine();
                nome = entrada.nextLine();
                email = entrada.nextLine();
                cpf = entrada.nextLine();

                Individual ind = new Individual(numero, nome, email, cpf);
                if (clientela.pesquisaNum(ind.getNumero()) != null)
                    System.out.println("1:erro-numero repetido.");
                else {
                    System.out.println("1:" + ind.descrever());
                    clientela.addCliente(ind);
                    // numero = -1;
                }
            }
        } while (numero != -1);

    }

    // 2
    private void cadastraClienteCorp() {

        int numero = 0;
        String nome = null, email = null, cnpj = null, nomeFantasia = null;

        do {
            numero = entrada.nextInt();
            if (numero != -1) {
                entrada.nextLine();
                nome = entrada.nextLine();
                email = entrada.nextLine();
                cnpj = entrada.nextLine();
                nomeFantasia = entrada.nextLine();

                Corporativo corp = new Corporativo(numero, nome, email, cnpj, nomeFantasia);
                if (clientela.pesquisaNum(corp.getNumero()) != null)
                    System.out.println("2:erro-numero repetido.");
                else {
                    System.out.println("2:" + corp.descreverComplete());
                    clientela.addCliente(corp);
                }
            }
        } while (numero != -1);

    }

    // 3
    private void cadastraJogos() {
        int codigo = 0, ano = 0;
        String nome = null, categoria = null;
        int valorDiario = 0;

        do {
            codigo = entrada.nextInt();
            if (codigo != -1) {
                entrada.nextLine();
                nome = entrada.nextLine();
                ano = entrada.nextInt();
                valorDiario = entrada.nextInt();
                // nao faz parte do constructor de Jogo
                entrada.nextLine();
                categoria = entrada.nextLine();

                boolean autenticaCod = false, autenticaCategoria = false;

                Jogo j = new Jogo(codigo, nome, ano, valorDiario);
                if (catalogo.pesquisaCod(j.getCodigo()) != null) {
                    System.out.println("3:erro-codigo repetido.");
                } else {
                    autenticaCod = true;
                }
                if (j.categoriaEx(categoria) == false) {
                    System.out.println("3:erro-categoria inexistente.");
                } else {
                    j.setCategoria(Categoria.valueOf(categoria));
                    autenticaCategoria = true;
                }

                if (autenticaCod == true && autenticaCategoria == true) {
                    System.out.println("3:" + j.descrever());
                    catalogo.addJogo(j);
                }
            }
        } while (codigo != -1);

    }

    // 4
    private void cadastraContratos() {
        int id = 0, periodo = 0;
        int numeroCliente = 0, codigoJogo = 0;

        do {
            id = entrada.nextInt();
            if (id != -1) {
                periodo = entrada.nextInt();
                numeroCliente = entrada.nextInt();
                codigoJogo = entrada.nextInt();

                Contrato con = new Contrato(id, periodo);

                boolean autenticaNum = false, autenticaId = false, autenticaCod = false;

                if (quadroContrato.pesquisaId(con.getId()) != null)
                    System.out.println("4:erro-id repetido");
                else {
                    autenticaNum = true;
                    // quadroContrato.addContrato(con);
                }

                if (clientela.pesquisaNum(numeroCliente) == null) {
                    System.out.println("4:erro-cliente inexistente.");
                } else {
                    con.setCliente(clientela.pesquisaNum(numeroCliente));
                    autenticaId = true;
                }

                if (catalogo.pesquisaCod(codigoJogo) == null) {
                    System.out.println("4:erro-jogo inexistente.");
                } else {
                    Jogo jAux = catalogo.pesquisaCod(codigoJogo);
                    con.setJogo(catalogo.pesquisaCod(codigoJogo));
                    jAux.setContrato(con);
                    autenticaCod = true;
                }

                if (autenticaNum == true && autenticaId == true && autenticaCod == true) {
                    System.out.println("4:" + con.descrever());
                    quadroContrato.addContrato(con);
                }
            }

        } while (id != -1);
    }

    // 5
    private void consultarCodigo() {
        int codigoJogo = 0;

        codigoJogo = entrada.nextInt();

        if (catalogo.pesquisaCod(codigoJogo) == null) {
            System.out.println("5:erro-codigo inexistente.");
        } else {
            System.out.println("5:" + catalogo.pesquisaCod(codigoJogo).descreverRedux5());
        }
    }

    // 6
    private void cosultarJogoCategoria() {
        boolean autenticaCategoria = false, autenticaPesquisa = false;
        entrada.nextLine();
        String inputCategoria = entrada.nextLine();

        if (catalogo.categoriaEx(inputCategoria) == false) {
            System.out.println("6:erro-categoria inexistente.");
        } else
            autenticaCategoria = true;

        if (autenticaCategoria == true) {
            if (catalogo.pesquisaCategoria(Categoria.valueOf(inputCategoria)) == null) {
                System.out.println("6:erro-nenhum jogo encontrado.");
            } else
                autenticaPesquisa = true;
        }

        if (autenticaCategoria == true && autenticaPesquisa == true)
            catalogo.printJogosByCategoria(Categoria.valueOf(inputCategoria));
    }

    // 7
    private void mudarNomeCliente() {
        // input
        int clienteInput = entrada.nextInt();
        entrada.nextLine();
        String newName = entrada.nextLine();

        if (clientela.pesquisaNumTrocaNome(clienteInput, newName) == null) {
            System.out.println("7:erro-numero inexistente.");
        } else {
            System.out.println("7:" + clientela.pesquisaNumDescreve(clienteInput));
        }
    }

    // 8
    private void rmContratoDeJogo() {
        int codigoInput = entrada.nextInt();
        boolean autenticaCod = false, autenticaContrato = false;

        Jogo jAux = catalogo.pesquisaCod(codigoInput);

        if (catalogo.pesquisaCod(codigoInput) == null) {
            System.out.println("8:erro-codigo inexistente.");
        } else {
            autenticaCod = true;
        }

        if (autenticaCod == true) {
            if (quadroContrato.pesquisaContratoByJogo(jAux) == null) {
                System.out.println("8:nenhum contrato encontrado");
            } else {
                autenticaContrato = true;
            }
        }

        if (autenticaCod == true && autenticaContrato == true) {
            System.out.println("8:contrato removido: " + quadroContrato.pesquisaContratoByJogo(jAux).getId());
            quadroContrato.rmContrato(jAux.getContrato());
        }
    }

    // 9
    private void listaTodosContratos() {

        if (quadroContrato.exContratos() == false) {
            quadroContrato.descreveTodosContratos();

        } else {
            System.out.println("9:erro-nenhum contrato cadastrado.");
        }
    }

    // 10
    private void clientesMaiorValorContratado() {
        if (quadroContrato.exContratos() == false) {
            // 10:número;nome;email;somatório de valorMinuto
            System.out.println("10:" + quadroContrato.getClienteMaiorValor());
        } else {
            System.out.println("10:erro-nenhum contrato encontrado.");
        }
    }

    // Redireciona Entrada de dados para arquivos em vez de teclado
    // Chame este metodo para redirecionar a leitura de dados para arquivos
    private void redirecionaEntrada() {
        try {
            BufferedReader streamEntrada = new BufferedReader(new FileReader(nomeArquivoEntrada));
            entrada = new Scanner(streamEntrada); // Usa como entrada um arquivo
        } catch (Exception e) {
            System.out.println(e);
        }
        Locale.setDefault(Locale.ENGLISH); // Ajusta para ponto decimal
        entrada.useLocale(Locale.ENGLISH); // Ajusta para leitura para ponto decimal
    }

    // Redireciona Saida de dados para arquivos em vez da tela (terminal)
    // Chame este metodo para redirecionar a escrita de dados para arquivos
    private void redirecionaSaida() {
        try {
            PrintStream streamSaida = new PrintStream(new File(nomeArquivoSaida), Charset.forName("UTF-8"));
            System.setOut(streamSaida); // Usa como saida um arquivo
        } catch (Exception e) {
            System.out.println(e);
        }
        Locale.setDefault(Locale.ENGLISH); // Ajusta para ponto decimal
    }

}
