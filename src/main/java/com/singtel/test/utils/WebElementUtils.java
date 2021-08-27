package com.singtel.test.utils;

import com.google.common.base.Strings;
import com.singtel.test.CustomException;
import com.singtel.test.mdl.CustomExceptionType;
import com.singtel.test.mdl.Locators;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WebElementUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebElementUtils.class);

    private Object[] getWebElementIdentifiers(final String propertyValue) {
        Object[] result = new Object[3];
        LOGGER.debug("getWebElementIdentifiers={}", propertyValue);
        if (Strings.isNullOrEmpty(propertyValue)) {
            throw new CustomException(CustomExceptionType.INCOMPLETE_PARAMS, "invalid propertyValue for WebElement Identification [{}]", propertyValue);
        }
        String[] splitString = propertyValue.split(":");
        if (splitString.length == 1) {
            throw new CustomException(CustomExceptionType.UNSATISFIED_IMPLICIT_ASSUMPTION, "missing locator (xpath:,id:,name: etc..) in [{}]", propertyValue);
        }
        String locator = splitString[0];
        String param1;
        String param2 = null;

        if (splitString.length == 2) {
            param1 = splitString[1];
        } else if (splitString.length == 3) {
            param1 = splitString[1];
            param2 = splitString[2];
        } else {
            throw new CustomException(CustomExceptionType.PROCESSING_FAILED, "additional colon found in property value [{}]", propertyValue);
        }
        result[0] = locator;
        result[1] = param1;
        result[2] = param2;
        return result;
    }

    //syntax of opOnWebElement = <locator>:<identifier>
    public By getByReference(final String inlineLocator) {
        Object[] webElementIdentifiers = getWebElementIdentifiers(inlineLocator);
        String locator = (String) webElementIdentifiers[0];
        String param1 = (String) webElementIdentifiers[1];
        return getByReference(locator, param1);
    }

    public By getByReference(final String locator, final String identifier) {
        switch (Locators.valueOf(locator.toUpperCase())) {
            case XPATH:
                return By.xpath(identifier);
            case ID:
                return By.id(identifier);
            case CLASSNAME:
                return By.className(identifier);
            case CSS:
                return By.cssSelector(identifier);
            case LINKTEXT:
                return By.linkText(identifier);
            case NAME:
                return By.name(identifier);
            default:
                LOGGER.error("Undefined Locator to construct By object");
                throw new CustomException(CustomExceptionType.UNDEFINED, "Undefined Locator to construct By object");
        }
    }
}
