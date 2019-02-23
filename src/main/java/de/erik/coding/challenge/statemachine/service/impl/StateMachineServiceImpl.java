package de.erik.coding.challenge.statemachine.service.impl;

import de.erik.coding.challenge.statemachine.domain.StateTransition;
import de.erik.coding.challenge.statemachine.service.StateMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StateMachineServiceImpl implements StateMachineService {

    @Autowired
    private List<StateTransition> stateTransitions;

    @Override
    public String processState(final String inputState, final List<String> inputDrugs) {
        final Optional<StateTransition> relevantStateTransition = this.stateTransitions.stream()
                .filter(stateTransition -> inputState.equals(stateTransition.getInitialState())
                        && isDrugsRelevantForStateChange(stateTransition, inputDrugs))
                .findFirst();

        if(relevantStateTransition.isPresent()) {
            return relevantStateTransition.get().getEndState();
        }

        throw new IllegalArgumentException("End state can't be reached with given initial state and medication!");
    }

    private boolean isDrugsRelevantForStateChange(final StateTransition stateTransition, final List<String> inputDrugs) {
        final List<String> drugsToBeCheckedAgainst = stateTransition.getDrugs();
        return drugsToBeCheckedAgainst.size() == inputDrugs.size() && drugsToBeCheckedAgainst.containsAll(inputDrugs);
    }
}
