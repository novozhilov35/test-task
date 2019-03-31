package pages;

import core.BasePage;
import io.qameta.allure.Step;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/*
    Страница Tags
 */
public class TagsPage extends BasePage {
    private static final String PAGE_LOCATOR = "//h1[normalize-space(text())='Tags']";

    @FindBy (xpath = "//input[@id='tagfilter']")
    private WebElement tagFilterEdit;  // поле ввода фильтра-поиска тэгов

    @FindBy (xpath = "//a[@class='post-tag']")
    private List<WebElement> tagsList;   // список тегов

    public TagsPage() {
        super(PAGE_LOCATOR);
    }

    @Step("Поиск в тегах строки '{value}'")
    public TagsPage searchValue(String value){
        setValue(tagFilterEdit, value);
        try {
            Thread.sleep(1000);       // сделал задержку для ожидания скриптовой перерисовки страницы
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Step("Проверка соответствия найденных тэгов '{value}'")
    public TagsPage checkTagList(String value, ErrorCollector errorCollector){
        List<String> errors = new ArrayList<>();
        for (WebElement element : tagsList){
            if (!getText(element).toLowerCase().contains(value.toLowerCase()))
                errors.add("Тэг " + getText(element) + " не содержит " + value);
        }
        if (!errors.isEmpty()){
            errorCollector.addError(new AssertionError(String.join("\n", errors)));
        }
        return this;
    }

    @Step("Переход по первой ссылке с полным соответствием тегу '{value}'")
    public ResultsPage clickEqualsTag(String value){
        for (WebElement element : tagsList){
            if (getText(element).toLowerCase().equals(value.toLowerCase())){
                clickElement(element);
                return new ResultsPage();
            }
        }
        return null;
    }

    @Override
    protected void initPage() {
    }
}
