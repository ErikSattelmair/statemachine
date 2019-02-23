package de.erik.coding.challenge.statemachine.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.erik.coding.challenge.statemachine.domain.StateTransition;
import de.erik.coding.challenge.statemachine.service.StateConfigurationFileReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class StateConfigurationJSONFileReader implements StateConfigurationFileReader {

    public List<StateTransition> readStateTransitions(final String stateTransitionsFilePath) {
        final ClassPathResource transitionsFile = new ClassPathResource(stateTransitionsFilePath);

        if(!transitionsFile.exists()) {
            throw new ExceptionInInitializerError("State Transition config file not found!");
        }

        final ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(transitionsFile.getInputStream(),
                    mapper.getTypeFactory().constructCollectionType(List.class, StateTransition.class));
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Error while reading State Transition config file!");
       }
    }

}