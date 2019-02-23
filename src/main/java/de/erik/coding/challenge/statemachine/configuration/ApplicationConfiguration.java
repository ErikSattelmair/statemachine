package de.erik.coding.challenge.statemachine.configuration;

import de.erik.coding.challenge.statemachine.domain.StateTransition;
import de.erik.coding.challenge.statemachine.service.StateConfigurationFileReader;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private StateConfigurationFileReader stateConfigurationFileReader;

    @Value("de/erik/coding/challenge/statemachine/configuration/properties/statemachine.properties")
    private Resource resource;

    @Bean
    public List<StateTransition> stateTransitions() {
        final Properties properties = new Properties();
        try {
            properties.load(this.resource.getInputStream());
            return this.stateConfigurationFileReader.readStateTransitions(properties.getProperty("statemachine.config.json"));
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Error while reading properties file!");
        }
    }

    @Bean
    public List<String> validStates() {
        final Properties properties = new Properties();
        try {
            properties.load(this.resource.getInputStream());
            return Arrays.asList(properties.getProperty("statemachine.valid.states").split(","));
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Error while reading properties file!");
        }
    }

    @Bean
    public List<String> validDrugs() {
        final Properties properties = new Properties();
        try {
            properties.load(this.resource.getInputStream());
            return Arrays.asList(properties.getProperty("statemachine.valid.drugs").split(","));
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Error while reading properties file!");
        }
    }

}
