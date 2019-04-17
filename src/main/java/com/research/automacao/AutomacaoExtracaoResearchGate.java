/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.research.automacao;

import com.research.util.FileUtil;
import com.research.util.IdentificadorSO;
import com.research.util.Normalizador;
import com.research.model.ProfessorResearch;
import com.research.validacao.ValidacaoExtracaoResearchGate;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author rafael
 */
public class AutomacaoExtracaoResearchGate {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static List<ProfessorResearch> professores = new ArrayList<>();

    /**
     * Método para navegar pelo HTML,que procurarará o botão de acesso, ou seja,
     * o botão para realizar o login no site.
     *
     * @param driver - Web driver da página para não perder referencia;
     */
    private static void clickButton(WebDriver driver) {

        List<WebElement> div = driver.findElements(By.className("react-container"));

        WebElement conteudo = div.get(0).findElement(By.tagName("div"));
        WebElement conteudo2 = conteudo.findElement(By.tagName("div"));

        WebElement conteudo3 = conteudo2.findElement(By.tagName("div"));

        WebElement conteudo4 = conteudo3.findElement(By.tagName("form"));

        WebElement conteudo5 = conteudo4.findElement(By.tagName("div"));

        List<WebElement> conteudo6 = conteudo5.findElements(By.tagName("div"));

        WebElement button = conteudo6.get(5).findElement(By.tagName("button"));

        button.click();

    }

    /**
     * Método para navegar pelo HTML, que procurarará o link que levará para o
     * perfil da pessoa buscada.
     *
     * @param driver - Web driver da página para não perder referencia;
     */
    private static void clickProfile(WebDriver driver) {

        List<WebElement> elementos = driver.findElements(By.tagName("div"));

        elementos.get(53).findElement(By.tagName("a")).click();

    }

    /**
     * Método para clicar na 'lupa' de busca, após ter inserido o nome da pessoa
     * que será buscada.
     *
     * @param driver - Web driver da página para não perder referencia;
     */
    private static void clickSearch(WebDriver driver) {
        WebElement header = driver.findElement(By.tagName("header"));
        List<WebElement> buttons = header.findElements(By.tagName("button"));

        buttons.get(1).click();
    }

    /**
     * Método para coletar as tags de skills das pessoas buscadas.
     *
     * @param driver - Web driver da página para não perder referencia;
     * @return uma lista de tópicos que se encontra na página da pessoa.
     */
    private static List<String> collectSkills(WebDriver driver) {

        List<String> skills = new ArrayList<>();

        List<WebElement> elementos = driver.findElements(By.tagName("div"));

        List<WebElement> tags = elementos.get(64).findElements(By.tagName("div"));

        for (WebElement tag : tags) {

            skills.add(tag.getText());
        }

        return skills;
    }

    /**
     * Método para coletar os titulos dos artigos que se encontram no perfil da
     * pessoa.
     *
     * @param driver - Web driver da página para não perder referencia;
     * @param quantidade - Numero de artigos que será coletado no perfil.
     * @return uma lista de tópicos que se encontra na página da pessoa.
     */
    private static List<String> collectResearch(WebDriver driver, int quantidade) {

        List<String> research = new ArrayList<>();

        List<WebElement> elementos = driver.findElements(By.tagName("div"));

        int contador = 0;

        while (true) {

            try {

                if (elementos.get(contador).getAttribute("class").equals("nova-e-text nova-e-text--size-l nova-e-text--family-sans-serif nova-e-text--spacing-none nova-e-text--color-inherit nova-v-publication-item__title")) {

                    research.add(Normalizador.semAcento(elementos.get(contador).findElement(By.tagName("a")).getText()));
                }

                if (research.size() - 1 != quantidade) {

                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    driver.manage().window().maximize();
                    js.executeScript("window.scrollBy(0,1000)");

                }

                contador++;
            } catch (org.openqa.selenium.StaleElementReferenceException a) {
                elementos = driver.findElements(By.tagName("div"));
                continue;
            } catch (java.lang.IndexOutOfBoundsException e) {
                break;
            } catch (org.openqa.selenium.WebDriverException e) {
                break;
            }
        }

        return research;
    }

    /**
     * Método para realizar a extração da quantidade de artigos que a pessoa
     * possui cadastrada em seu perfil.
     *
     * @param driver - Web driver da página para não perder referencia;
     * @return a quantidade de artigos disponíveis no perfil.
     */
    private static int qntdResearch(WebDriver driver) {

        List<WebElement> elemtentos = driver.findElements(By.tagName("div"));

        int quantidade = Integer.parseInt(elemtentos.get(63).getText().substring(5, elemtentos.get(63).getText().length() - 1));

        return quantidade;
    }

    /**
     * Método para extrair o nick_name da pessoa, através de um recorte na url
     * da página.
     *
     * @param url URL da página
     * @return o Nick_Name da pessoa
     */
    private static String collectNickName(String url) {

        String nick = url.replace("https://www.researchgate.net/profile/", "");

        return nick;
    }

