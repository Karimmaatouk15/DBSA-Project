package readers;

import interfaces.InputStreamInterface;

import java.io.*;


public class CharacterReader implements InputStreamInterface {
    private RandomAccessFile reader = null;


    @Override
    public void open(String filePath) {
        try {
            reader = new RandomAccessFile(filePath, "r"); //Read Mode
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object read() {
        if (!endOfStream()) {
            try {
                return reader.read();
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
            r = (int) read();
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
        long oldPosition;
        try {
            oldPosition = reader.getFilePointer();
            if (reader.read() == EOF_INT) {   // If EOF Character is read
                return true;
            } else {
                this.seek(oldPosition);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
