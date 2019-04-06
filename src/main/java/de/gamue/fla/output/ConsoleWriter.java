package de.gamue.fla.output;

import java.util.Map;

public class ConsoleWriter implements OutputWriter {

    @Override
    public void write(Map<Float, Integer> focalLengthToAmount) {
        for (Map.Entry<Float, Integer> lengthToAmount : focalLengthToAmount.entrySet()) {
            System.out.println(lengthToAmount.getKey() + "mm - " + lengthToAmount.getValue());
        }
    }
}
