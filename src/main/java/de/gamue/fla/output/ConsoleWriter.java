package de.gamue.fla.output;

import de.gamue.fla.FocalLengthResult;
import java.util.Map;

public class ConsoleWriter implements OutputWriter {

    @Override
    public void write(FocalLengthResult result) {
        Map<Float, Integer> focalLengthToAmount = result.getFocalLengthToAmount();
        for (Map.Entry<Float, Integer> lengthToAmount : focalLengthToAmount.entrySet()) {
            System.out.println(lengthToAmount.getKey() + "mm - " + lengthToAmount.getValue());
        }
    }
}
