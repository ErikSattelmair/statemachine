package de.erik.coding.challenge.statemachine.service;

import de.erik.coding.challenge.statemachine.domain.StateTransition;
import de.erik.coding.challenge.statemachine.service.impl.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationProcessorTest.Configuration.class)
public class ApplicationProcessorTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Autowired
    private ApplicationProcessor applicationProcessor;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(this.outContent));
    }

    @Test
    public void processInput_should_print_error_message_regarding_empty_input_when_input_is_empty() {
        this.applicationProcessor.processInput(new String[0]);

        assertEquals("Input must at least contain one state!", this.outContent.toString());
    }

    @Test
    public void processInput_should_print_error_message_regarding_empty_states_input_when_input_states_empty() {
        this.applicationProcessor.processInput(new String[]{""});
        assertEquals("Given input states not valid!", this.outContent.toString());
    }

    @Test
    public void processInput_should_print_error_message_regarding_stripped_states_input_when_input_states_stripped() {
        this.applicationProcessor.processInput(new String[]{"  "});
        assertEquals("Given input states not valid!", this.outContent.toString());
    }

    @Test
    public void processInput_should_print_error_message_regarding_invalid_states_input_when_invalid_state_is_given() {
        this.applicationProcessor.processInput(new String[]{"Y"});
        assertEquals("Given input states not valid!", this.outContent.toString());
    }

    @Test
    public void processInput_should_print_error_message_regarding_invalid_stripped_states_input_when_invalid_stripped_state() {
        this.applicationProcessor.processInput(new String[]{" Z"});
        assertEquals("Given input states not valid!", this.outContent.toString());
    }

    @Test
    public void processInput_should_print_error_regarding_invalid_drugs_input_when_invalid_drugs_is_given() {
        this.applicationProcessor.processInput(new String[]{"H", "XC"});
        assertEquals("Given input drugs not valid!", this.outContent.toString());
    }

    @Test
    public void processInput_should_return_correct_message_for_given_input() {
        this.applicationProcessor.processInput(new String[]{"X,D,H", "SM"});
        assertEquals("F:0,H:2,D:1,T:0,X:0", this.outContent.toString());
    }

    @After
    public void restoreStreams() {
        System.setOut(this.originalOut);
    }

    public static class Configuration {

        @Bean
        public ApplicationProcessor applicationProcessor() {
            return new ApplicationProcessorImpl();
        }

        @Bean
        public InputConverterService inputConverterService() {
            return new InputConverterServiceImpl();
        }

        @Bean
        public InputValidatorService inputValidatorService() {
            return new InputValidatorServiceImpl();
        }

        @Bean
        public StateMachineService stateMachineService() {
            return new StateMachineServiceImpl();
        }

        @Bean
        public MetricService metricService() {
            return new MetricServiceImpl();
        }

        @Bean
        public OutputService outputService() {
            return new StdOutOutputServiceImpl();
        }

        @Bean
        public List<String> validStates() throws IOException {
            return TestUtils.readPropertyAsList("statemachine.valid.states");
        }

        @Bean
        public List<String> validDrugs() throws IOException {
            return TestUtils.readPropertyAsList("statemachine.valid.drugs");
        }

        @Bean
        public List<StateTransition> stateTransitions() throws IOException {
            return new StateConfigurationJSONFileReader().readStateTransitions(TestUtils.readProperty("statemachine.config.json"));
        }
    }
}