    public static void main(String[] args) throws InterruptedException {

        /**
         * Credenciais para acessar o sistema
         */
        String email = "email_acesso";
        String password = "password_acesso";

        /**
         * Iniciando o WebDriver, utilizando uma chave e o arquivo
         * 'chromedriver' que vai ser diferenciado pelo Sistema Operacional do
         * computador, e por conta disso utilizamos um identificador de S.O.
         */
        System.setProperty("webdriver.chrome.driver", IdentificadorSO.identificarSO());

        driver = new ChromeDriver();

        /**
         * Informo qual o site que será iniciado, primeiramente neste caso, será
         * a url que levará para a tela de login do sistema.
         */
        driver.get("https://www.researchgate.net/login");

        wait = new WebDriverWait(driver, 10);

        /**
         * Identifico os locais de inserção das credenciais de acesso. E os
         * preencho.
         */
        driver.findElement(By.id("input-login")).sendKeys(email);
        driver.findElement(By.id("input-password")).sendKeys(password);

        /**
         * Chamada de método para localizar e clicar no botão de acesso.
         */
        clickButton(driver);

        FileReader in = null;

        Set<String> nomeSemRepeticao = ValidacaoExtracaoResearchGate.retirarRepeticao("NomeLista.txt");
        BufferedReader br = new BufferedReader(in);
        for (String linha : nomeSemRepeticao) {

            /**
             * U time de um minuto a cada busca para não realizar muitas
             * requisições em um curto período de tempo.
             */
            Thread.sleep(60000);

            /**
             * Crio um objeto da pessoa que será buscada no site.
             */
            ProfessorResearch professor = new ProfessorResearch();

            /**
             * Gravo o nome da pessoa que esta sendo utilizado no momento.
             */
            professor.setName(linha);

            try {

                /**
                 * Limpo o campo de busca caso haja algum conteúdo, e logo após
                 * insiro o nome da pessoa que esta sendo utilizada
                 */
                driver.findElement(By.cssSelector("#header-search-action")).clear();
                driver.findElement(By.cssSelector("#header-search-action")).sendKeys(Normalizador.semAcento(linha));

                /**
                 * Chamada de método para clicar no botão 'lupa', responsável
                 * pela busca.
                 */
                clickSearch(driver);

                try {
                    /**
                     * Chama m[etodo para clicar no perfil que a busca ir[a
                     * retornar
                     */
                    clickProfile(driver);

                } catch (NoSuchElementException ax) {

                    /**
                     * Caso não retorne um perfil válido, há uma tentativa de
                     * encontrar o perfil da pessoa utilizando um suposto
                     * nick_name através da URL, pois em alguns casos, o
                     * nick_name é o nome da pessoa, só que nos espaços em
                     * branco (' '), há um '_'.
                     */
                    driver.get("https://www.researchgate.net/profile/" + linha.replace(" ", "_"));
                }

                String url = driver.getCurrentUrl();

                try {

                    /**
                     * Caso encontre um perfil válido, há uma inserção do termo
                     * '/info' na URL, para ocorrer a exibição das skills da
                     * pessoa.
                     */
                    driver.get(url + "/info");

                    /**
                     * Chamada de método para poder coletar as tags de skills e
                     * já ocorre a adição da lista que será retornada ao objeto
                     * do professor que foi criado
                     */
                    professor.setSkills(collectSkills(driver)); // retorno da lista de
                } catch (org.openqa.selenium.NoSuchElementException e) {

                    /**
                     * Caso não encontre a pessoa, adiciona na lista das pessoas
                     * sem o campo nick_name preenchido.
                     */
                    professores.add(professor);

                    System.out.println(professor.toString());

                    /**
                     * Retorna para a página principal do site. E seleciona a
                     * proxima pessoa.
                     */
                    driver.get("https://www.researchgate.net");
                    continue;

                } catch (java.lang.IndexOutOfBoundsException | org.openqa.selenium.WebDriverException a) {

                    /**
                     * Caso não encontre a pessoa, adiciona na lista das pessoas
                     * sem o campo nick_name preenchido.
                     */
                    professores.add(professor);

                    System.out.println(professor.toString());

                    /**
                     * Retorna para a página principal do site. E seleciona a
                     * proxima pessoa.
                     */
                    driver.get("https://www.researchgate.net");
                    continue;

                }

                /**
                 * Caso encontre o perfil com os skills, chama o método para
                 * coletar o Nick_Name da pessoa e já vincula com a pessoa que
                 * esta sendo utilizada na lista.
                 */
                professor.setNickName(collectNickName(url)); // retorno do nick name

                /**
                 * Caso encontre um perfil válido, há uma inserção do termo
                 * '/research' na URL, para ocorrer a exibição dos artigos da
                 * pessoa.
                 */
                driver.get(url + "/research");

                int quantidade = 0;
                try {

                    /**
                     * Chama o método para coletar a quantidade de artigos que a
                     * pessoa possui em seu perfil
                     */
                    quantidade = qntdResearch(driver); // retorna quantidade de research

                } catch (java.lang.NumberFormatException e) {

                }

                /**
                 * Vinculo com o objeto da Pessoa que foi criado
                 */
                professor.setQndArtigos(quantidade);

                /**
                 * Quaso haja artigos em seu perfil, irá ocorrer o chamado do
                 * método responsável pela extração dos titulos de todos os
                 * artigos da página, e logo após, esta lista que será retornada
                 * será vinculada ao objeto criado para o professor.
                 */
                if (quantidade > 0) {

                    professor.setArtigos(collectResearch(driver, quantidade)); // retorno da lista de artigos

                }

                System.out.println(professor.toString());

                /**
                 * Adiciono a pessoa à uma lista com todas as informações
                 * preenchidas.
                 */
                professores.add(professor);
            } catch (Exception exemplo) {

                /**
                 * Caso haja alguma interrupção durante a execução do código que
                 * nao tenha um tratamento previsto, será gravado um arquivo
                 * .json para salvar todo o progresso que já foi realizado.
                 */
                System.out.println(exemplo.toString());

                try {
                    FileUtil.writerFile("Professores - Vini", professores);
                } catch (IOException ex) {
                    Logger.getLogger(AutomacaoExtracaoResearchGate.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

        /**
         * Quando finalizar a execução de todos os nomes da lista, será gravado
         * um arquivo contendo todos os resultados coletados.
         */
        try {
            FileUtil.writerFile("Professores - Vini", professores);
        } catch (IOException ex) {
            Logger.getLogger(AutomacaoExtracaoResearchGate.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
