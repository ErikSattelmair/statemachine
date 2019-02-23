package de.erik.coding.challenge.statemachine.service;

import de.erik.coding.challenge.statemachine.domain.StateTransition;
import de.erik.coding.challenge.statemachine.service.impl.InputValidatorServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InputValidatorServiceTest.Configuration.class)
public class InputValidatorServiceTest {

    @Autowired
    private InputValidatorService inputValidatorService;

    @Test(expected = IllegalArgumentException.class)
    public void isInputStatesValid_should_throw_IllegalArgumentException_for_given_input() {
        this.inputValidatorService.isInputStatesValid(Collections.emptyList());
    }

    @Test
    public void isInputStatesValid_should_return_false_for_given_input() {
        assertFalse(this.inputValidatorService.isInputStatesValid(Lists.newArrayList("X")));
        assertFalse(this.inputValidatorService.isInputStatesValid(Lists.newArrayList("B")));
        assertFalse(this.inputValidatorService.isInputStatesValid(Lists.newArrayList("D")));
        assertFalse(this.inputValidatorService.isInputStatesValid(Lists.newArrayList("C")));
    }

    @Test
    public void isInputStatesValid_should_return_true_for_given_input() {
        assertTrue(this.inputValidatorService.isInputStatesValid(Lists.newArrayList("A")));
    }

    @Test
    public void isInputDrugsValid_should_return_true_for_empty_input() {
        assertTrue(this.inputValidatorService.isInputDrugsValid(Collections.emptyList()));
    }

    @Test
    public void isInputDrugsValid_should_return_false_for_given_input() {
        assertFalse(this.inputValidatorService.isInputDrugsValid(Lists.newArrayList("E")));
        assertFalse(this.inputValidatorService.isInputDrugsValid(Lists.newArrayList("A")));
        assertFalse(this.inputValidatorService.isInputDrugsValid(Lists.newArrayList("B")));
        assertFalse(this.inputValidatorService.isInputDrugsValid(Lists.newArrayList("C,D")));
    }

    @Test
    public void isInputDrugsValid_should_return_true_for_given_input() {
        assertTrue(this.inputValidatorService.isInputDrugsValid(Lists.newArrayList("C")));
        assertTrue(this.inputValidatorService.isInputDrugsValid(Lists.newArrayList("D")));
    }

    public static class Configuration {

        @Bean
        public List<StateTransition> stateTransitions() {
            final StateTransition stateTransition = new StateTransition();
            stateTransition.setInitialState("A");
            stateTransition.setEndState("B");
            stateTransition.setDrugs(Lists.newArrayList("C", "D"));

            return Lists.newArrayList(stateTransition);
        }

        @Bean
        public InputValidatorService inputValidatorService() {
            return new InputValidatorServiceImpl();
        }

    }
}
