package pages;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/*
    Страница просмотра вопроса
 */
public class QuestionPage extends BasePage{
    private String checkTitle;
    private static final String PAGE_LOCATOR = "//div[@id='question-header']";

    @FindBy (xpath = "//div[@id='question-header']//a")
    private WebElement questionTitle;   // заголовок страницы ответа

    public QuestionPage(String checkTitle) {
        super(PAGE_LOCATOR);
        this.checkTitle = checkTitle;
    }

    @Step("Проверка заголовка '{title}'")
    public void checkTitle(List<String> errors){
       // PageFactory.initElements(driver, this);
        if (!getText(questionTitle).equals(checkTitle))
            errors.add("Не совпадает заголовок: " + checkTitle);
    }

    @Override
    protected void initPage() {

    }
}
