import com.research.util.IdentificadorSO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ExtracaoArticle {

    public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", IdentificadorSO.identificarSO());

        ChromeDriver driver = new ChromeDriver();

        HashMap<Integer, String> quantidade_link = new HashMap<>();

        quantidade_link.put(29, "https://www.researchgate.net/profile/Fernando_Modesto");

        for (Integer valor : quantidade_link.keySet()) {

            String link = quantidade_link.get(valor);
            System.out.println(link);

            driver.get(quantidade_link.get(valor));

            String artigo;
            String ano;

            int quantidade = valor;

            /**
             * Carregamento e armazenamento de alguns elementos da página
             */
            List<WebElement> elementos = driver.findElements(By.tagName("a"));

            /**
             * Espera de um segundo para poder carregar o layout completo do
             * site
             */
            Thread.sleep(2000);

            elementos = driver.findElements(By.className("nova-o-stack__item"));

            try {
                String institution = driver.findElement(By.xpath("//*[@id=\"rgw4_5cf2d35a94239\"]/div/div[2]/div/div[3]/div[1]/div[2]/div[1]/div/div/div[2]/div/div/div/div[1]/div/a")).getText();

                System.out.println(institution);

            } catch (org.openqa.selenium.NoSuchElementException a) {

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

            for (int i = 1; i < elementos.size(); i++) {

                //skills.add(elementos.get(i).getText());
                System.out.println(elementos.get(i).getText());

            }

            int contador = 0;

            for (int i = 2; i < quantidade + 2; i++) {

                try {

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
                    contador++;
                    System.out.println(ano + ";" + artigo + ";" + quantidade_link.get(valor));

                } catch (org.openqa.selenium.NoSuchElementException e) {

                }

            }

            System.out.println("Faltou: " + (quantidade - contador));

        }
    }
}
