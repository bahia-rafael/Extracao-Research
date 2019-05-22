/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.research.automacao;

import com.research.model.ProfessorResearch;
import com.research.paralelismo.ConsumidorExtracaoResearchPrimeiroPasso;
import com.research.util.FileUtil;
import com.research.util.IdentificadorSO;
import com.research.validacao.ValidacaoExtracaoResearchGate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author bahia-rafael
 */
public class ExecucaoSegundoPasso {

    public static void main(String[] args) {

        /**
         * Carregar a lista de nomes que foram solicitadas
         */
        Set<String> professores = ValidacaoExtracaoResearchGate.retirarRepeticao("NomeLista - Segundo Passo.txt");

        List<ProfessorResearch> result = new ArrayList<>();

        /**
         * Iniciando o WebDriver, utilizando uma chave e o arquivo
         * 'chromedriver' que vai ser diferenciado pelo Sistema Operacional do
         * computador, e por conta disso utilizamos um identificador de S.O.
         */
        System.setProperty("webdriver.chrome.driver", IdentificadorSO.identificarSO());

        ChromeDriver driver = new ChromeDriver();

        /**
         * Extraindo um nome da lista que foi carregada
         */
        for (String nome : professores) {

            try {
                /**
                 * Criaçao de um objeto ProfessorResearch
                 */
                ProfessorResearch professor = new ProfessorResearch();

                /**
                 * Atribuição do nome extraido para o objeto do
                 * ProfessorResearch que foi criado
                 */
                professor.setName(nome);

                /**
                 * Informo qual o site que será iniciado, com a URL de busca
                 * utilizando o nome do Professor.
                 */
                driver.get("https://www.researchgate.net/search/authors?q=" + nome.replace(" ", "%2B"));

                /**
                 * Espera de um segundo para poder carregar o layout completo do
                 * site
                 */
                espera();

                /**
                 * Carregamento e armazenamento de alguns elementos da página
                 */
                List<WebElement> elementos = driver.findElements(By.tagName("a"));

                /**
                 * Espera de um segundo para poder carregar o layout completo do
                 * site
                 */
                espera();

                /**
                 * Seleciona o perfil que foi retornado pelo site
                 */
                elementos.get(5).click();

                elementos = driver.findElements(By.className("nova-o-stack__item"));

                /**
                 * Caso tenha o botão "Show All", peço para o bot clicar, para
                 * poder listar por completo as skills informadas
                 */
                try {
                    driver.findElement(By.cssSelector("#about > div > div > div:nth-child(3) > div > div > div:nth-child(2) > div > div:nth-child(2) > button")).click();
                } catch (org.openqa.selenium.NoSuchElementException ex) {

                }

                /**
                 * Armazena todas as skills que estiverem no site
                 */
                elementos = driver.findElements(By.className("sub-section"));

                /**
                 * Armazena em uma lista para poder atribuir ao Objeto
                 * ProfessorReserach criado neste loops
                 */
                List<String> skills = new ArrayList<>();

                for (int i = 0; i < elementos.size(); i++) {

                    skills.add(elementos.get(i).getText());

                }

                professor.setSkills(skills);

                /**
                 * Localizar onde esta o botão Research para extrair a
                 * quantidade de pesquisar que o professor possui, e ao final
                 * atribui ao objeto do ProfessorResearch
                 */
                elementos = driver.findElements(By.tagName("button"));

                for (WebElement elemento : elementos) {
                    if (elemento.getText().contains("Research")) {
                        professor.setQndArtigos(Integer.parseInt(elemento.getText().replace("Research ", "")));
                    }
                }

                /**
                 * Identifica e armazena o nickname do professor
                 */
                String nick = driver.getCurrentUrl().replace("https://www.researchgate.net/profile/", "");

                professor.setNickName(nick);

                /**
                 * Adiciono o professor a uma lista para poder gerar um
                 * resultado no final da execuçao
                 */
                result.add(professor);

            } catch (java.util.NoSuchElementException ex) {
                break;
            } catch (NumberFormatException ax) {
                System.out.println(ax.getMessage());
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }

        /**
         * Gravo a lista gerada para o arquivo, e no fim gerar uma planilha
         */
        try {
            FileUtil.writerFile("Resultado - Segundo Passo", result);
        } catch (IOException ex) {
            Logger.getLogger(ExecucaoSegundoPasso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void espera() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ConsumidorExtracaoResearchPrimeiroPasso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
