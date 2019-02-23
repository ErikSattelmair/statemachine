package de.erik.coding.challenge.statemachine;

import de.erik.coding.challenge.statemachine.configuration.ApplicationConfiguration;
import de.erik.coding.challenge.statemachine.service.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        final AbstractApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

        processInput(args, applicationContext);

        applicationContext.close();
    }

    private static void processInput(final String[] args, final AbstractApplicationContext applicationContext) {
        try{
            final InputConverterService inputConverterService = applicationContext.getBean(InputConverterService.class);
            final InputValidatorService inputValidatorService = applicationContext.getBean(InputValidatorService.class);

            final List<String> inputStates = inputConverterService.extractAndConvertInputStates(args);
            final List<String> inputDrugs = inputConverterService.extractAndConvertInputDrugs(args);

            if(!inputValidatorService.isInputStatesValid(inputStates)) {
                throw new IllegalArgumentException("Given input states not valid!");
            }

            if(!inputValidatorService.isInputDrugsValid(inputDrugs)) {
                throw new IllegalArgumentException("Given drugs not valid!");
            }

            final StateMachineService stateMachineService = applicationContext.getBean(StateMachineService.class);
            final List<String> endStates = new ArrayList<>();
            for(final String state : inputStates) {
                endStates.add(stateMachineService.processState(state, inputDrugs));
            }

            final MetricService metricService = applicationContext.getBean(MetricService.class);
            final OutputService outputService = applicationContext.getBean(OutputService.class);

            outputService.writeResult(metricService.createMetricToken(endStates));
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

}
