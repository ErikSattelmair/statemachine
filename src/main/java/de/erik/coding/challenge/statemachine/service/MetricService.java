package de.erik.coding.challenge.statemachine.service;

import java.util.List;

public interface MetricService {

    String createMetricToken(final List<String> endStates);

}
