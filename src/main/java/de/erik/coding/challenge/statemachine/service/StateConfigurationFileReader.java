package de.erik.coding.challenge.statemachine.service;

import de.erik.coding.challenge.statemachine.domain.StateTransition;

import java.util.List;

public interface StateConfigurationFileReader {

    List<StateTransition> readStateTransitions();

}
