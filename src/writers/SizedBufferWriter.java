package writers;

import interfaces.OutputStreamInterface;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayDeque;
import java.util.Queue;

public class SizedBufferWriter implements OutputStreamInterface {
    private RandomAccessFile writer = null;
    private int queueLength;
    private Queue<Integer> buffer;

    public SizedBufferWriter(int bufferSize) {
        this.queueLength = bufferSize / 4;
    }

    @Override
    public void create(String filePath) {
        try {
            writer = new RandomAccessFile(new File(filePath), "rw");
            buffer = new ArrayDeque(queueLength);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeln(Object ln) {
        if (buffer.size() == queueLength) {
            writeBuffer();
        }

        buffer.offer((int) ln);
    }

    private void writeBuffer() {//writes Int
        while (!buffer.isEmpty()) {
            try {
                writer.write(buffer.poll());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() {   // see if there's need to clear the buffer
        try {
            writeBuffer(); //remaining of buffer is written when writer is closed
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
