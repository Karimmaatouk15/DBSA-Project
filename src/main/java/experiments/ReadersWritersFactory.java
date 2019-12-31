package experiments;

import interfaces.InputStreamInterface;
import interfaces.OutputStreamInterface;
import readers.*;
import writers.*;


public class ReadersWritersFactory {
    private static ReadersWritersFactory singleton = null;

    private ReadersWritersFactory() {
    }

    public static ReadersWritersFactory getSingleton() {
        if (singleton == null)
            return new ReadersWritersFactory();

        return singleton;
    }

    public static InputStreamInterface getNewReaderInstance(StreamType type, int B) {
        if (type == StreamType.CHARACTER) {
            return new CharacterReader();
        } else if (type == StreamType.LINE) {
            return new LineReader();
        } else if (type == StreamType.SIZED_BUFFER) {
            return new SizedBufferReader(B);
        } else {
            return new MemoryMappedReader(B);
        }
    }

    public static OutputStreamInterface getNewWriterInstance(StreamType type, int B) {
        if (type == StreamType.CHARACTER) {
            return new CharacterWriter();
        } else if (type == StreamType.LINE) {
            return new LineWriter();
        } else if (type == StreamType.SIZED_BUFFER) {
            return new SizedBufferWriter(B);
        } else {
            return new MemoryMappedWriter(B);
        }
    }
}
