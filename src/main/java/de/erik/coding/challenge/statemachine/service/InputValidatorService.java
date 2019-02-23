package de.erik.coding.challenge.statemachine.service;

import java.util.List;

public interface InputValidatorService {

    boolean isInputStatesValid(final List<String> inputStates);

    boolean isInputDrugsValid(final List<String> inputDrugs);

}