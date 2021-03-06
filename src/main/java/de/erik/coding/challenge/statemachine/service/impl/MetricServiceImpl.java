package de.erik.coding.challenge.statemachine.service.impl;

import de.erik.coding.challenge.statemachine.service.MetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@Service
public class MetricServiceImpl implements MetricService {

    @Autowired
    private List<String> validStates;

    @Override
    public String createMetricToken(final List<String> endStates) {
        final Map<String, Long> defaultEndStatesWithDefaultMetrics = getAllPossibleEndStatesWithDefaultMetrics();
        defaultEndStatesWithDefaultMetrics.putAll(getMetricsForEndstates(endStates));
        return createMetricsToken(defaultEndStatesWithDefaultMetrics);
    }

    private Map<String, Long> getAllPossibleEndStatesWithDefaultMetrics() {
        return this.validStates.stream().collect(Collectors.toMap(identity(), e -> Long.valueOf(0),
                (v1,v2) ->{ throw new RuntimeException(String.format("Duplicate key for values %s and %s", v1, v2));},
                LinkedHashMap::new));
        // LinkedHashMap is used just to keep the order! Could be dropped completely when order is not important!
    }

    private Map<String, Long> getMetricsForEndstates(final List<String> endStates) {
        return endStates.stream().collect(Collectors.groupingBy(identity(), Collectors.counting()));
    }

    private String createMetricsToken(final Map<String, Long> metrics) {
        final StringBuilder metricsToken = new StringBuilder();

        for(Map.Entry<String,Long> entry : metrics.entrySet()) {
            metricsToken.append(",").append(entry.getKey()).append(":").append(entry.getValue());
        }

        return metricsToken.substring(1);
    }

}
