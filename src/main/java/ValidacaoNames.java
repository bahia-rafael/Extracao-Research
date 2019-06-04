import com.research.util.LoadUtil;
import com.research.validacao.ValidacaoExtracaoResearchGate;
import java.util.List;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rafa
 */
public class ValidacaoNames {

    public static void main(String[] args) {

        List<JsonName> lista = LoadUtil.loadListObjects("ValidacaoName", JsonName[].class);

        Set<String> nomes = ValidacaoExtracaoResearchGate.retirarRepeticao("Names.txt");

        for (int i = 0; i < lista.size(); i++) {
            for (String nome : nomes) {
                if (nome.equalsIgnoreCase(lista.get(i).getNome())) {
                    System.out.println("quantidade_link.put(" + lista.get(i).getQuantidade_Artigos() + ", \"" + lista.get(i).getLink() + "\");");
                }
            }
        }

    }

}

class JsonName {

    private String Nome;
    private String Link;
    private String Quantidade_Artigos;

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String Link) {
        this.Link = Link;
    }

    public String getQuantidade_Artigos() {
        return Quantidade_Artigos;
    }

    public void setQuantidade_Artigos(String Quantidade_Artigos) {
        this.Quantidade_Artigos = Quantidade_Artigos;
    }

}
