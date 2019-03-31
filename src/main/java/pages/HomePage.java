package pages;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/*
    Главная страница
 */
public class HomePage extends BasePage{
    private static final String PAGE_LOCATOR = "//header//span[text()='Stack Overflow']";

    @FindBy (xpath = "//form[@id='search']//input")
    private WebElement searchEdit;   // инпут для поиска

    @FindBy (xpath = "//form[@id='search']//button")
    private WebElement searchButton;   // кнопка поиска

    public HomePage() {
        super(PAGE_LOCATOR);
    }

    @Step("Поиск строки '{value}'")
    public ResultsPage searchValue(String value){
        setValue(searchEdit, value);
        clickElement(searchButton);
        return new ResultsPage();
    }

    @Override
    protected void initPage() {

    }
}
