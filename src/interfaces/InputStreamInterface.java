package interfaces;

import java.io.IOException;

public interface InputStreamInterface {


    void open(String filePath) ;

    Object readln() ;

    void seek(long pos) ;

    boolean endOfStream()  ;

    void close();
}
