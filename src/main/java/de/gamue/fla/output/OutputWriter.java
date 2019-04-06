package de.gamue.fla.output;

import java.util.Map;

public interface OutputWriter {

    void write(Map<Float, Integer> focalLengthToAmount);
}
