package de.erik.coding.challenge.statemachine.domain;

import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode
public class StateTransition {

    private String initialState;

    private List<String> drugs;

    private String endState;

}
