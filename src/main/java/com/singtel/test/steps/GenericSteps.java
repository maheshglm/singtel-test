package com.singtel.test.steps;

import com.singtel.test.utils.ThreadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenericSteps {

    @Autowired
    private ThreadUtils threadUtils;

    public void pauseExecution(long timeInSeconds) {
        threadUtils.sleepSeconds((int)timeInSeconds);
    }
}
