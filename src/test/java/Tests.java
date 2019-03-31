import org.junit.*;
import org.junit.rules.ErrorCollector;

import pages.HomePage;
import pages.ResultsPage;
import pages.TagsPage;

import static core.Driver.getCurrentURL;
import static core.Driver.goToURL;

/*
1) Перейти на внешний ресурс: http://stackoverflow.com/
2) В строку поиска ввести значение «webdriver».
3) Проверить, что в каждом результате представлено слово WebDriver.
4) Войти в каждое обсуждения из выборки и убедиться, что перешли именно в эту тему (проверить заголовок обсуждения).
5) Перейти в раздел Tags
6) В строку поиска ввести значение – webdriver. Убедиться, что в результате присутствуют элементы содержащее слово webdriver.
7) Найти в результатах тэг по точному совпадению поискового запроса и кликнуть по нему, проверить, что после перехода отображаются обсуждения помеченные тэгом (уточнение имеется ввиду поисковый тег, а не HTML) webdriver.
*/
public class Tests {
    private static final String URL = "https://stackoverflow.com/";

    private static final String SEARCH_STR = "webdriver";

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @BeforeClass
    public static void init(){
        core.Driver.init();
    }

    @Test
    public void testTask() {
        //  1) Перейти на внешний ресурс: http://stackoverflow.com/
        goToURL(URL);
        Assert.assertTrue(getCurrentURL().equals(URL));
        HomePage homePage = new HomePage();

        //  2) В строку поиска ввести значение «webdriver».
        ResultsPage resultsPage = homePage.searchValue(SEARCH_STR);

        //  3) Проверить, что в каждом результате представлено слово WebDriver.
        resultsPage.checkResultList(SEARCH_STR, errorCollector);

        //  4) Войти в каждое обсуждения из выборки и убедиться, что перешли именно в эту тему (проверить заголовок обсуждения).
        resultsPage.checkEachQuestion(errorCollector);

        //  5) Перейти в раздел Tags
        TagsPage tagsPage = resultsPage.clickTagsLink();

        //  6) В строку поиска ввести значение – webdriver.
        tagsPage.searchValue(SEARCH_STR)
                .checkTagList(SEARCH_STR, errorCollector);  //    Убедиться, что в результате присутствуют элементы содержащее слово webdriver (названия тэгов).

        //  7) Найти в результатах тэг по точному совпадению поискового запроса и кликнуть по нему
        resultsPage = tagsPage.clickEqualsTag(SEARCH_STR);

        //  проверить, что после перехода отображаются обсуждения помеченные тэгом webdriver.
        resultsPage.checkTagResultList(SEARCH_STR, errorCollector);

    }

    @AfterClass
    public static void destroy(){
        core.Driver.close();
    }
}
