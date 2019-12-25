package interfaces;

import java.io.IOException;

public interface OutputStreamInterface {
    void create(String filePath)throws IOException;

    void writeln()throws  IOException;

    void close() throws  IOException;
}
