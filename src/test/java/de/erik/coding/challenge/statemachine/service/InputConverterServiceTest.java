package de.erik.coding.challenge.statemachine.service;

import de.erik.coding.challenge.statemachine.service.impl.InputConverterServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InputConverterServiceTest.Configuration.class)
public class InputConverterServiceTest {

    @Autowired
    private InputConverterService inputConverterService;

    @Test(expected = IllegalArgumentException.class)
    public void extractAndConvertInputStates_should_throw_IllegalArgumentException_when_input_is_empty() {
        this.inputConverterService.extractAndConvertInputStates(new String[0]);
    }

    @Test
    public void extractAndConvertInputStates_should_return_converted_input_states_for_given_values() {
        final List<String> result = this.inputConverterService.extractAndConvertInputStates(new String[]{"A,b"});
        assertEquals(2, result.size());
        assertEquals("A", result.get(0));
        assertEquals("B", result.get(1));

        final List<String> result2 = this.inputConverterService.extractAndConvertInputStates(new String[]{"A,b", "dweq"});
        assertEquals(2, result2.size());
        assertEquals("A", result2.get(0));
        assertEquals("B", result2.get(1));
    }

    @Test
    public void extractAndConvertInputStates_should_return_converted_input_states_for_given_values_and_strip_last_value() {
        final List<String> result = this.inputConverterService.extractAndConvertInputStates(new String[]{"A,b, "});
        assertEquals(2, result.size());
        assertEquals("A", result.get(0));
        assertEquals("B", result.get(1));

        final List<String> result2 = this.inputConverterService.extractAndConvertInputStates(new String[]{" A,b,"});
        assertEquals(2, result2.size());
        assertEquals("A", result2.get(0));
        assertEquals("B", result2.get(1));
    }

    @Test
    public void extractAndConvertInputStates_should_return_converted_input_states_empty_list() {
        final List<String> result = this.inputConverterService.extractAndConvertInputStates(new String[]{""});
        assertTrue(result.isEmpty());

        final List<String> result2 = this.inputConverterService.extractAndConvertInputStates(new String[]{" "});
        assertTrue(result2.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void extractAndConvertInputDrugs_should_throw_IllegalArgumentException_when_input_is_empty() {
        this.inputConverterService.extractAndConvertInputDrugs(new String[0]);
    }

    @Test
    public void extractAndConvertInputDrugs_should_return_list_with_one_element_empty_string_when_input_drugs_is_missing() {
        final List<String> result = this.inputConverterService.extractAndConvertInputDrugs(new String[]{"a,sd,s"});
        assertTrue(result.isEmpty());
    }

    @Test
    public void extractAndConvertInputDrugs_should_return_list_with_one_element_empty_string_when_input_drugs_is_empty_string() {
        final List<String> result = this.inputConverterService.extractAndConvertInputDrugs(new String[]{"a,sd,s", ""});
        assertTrue(result.isEmpty());

        final List<String> result2 = this.inputConverterService.extractAndConvertInputDrugs(new String[]{"a,sd,s", " "});
        assertTrue(result2.isEmpty());
    }

    @Test
    public void extractAndConvertInputDrugs_should_return_converted_drugs_when_input_drugs_given() {
        final List<String> result = this.inputConverterService.extractAndConvertInputDrugs(new String[]{"a,sd,s", " i,E"});
        assertEquals(2, result.size());
        assertEquals("I", result.get(0));
        assertEquals("E", result.get(1));
    }

    public static class Configuration {

        @Bean
        public InputConverterService inputConverterService() {
            return new InputConverterServiceImpl();
        }

    }
}
