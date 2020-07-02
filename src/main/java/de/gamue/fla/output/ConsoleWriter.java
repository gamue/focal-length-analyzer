package de.gamue.fla.output;

import de.gamue.fla.FocalLengthResult;
import java.util.Collection;
import java.util.Map;

public class ConsoleWriter implements OutputWriter {

    @Override
    public void write(Collection<FocalLengthResult> results) {
        boolean printCameraName = results.size() > 1;
        results.forEach(result -> {
            if (printCameraName) {
                printCameraName(result.getCameraName());
            }
            Map<Float, Integer> focalLengthToAmount = result.getFocalLengthToAmount();
            for (Map.Entry<Float, Integer> lengthToAmount : focalLengthToAmount.entrySet()) {
                System.out.println(lengthToAmount.getKey() + "mm - " + lengthToAmount.getValue());
            }
        });
    }

    private void printCameraName(String cameraName) {
        System.out.println("###");
        System.out.println(cameraName);
        System.out.println("###");
    }
}
