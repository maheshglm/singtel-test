package com.singtel.test;

import cfg.SpringConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class BaseRunner {

    @BeforeClass
    public static void init() {
        Bootstrap.init(SpringConfig.class);
    }


    @AfterClass
    public static void done() {
        Bootstrap.done();
    }
}
