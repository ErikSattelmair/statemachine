package de.erik.coding.challenge.statemachine.configuration;

import de.erik.coding.challenge.statemachine.domain.StateTransition;
import de.erik.coding.challenge.statemachine.service.StateConfigurationFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ComponentScan(basePackages = "de.erik.coding.challenge.statemachine")
public class ApplicationConfiguration {

    @Autowired
    private StateConfigurationFileReader stateConfigurationFileReader;

    @Bean
    public List<StateTransition> stateTransitions() {
        return this.stateConfigurationFileReader.readStateTransitions();
    }

}
