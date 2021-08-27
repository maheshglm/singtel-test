package com.singtel.test.utils;

import org.springframework.stereotype.Component;

import java.util.Formatter;
import java.util.Locale;

@Component
public class FormatterUtils {

    public String format(String formatString, Object... args) {
        final StringBuilder sb = new StringBuilder();
        try (Formatter formatter = new Formatter(sb, Locale.US)) {
            formatter.format(formatString, args);
            return sb.toString();
        }
    }
}

