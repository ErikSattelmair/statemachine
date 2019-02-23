package de.erik.coding.challenge.statemachine.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.erik.coding.challenge.statemachine.domain.StateTransition;
import de.erik.coding.challenge.statemachine.service.StateConfigurationFileReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class StateConfigurationJSONFileReader implements StateConfigurationFileReader {

    @Value("classpath:de/erik/coding/challenge/statemachine/configuration/json/state_transition.json")
    private Resource stateTransitionConfiguration;

    public List<StateTransition> readStateTransitions() {
        if(!this.stateTransitionConfiguration.exists()) {
            throw new ExceptionInInitializerError("State Transition config file not found!");
        }

        final ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(this.stateTransitionConfiguration.getInputStream(),
                    mapper.getTypeFactory().constructCollectionType(List.class, StateTransition.class));
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Error while reading State Transition config file!");
       }
    }

}