
import com.research.util.IdentificadorSO;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Main {

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", IdentificadorSO.identificarSO());

        ChromeDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(driver, 20);

        driver.get("https://www.researchgate.net/profile/Andrea_Silva26");

        String artigo;
        String ano;

        ano = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"research\"]/div/div/div[2]/div/div/div/div/div[2]/div/div/div/div/div[2]/div/div[3]/ul/li/span"))).getText();

        for (int i = 2; i < 10; i++) {

            try {

                artigo = driver.findElementByXPath("//*[@id=\"research\"]/div/div/div[2]/div/div/div/div/div[" + i + "]/div/div/div/div/div[1]/div/a").getText();

                //*[@id="research"]/div/div/div[2]/div/div/div/div/div[2]/div/div/div/div/div[2]/div/div[3]/ul/li/span
                //*[@id="research"]/div/div/div[2]/div/div/div/div/div[3]/div/div/div/div/div[2]/div/div[3]/ul/li[1]/span
            } catch (org.openqa.selenium.NoSuchElementException a) {

                artigo = driver.findElementByXPath("//*[@id=\"research\"]/div/div/div[2]/div/div/div/div/div[" + i + "]/div/div/div/div/div[2]/div/a").getText();

            }

            System.out.println(ano + " - " + artigo);

            ano = driver.findElement(By.xpath("//*[@id=\"research\"]/div/div/div[2]/div/div/div/div/div[" + i + "]/div/div/div/div/div[2]/div/div[3]/ul/li[" + (i - 1) + "]/span")).getText();

        }

    }
}
