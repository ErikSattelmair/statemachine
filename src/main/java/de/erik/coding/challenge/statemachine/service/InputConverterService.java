package de.erik.coding.challenge.statemachine.service;

import java.util.List;

public interface InputConverterService {

    List<String> extractAndConvertInputStates(final String[] input);

    List<String> extractAndConvertInputDrugs(final String[] input);
}