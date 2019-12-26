package interfaces;

import java.io.IOException;

public interface OutputStreamInterface {
    void create(String filePath);

    void writeln(Object ln);

    void close();
}
