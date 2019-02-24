package de.erik.coding.challenge.statemachine.service.impl;

import de.erik.coding.challenge.statemachine.service.InputValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InputValidatorServiceImpl implements InputValidatorService {

    @Autowired
    private List<String> validStates;

    @Autowired
    private List<String> validDrugs;

    @Override
    public boolean isInputStatesValid(final List<String> inputStates) {
        return !inputStates.isEmpty() && inputStates.stream().anyMatch(this.validStates::contains);
    }

    @Override
    public boolean isInputDrugsValid(final List<String> inputDrugs) {
        return inputDrugs.isEmpty() || inputDrugs.stream().anyMatch(this.validDrugs::contains);
    }

}
