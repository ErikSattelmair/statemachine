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

import java.io.IOException;
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
        assertFalse(this.inputValidatorService.isInputStatesValid(Lists.newArrayList("B")));
        assertFalse(this.inputValidatorService.isInputStatesValid(Lists.newArrayList("Y")));
        assertFalse(this.inputValidatorService.isInputStatesValid(Lists.newArrayList("C")));
    }

    @Test
    public void isInputStatesValid_should_return_true_for_given_input() {
        assertTrue(this.inputValidatorService.isInputStatesValid(Lists.newArrayList("T")));
        assertTrue(this.inputValidatorService.isInputStatesValid(Lists.newArrayList("X")));
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
        assertTrue(this.inputValidatorService.isInputDrugsValid(Lists.newArrayList("As")));
        assertTrue(this.inputValidatorService.isInputDrugsValid(Lists.newArrayList("I")));
    }

    public static class Configuration {

        @Bean
        public List<String> validStates() throws IOException {
            return TestUtils.readProperty("statemachine.valid.states");
        }

        @Bean
        public List<String> validDrugs() throws IOException {
            return TestUtils.readProperty("statemachine.valid.drugs");
        }

        @Bean
        public InputValidatorService inputValidatorService() {
            return new InputValidatorServiceImpl();
        }

    }
}
