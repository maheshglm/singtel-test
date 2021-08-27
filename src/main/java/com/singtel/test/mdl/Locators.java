package com.singtel.test.mdl;

public enum Locators {

    NAME("name"),
    XPATH("xpath"),
    ID("id"),
    CSS("css"),
    CLASSNAME("className"),
    LINKTEXT("linkText"),
    ACCESS_ID("accessibilityId");

    private String locator;

    public String getLocator() {
        return locator;
    }

    Locators(String locator){
        this.locator = locator;
    }
}
