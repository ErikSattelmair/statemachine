package de.erik.coding.challenge.statemachine.service.impl;

import de.erik.coding.challenge.statemachine.domain.StateTransition;
import de.erik.coding.challenge.statemachine.service.InputValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InputValidatorServiceImpl implements InputValidatorService {

    @Autowired
    private List<StateTransition> stateTransitions;

    @Override
    public boolean isInputStatesValid(final List<String> inputStates) {
        if(inputStates.isEmpty()) {
            throw new IllegalArgumentException("Input states must not be null or empty!");
        }

        final Set<String> validStates = getValidStates();

        return inputStates.stream().anyMatch(validStates::contains);
    }

    @Override
    public boolean isInputDrugsValid(final List<String> inputDrugs) {
        if(inputDrugs.isEmpty()) {
            return true;
        }

        final Set<String> validDrugs = getValidDrugs();

        return inputDrugs.stream().anyMatch(validDrugs::contains);
    }

    private Set<String> getValidStates() {
        return this.stateTransitions
                .stream()
                .map(StateTransition::getInitialState)
                .collect(Collectors.toSet());
    }

    private Set<String> getValidDrugs() {
        final Set<String> validDrugs = new HashSet<>();

        for(final StateTransition stateTransition : this.stateTransitions) {
            validDrugs.addAll(new HashSet<>(stateTransition.getDrugs()));
        }

        return validDrugs;
    }

}
