package core;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import static core.Driver.getDriver;

public abstract class BasePage {
    private String pageLocator;
    protected BasePage(String pageLocator){
        PageFactory.initElements(getDriver(), this);
        this.pageLocator = pageLocator;
        waitLoadPage(pageLocator);
        initPage();
    }

    protected abstract void initPage();

    protected void clickElement(WebElement element){
        Driver.waitClickable(element);
        element.click();
    }

    protected void setValue(WebElement element, String value){
        element.sendKeys(value);
    }

    protected String getText(WebElement element){
        return element.getText();
    }

    protected void navigateBack(){
        getDriver().navigate().back();
    }

    protected void waitLoadPage(String pageLocator){
        Driver.waitLocated(pageLocator);
    }

}
