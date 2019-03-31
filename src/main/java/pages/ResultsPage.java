package pages;

import core.BasePage;
import io.qameta.allure.Step;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
    Страница списка результатов
 */
public class ResultsPage extends BasePage{
    private static final String PAGE_LOCATOR = "//div[contains(@class,'question-summary')]";

    @FindBy (xpath = "//div[@class='result-link']//a")
    private List<WebElement> searchResultListLink;   // список заголовоков страниц (ссылок) результатов поиска

    @FindBy (xpath = "//div[contains(@class,'js-search-results')]//div[@class='excerpt']")
    private List<WebElement> searchResultListText;   // список результатов поиска (куски текста, в которых найдены совпадения)

    @FindBy (xpath = "//div[@id='left-sidebar']//li/a[normalize-space(text())='Tags']")
    private WebElement tagsLink; // ссылка на страницу Tags в боковом меню

    @FindBy (xpath = "//div[contains(@class,'question-summary')]//div[starts-with(@class,'tags')]")
    private List<WebElement> searchResultTagsList;   //

    public ResultsPage() {
        super(PAGE_LOCATOR);
    }

    @Step("Проверка каждого результата на первой странице на '{value}'")
    public ResultsPage checkResultList(String value, ErrorCollector errorCollector){
        /*  сделал отдельный список errors только для корректного вывода всех ошибок в allure
            простая проверка errorCollector.checkThat() отображала в отчете allure только последнюю,
            хотя в логе присутствовали все
         */
        List<String> errors = new ArrayList<>();
        for (WebElement element : searchResultListText){
            if (!getText(element).toLowerCase().contains(value.toLowerCase()))
                errors.add("Текст не содержит: " + getText(element));
           // errorCollector.checkThat("Substring", element.getText(), containsString(value));
        }
        if (!errors.isEmpty()){
            errorCollector.addError(new AssertionError(String.join("\n", errors)));
        }
        return this;
    }

    @Step("Переход на каждую страницу результата поиска'")
    public ResultsPage checkEachQuestion(ErrorCollector errorCollector){
        List<String> errors = new ArrayList<>();
        int resultsSize = searchResultListLink.size();
        for (int i = 0; i<resultsSize; i++){
            QuestionPage questionPage = clickQuestionByIndex(i);
            questionPage.checkTitle(errors);
            navigateBack();
            waitLoadPage(PAGE_LOCATOR);
        }
        if (!errors.isEmpty()){
            errorCollector.addError(new AssertionError(String.join("\n", errors)));
        }
        return this;
    }

    private QuestionPage clickQuestionByIndex(int index){
        WebElement resultLink = searchResultListLink.get(index);
        String checkTitle = getText(resultLink).substring(3);   // берем без "Q: "
        clickElement(resultLink);
        return new QuestionPage(checkTitle);
    }

    public TagsPage clickTagsLink(){
        clickElement(tagsLink);
        return new TagsPage();
    }

    @Step("Проверка наличия тэга '{value}' в каждом результате на первой странице")
    public ResultsPage checkTagResultList(String value, ErrorCollector errorCollector){
        List<String> errors = new ArrayList<>();
        for (WebElement element : searchResultTagsList){
            String[] tags = getText(element).split(" ");
            if (!Arrays.asList(tags).contains(value))
                    errors.add("Нет тэга: " + getText(element));
        }
        if (!errors.isEmpty()){
            errorCollector.addError(new AssertionError(String.join("\n", errors)));
        }
        return this;
    }

    @Override
    protected void initPage() {
    }
}
