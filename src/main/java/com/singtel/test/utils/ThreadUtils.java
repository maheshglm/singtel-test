package com.singtel.test.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ThreadUtils {

    public static final Logger LOGGER = LoggerFactory.getLogger(ThreadUtils.class);

    public void sleepMillis(long millis) {
        LOGGER.debug("sleepmillis [{}]", millis);
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            //
        }
    }

    public void sleepSeconds(int seconds) {
        sleepMillis(seconds * 1000L);
    }

}
