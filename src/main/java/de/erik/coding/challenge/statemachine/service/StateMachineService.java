package de.erik.coding.challenge.statemachine.service;

import java.util.List;

public interface StateMachineService {

    String processState(final String state, final List<String> drugs);

}
