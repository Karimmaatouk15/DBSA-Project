package readers;

import interfaces.InputStreamInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

public class SizedBufferReader implements InputStreamInterface {
    private RandomAccessFile reader = null;
    private BufferedReader bufferedReader = null;
    private int blockSize;

    public SizedBufferReader(int blockSize) {
        this.blockSize = blockSize;
    }

    @Override
    public void open(String filePath) {
        try {
            reader = new RandomAccessFile(filePath, "r");
            bufferedReader = new BufferedReader(new FileReader(reader.getFD()), blockSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object read() {
        if (!endOfStream()) {
            try {
                return bufferedReader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    @Override
    public String readLine() {
        String line = "";
        int r;
        do {
            r = (Integer) read();
            line += (char) r;
        } while (r != LINE_FEED_BYTE);
        return line;
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
