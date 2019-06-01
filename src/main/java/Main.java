import com.research.util.IdentificadorSO;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", IdentificadorSO.identificarSO());

        ChromeDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(driver, 20);

        driver.get("https://www.researchgate.net/profile/Andrea_Silva26");

        String artigo;
        String ano;

        int quantidade = 8;

        /**
         * Carregamento e armazenamento de alguns elementos da página
         */
        List<WebElement> elementos = driver.findElements(By.tagName("a"));

        /**
         * Espera de um segundo para poder carregar o layout completo do site
         */
        Thread.sleep(2000);

        elementos = driver.findElements(By.className("nova-o-stack__item"));

        /**
         * Caso tenha o botão "Show All", peço para o bot clicar, para poder
         * listar por completo as skills informadas
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
         * Armazena em uma lista para poder atribuir ao Objeto ProfessorReserach
         * criado neste loops
         */
        List<String> skills = new ArrayList<>();

        for (int i = 1; i < elementos.size(); i++) {

            //skills.add(elementos.get(i).getText());
            System.out.println(elementos.get(i).getText());

        }

        for (int i = 2; i < quantidade + 2; i++) {

            Thread.sleep(2000);

            try {

                ano = driver.findElement(By.xpath("//*[@id=\"research\"]/div/div/div[2]/div/div/div/div/div[" + i + "]/div/div/div/div/div[2]/div/div[3]/ul/li/span")).getText();
                artigo = driver.findElement(By.xpath("//*[@id=\"research\"]/div/div/div[2]/div/div/div/div/div[" + i + "]/div/div/div/div/div[1]/div/a")).getText();
                
            } catch (org.openqa.selenium.NoSuchElementException a) {
                try {
                    ano = driver.findElement(By.xpath("//*[@id=\"research\"]/div/div/div[2]/div/div/div/div/div[" + i + "]/div/div/div/div/div[3]/div/div[3]/ul/li/span")).getText();
                } catch (org.openqa.selenium.NoSuchElementException e) {
                    ano = "Nao foi possivel coletar";
                }

                artigo = driver.findElement(By.xpath("//*[@id=\"research\"]/div/div/div[2]/div/div/div/div/div[" + i + "]/div/div/div/div/div[2]/div/a")).getText();

            }

            System.out.println(ano + " - " + artigo);

        }

    }
}
