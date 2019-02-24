package de.erik.coding.challenge.statemachine.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.erik.coding.challenge.statemachine.domain.StateTransition;
import de.erik.coding.challenge.statemachine.service.impl.StateMachineServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StateMachineServiceTest.Configuration.class)
public class StateMachineServiceTest {

    @Autowired
    private StateMachineService stateMachineService;

    @Autowired
    private List<StateTransition> stateTransitions;

    @Test
    public void processState_should_return_endstate_for_given_initial_state_and_medication() {
        for(final StateTransition stateTransition : this.stateTransitions) {
            final String initialSate = stateTransition.getInitialState();
            final String endState = stateTransition.getEndState();
            final List<String> drugs = stateTransition.getDrugs();

            assertEquals(MessageFormat.format("initial state {0}, Event {1}, end state {2}", initialSate, String.join(",", drugs), endState),
                    endState, this.stateMachineService.processState(initialSate, drugs));
        }
    }

    @Test
    public void processState_should_return_initial_state_for_given_initial_state_and_medication() {
        assertEquals("X", this.stateMachineService.processState("X", Lists.newArrayList("AS")));
    }

    public static class Configuration {

        @Bean
        public List<StateTransition> stateTransitions() throws IOException {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new ClassPathResource("de/erik/coding/challenge/statemachine/configuration/json/state_transition.json").getInputStream(),
                    mapper.getTypeFactory().constructCollectionType(List.class, StateTransition.class));
        }

        @Bean
        public StateMachineService stateMachineService() {
            return new StateMachineServiceImpl();
        }

    }
}
