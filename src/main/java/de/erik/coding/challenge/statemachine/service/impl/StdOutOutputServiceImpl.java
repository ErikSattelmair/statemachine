package de.erik.coding.challenge.statemachine.service.impl;

import de.erik.coding.challenge.statemachine.service.OutputService;
import org.springframework.stereotype.Service;

@Service
public class StdOutOutputServiceImpl implements OutputService {

    @Override
    public void writeResult(final String result) {
        System.out.println(result);
    }
}
