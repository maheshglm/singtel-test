package com.singtel.test;

import com.singtel.test.utils.ReportUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Bootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bootstrap.class);

    private static ConfigurableApplicationContext context;
    private static Class configClass;
    private static boolean initialized = false;

    private Bootstrap() {
    }

    public static void configureContainer() {
        if (context == null) {
            context = new AnnotationConfigApplicationContext(configClass);
        }
    }

    public static void init(Class config) {
        if (!initialized) {
            configClass = config;
            configureContainer();
            initialized = true;
            LOGGER.info("bootstrap: initialized");
        }
    }

    public static void done() {
        if (context != null) {
            try {
                context.getBean(ReportUtils.class).generateReports("testout/reports");
                context.close();
                LOGGER.info("bootstrap:done");
            } finally {
                context = null;
            }
        }
    }

    public static ConfigurableApplicationContext getContext() {
        init(configClass);
        return context;
    }

    public static synchronized Object getBean(Class class1) {
        init(configClass);
        return context.getBean(class1);
    }

    public static synchronized Object getBean(String name) {
        init(configClass);
        return context.getBean(name);
    }


}
