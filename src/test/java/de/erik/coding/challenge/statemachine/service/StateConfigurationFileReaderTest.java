package de.erik.coding.challenge.statemachine.service;

import de.erik.coding.challenge.statemachine.domain.StateTransition;
import de.erik.coding.challenge.statemachine.service.impl.StateConfigurationJSONFileReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StateConfigurationFileReaderTest.Configuration.class)
public class StateConfigurationFileReaderTest {

    private static final String STATE_MACHINE_CONFIG_PROPERTY = "statemachine.config.json";

    @Autowired
    private StateConfigurationFileReader stateConfigurationFileReader;

    @Test
    public void readStateTransitions_should_return_given_number_of_elements() throws IOException {
        final List<StateTransition> stateTransitions = this.stateConfigurationFileReader.readStateTransitions(TestUtils.readProperty(STATE_MACHINE_CONFIG_PROPERTY));
        assertEquals(12, stateTransitions.size());
    }

    @Test
    public void readStateTransitions_should_return_correct_first_element_values() throws IOException {
        final List<StateTransition> stateTransitions = this.stateConfigurationFileReader.readStateTransitions(TestUtils.readProperty(STATE_MACHINE_CONFIG_PROPERTY));
        final StateTransition stateTransition = stateTransitions .get(0);

        assertEquals("F", stateTransition.getInitialState());

        final List<String> drugs = stateTransition.getDrugs();
        assertEquals(1, drugs.size());
        assertEquals("As", drugs.get(0));

        assertEquals("H", stateTransition.getEndState());
    }

    @Test
    public void readStateTransitions_should_return_correct_last_element_values() throws IOException {
        final List<StateTransition> stateTransitions = this.stateConfigurationFileReader.readStateTransitions(TestUtils.readProperty(STATE_MACHINE_CONFIG_PROPERTY));
        final StateTransition stateTransition = stateTransitions .get(stateTransitions.size() - 1);

        assertEquals("X", stateTransition.getInitialState());

        final List<String> drugs = stateTransition.getDrugs();
        assertEquals(1, drugs.size());
        assertEquals("SM", drugs.get(0));

        assertEquals("H", stateTransition.getEndState());
    }

    public static class Configuration {

        @Bean
        public StateConfigurationFileReader stateConfigurationFileReader() {
            return new StateConfigurationJSONFileReader();
        }
    }
}
