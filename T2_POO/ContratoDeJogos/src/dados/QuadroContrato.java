package dados;

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