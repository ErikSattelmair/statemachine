package de.erik.coding.challenge.statemachine;

import de.erik.coding.challenge.statemachine.configuration.ApplicationConfiguration;
import de.erik.coding.challenge.statemachine.service.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Application {

    public static void main(String[] args) {
        final AbstractApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        final ApplicationProcessor applicationProcessor = applicationContext.getBean(ApplicationProcessor.class);

        applicationProcessor.processInput(args);
        applicationContext.close();
    }

}
