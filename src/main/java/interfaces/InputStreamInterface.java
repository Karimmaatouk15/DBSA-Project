package interfaces;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.io.IOException;

public interface InputStreamInterface {
    byte LINE_FEED_BYTE = 10;
    byte CR_BYTE = 13;
    int EOF_INT = -1;

    void open(String filePath);

    Object read();

    String readLine();

    void seek(long pos);

    boolean endOfStream();

    long fileLength();

    void close();
}
