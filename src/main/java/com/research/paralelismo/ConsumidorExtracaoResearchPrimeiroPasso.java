/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.research.paralelismo;

import com.research.model.ProfessorSearchFirst;
import com.research.util.IdentificadorSO;
import com.research.util.Normalizador;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author bahia-rafael
 */
public class ConsumidorExtracaoResearchPrimeiroPasso extends Thread {

    private int id;
    private ConcurrentLinkedDeque<String> professores;
    private ConcurrentLinkedDeque<ProfessorSearchFirst> result;

    public ConsumidorExtracaoResearchPrimeiroPasso(int id, ConcurrentLinkedDeque<String> professores, ConcurrentLinkedDeque<ProfessorSearchFirst> result) {
        this.id = id;
        this.professores = professores;
        this.result = result;
    }

    @Override
    public void run() {

        /**
         * Iniciando o WebDriver, utilizando uma chave e o arquivo
         * 'chromedriver' que vai ser diferenciado pelo Sistema Operacional do
         * computador, e por conta disso utilizamos um identificador de S.O.
         */
        System.setProperty("webdriver.chrome.driver", IdentificadorSO.identificarSO());

        ChromeDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait = new WebDriverWait(driver, 10);

        /**
         * Carregar a lista de nomes que foram solicitadas
         */
        while (true) {

            try {
                String professor = professores.pop();

                /**
                 * Informo qual o site que será iniciado, com a URL já no perfil
                 * do professor.
                 */
                driver.get("https://www.researchgate.net/search/authors?q=" + professor.replace(" ", "%2B"));

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ConsumidorExtracaoResearchPrimeiroPasso.class.getName()).log(Level.SEVERE, null, ex);
                }

                List<WebElement> elementos = driver.findElements(By.tagName("a"));

                String nomeEcontrado = Normalizador.semAcento(elementos.get(5).getText());

                if (nomeEcontrado.equalsIgnoreCase(professor) || professor.contains(nomeEcontrado.toUpperCase()) || nomeEcontrado.contains(professor.toUpperCase())) {
                    
                    System.out.println("Igual: " + nomeEcontrado + " == " + professor);

                    String rotulo = "Igual";
                    String link = elementos.get(5).getAttribute("a");

                    ProfessorSearchFirst resultado = new ProfessorSearchFirst(professor, nomeEcontrado, rotulo, link);
                    result.add(resultado);
                } else {
                    String rotulo = "Diferente";

                    ProfessorSearchFirst resultado = new ProfessorSearchFirst(professor, nomeEcontrado, rotulo, null);
                    result.add(resultado);

                    System.out.println("Diferente: " + nomeEcontrado + " != " + professor);
                }
            } catch (java.util.NoSuchElementException ex) {
                break;
            }
        }
    }
}
