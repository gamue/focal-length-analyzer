package de.gamue.fla;

import java.util.Map;
import java.util.TreeMap;
import lombok.Data;

@Data
public class FocalLengthResult {

    private Map<Float, Integer> focalLengthToAmount = new TreeMap<>();

    private String cameraName;

    public FocalLengthResult(String cameraName) {
        this.cameraName = cameraName;
    }

    public int getAmount(float focalLength) {
        return focalLengthToAmount.getOrDefault(focalLength, 0);
    }

    public void increaseAmount(float focalLength) {
        int amount = getAmount(focalLength);
        amount++;
        focalLengthToAmount.put(focalLength, amount);
    }
}
