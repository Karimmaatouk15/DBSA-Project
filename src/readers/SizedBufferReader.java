package readers;

import interfaces.InputStreamInterface;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayDeque;
import java.util.Queue;

public class SizedBufferReader implements InputStreamInterface {
    private RandomAccessFile reader = null;
    private int queueLength;
    private Queue<Integer> buffer;

    public SizedBufferReader(int bufferSize) {
        this.queueLength = bufferSize / 4;
    }

    @Override
    public void open(String filePath) {
        try {
            reader = new RandomAccessFile(filePath, "r");
            buffer = new ArrayDeque(queueLength);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object readln() {
        if (buffer.isEmpty()) {
            readToBuffer();
        }

        return buffer.poll();
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
        return buffer.isEmpty() && fileEnd();
    }

    private void readToBuffer() {
        while (buffer.size() < queueLength && !fileEnd()) {
            try {
                buffer.add(reader.read());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean fileEnd() {
        long oldPosition;
        try {
            oldPosition = reader.getFilePointer();
            if (reader.read() == -1) {   // If EOF Character is read
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
