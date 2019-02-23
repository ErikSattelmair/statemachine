package de.erik.coding.challenge.statemachine.service;

import de.erik.coding.challenge.statemachine.service.impl.StdOutOutputServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = OutputServiceTest.Configuration.class)
public class OutputServiceTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Autowired
    private OutputService outputService;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(this.outContent));
    }

    @Test
    public void writeResult_should_write_given_input_to_system_out() {
        this.outputService.writeResult("Result");
        assertEquals("Result", this.outContent.toString());
    }

    @After
    public void restoreStreams() {
        System.setOut(this.originalOut);
    }

    public static class Configuration {

        @Bean
        public OutputService outputService() {
            return new StdOutOutputServiceImpl();
        }

    }
}
