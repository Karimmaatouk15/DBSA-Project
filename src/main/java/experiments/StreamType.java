package experiments;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public enum StreamType {
    CHARACTER,
    LINE,
    SIZED_BUFFER,
    MEMORY_MAPPED
}
