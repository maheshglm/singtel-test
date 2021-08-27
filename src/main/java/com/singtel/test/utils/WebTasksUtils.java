package com.singtel.test.utils;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;

@Component
public class WebTasksUtils {

    public void enterText(final WebElement element, final String text, final String followingKey) {
        element.clear();
        element.sendKeys(text);
        if (followingKey != null) {
            for (String key : followingKey.split(":")) {
                if ("ENTER".equals(key)) {
                    element.sendKeys(Keys.ENTER);
                } else if ("ESCAPE".equals(key)) {
                    element.sendKeys(Keys.ESCAPE);
                }
            }
        }
    }


}
