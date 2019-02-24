package de.erik.coding.challenge.statemachine.service.impl;

import de.erik.coding.challenge.statemachine.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationProcessorImpl implements ApplicationProcessor {

    @Autowired
    private InputConverterService inputConverterService;

    @Autowired
    private InputValidatorService inputValidatorService;

    @Autowired
    private StateMachineService stateMachineService;

    @Autowired
    private MetricService metricService;

    @Autowired
    private OutputService outputService;

    @Override
    public void processInput(final String[] inputArguments) {
        try{
            final List<String> inputStates = this.inputConverterService.extractAndConvertInputStates(inputArguments);
            final List<String> inputDrugs = this.inputConverterService.extractAndConvertInputDrugs(inputArguments);

            if(!this.inputValidatorService.isInputStatesValid(inputStates)) {
                throw new IllegalArgumentException("Given input states not valid!");
            }

            if(!this.inputValidatorService.isInputDrugsValid(inputDrugs)) {
                throw new IllegalArgumentException("Given input drugs not valid!");
            }

            final List<String> endStates = new ArrayList<>();
            for(final String state : inputStates) {
                endStates.add(this.stateMachineService.processState(state, inputDrugs));
            }

            this.outputService.writeResult(this.metricService.createMetricToken(endStates));
        } catch (IllegalArgumentException ex) {
            System.out.print(ex.getLocalizedMessage());
        } catch (Exception ex) {
            System.out.print("An unknown error occured!");
        }
    }
}
