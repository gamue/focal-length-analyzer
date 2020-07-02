package de.gamue.fla.output;

import de.gamue.fla.FocalLengthResult;
import java.util.Collection;

public interface OutputWriter {

    void write(Collection<FocalLengthResult> results);
}
