package de.erik.coding.challenge.statemachine.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.erik.coding.challenge.statemachine.domain.StateTransition;
import de.erik.coding.challenge.statemachine.service.impl.MetricServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MetricServiceTest.Configuration.class)
public class MetricServiceTest {

    @Autowired
    private MetricService metricService;

    @Test
    public void createMetricToken_should_return_metric_token_for_given_input() {
        assertEquals("D:2,F:2,X:2,H:1", this.metricService.createMetricToken(Lists.newArrayList("H", "F", "X", "X", "D", "F", "D")));
        assertEquals("D:0,F:0,X:0,H:0", this.metricService.createMetricToken(Lists.emptyList()));
    }

    public static class Configuration {

        @Bean
        public List<StateTransition> stateTransitions() throws IOException {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new ClassPathResource("de/erik/coding/challenge/statemachine/configuration/json/state_transition.json").getInputStream(),
                    mapper.getTypeFactory().constructCollectionType(List.class, StateTransition.class));
        }

        @Bean
        public MetricService metricService() {
            return new MetricServiceImpl();
        }

    }
}
