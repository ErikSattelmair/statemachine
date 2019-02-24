package de.erik.coding.challenge.statemachine.service.impl;

import de.erik.coding.challenge.statemachine.service.InputConverterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InputConverterServiceImpl implements InputConverterService {

    @Override
    public List<String> extractAndConvertInputStates(final String[] input) {
        if(isInputTooShort(input)) {
            throw new IllegalArgumentException("Input must at least contain one state!");
        }

        return extractAndConvertInput(input[0]);
    }

    @Override
    public List<String> extractAndConvertInputDrugs(final String[] input) {
        if(isInputTooShort(input)) {
            throw new IllegalArgumentException("Input must at least contain one state!");
        }

        if(input.length < 2) {
            return Collections.emptyList();
        }

        return extractAndConvertInput(input[1]);
    }

    private List<String> extractAndConvertInput(final String input) {
        return Arrays.stream(input.split(","))
                .filter(StringUtils::isNotBlank).map(StringUtils::strip).collect(Collectors.toList());
    }

    private boolean isInputTooShort(final String[] input) {
        return input.length < 1;
    }
}
