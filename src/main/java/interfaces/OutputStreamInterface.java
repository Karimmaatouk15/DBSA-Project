package interfaces;

import java.io.IOException;

public interface OutputStreamInterface {
    byte LINE_FEED_BYTE=10 ;
    int EOF_INT = -1;
    void create(String filePath);

    void write(Object ln);

    void writeLine(String line);

    void close();
}
