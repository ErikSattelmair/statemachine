package de.erik.coding.challenge.statemachine.configuration;

import de.erik.coding.challenge.statemachine.domain.StateTransition;
import de.erik.coding.challenge.statemachine.service.StateConfigurationFileReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "de.erik.coding.challenge.statemachine")
public class ApplicationConfiguration {

    private static final String PROPERTY_FILE_READ_ERROR = "Error while reading properties file";
    private static final String PROPERTY_NAME_VALID_STATES = "statemachine.valid.states";
    private static final String PROPERTY_NAME_VALID_DRUGS = "statemachine.valid.drugs";
    private static final String PROPERTY_NAME_CONFIG_FILE = "statemachine.config.json";

    @Value("de/erik/coding/challenge/statemachine/configuration/properties/statemachine.properties")
    private Resource resource;

    @Bean
    public List<StateTransition> stateTransitions(final StateConfigurationFileReader stateConfigurationFileReader) {
        return stateConfigurationFileReader.readStateTransitions(loadPropertiesFile().getProperty(PROPERTY_NAME_CONFIG_FILE));
    }

    @Bean
    public List<String> validStates() {
        return Arrays.asList(loadPropertiesFile().getProperty(PROPERTY_NAME_VALID_STATES).split(","));
    }

    @Bean
    public List<String> validDrugs() {
        return Arrays.asList(loadPropertiesFile().getProperty(PROPERTY_NAME_VALID_DRUGS).split(","));
    }

    private Properties loadPropertiesFile() {
        final Properties properties = new Properties();
        try {
            properties.load(this.resource.getInputStream());
            return properties;
        } catch (IOException e) {
            throw new ExceptionInInitializerError(PROPERTY_FILE_READ_ERROR);
        }
    }
}
