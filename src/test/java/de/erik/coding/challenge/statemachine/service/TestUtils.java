package de.erik.coding.challenge.statemachine.service;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class TestUtils {

    public static List<String> readProperty(final String propertyName) throws IOException {
        final ClassPathResource classPathResource =
                new ClassPathResource("de/erik/coding/challenge/statemachine/configuration/properties/statemachine.properties");
        final Properties properties = new Properties();
        properties.load(classPathResource.getInputStream());
        return Arrays.asList(properties.getProperty(propertyName).split(","));
    }

}
