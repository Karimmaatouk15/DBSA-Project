package readers;

import interfaces.InputStreamInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

public class LineReader implements InputStreamInterface {
    private RandomAccessFile reader = null;
    private BufferedReader bufferedReader = null;

    @Override
    public void open(String filePath) {
        try {
            reader = new RandomAccessFile(filePath, "r");
            bufferedReader = new BufferedReader(new FileReader(reader.getFD()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object read() {
        if (!endOfStream()) {
            try {
                return bufferedReader.readLine()+ (char)LINE_FEED_BYTE ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    @Override
    public String readLine() {
        return (String) read();
    }

    @Override
    public void seek(long pos) {
        try {
            reader.seek(pos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean endOfStream() {

        try {
            return !bufferedReader.ready();  // End of Stream is determined by !BufferReader.Ready()
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void close() {
        try {
            reader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public long fileLength() {
        try {
            return reader.length();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
