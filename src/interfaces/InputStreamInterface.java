package interfaces;

import java.io.IOException;

public interface InputStreamInterface {
    byte LINE_FEED_BYTE=10 ;
    int EOF_INT = -1;


    void open(String filePath) ;

    Object read() ;

    String readLine();

    void seek(long pos) ;

    boolean endOfStream()  ;

    void close();
}
