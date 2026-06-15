package dados;

import java.util.ArrayList;

public class Catalogo {

    private ArrayList<Jogo> catalogo;

    public Catalogo() {
        // Polimorfismo de classe
        catalogo = new ArrayList<>();
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

}
