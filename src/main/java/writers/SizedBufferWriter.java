package writers;

import interfaces.OutputStreamInterface;

import java.io.*;

public class SizedBufferWriter implements OutputStreamInterface {
    private RandomAccessFile writer = null;
    private BufferedWriter bufferedWriter = null;
    private int bufferSize;

    public SizedBufferWriter(int b) {
        bufferSize = b;
    }

    @Override
    public void create(String filePath) {
        try {
            writer = new RandomAccessFile(new File(filePath), "rw");
            bufferedWriter = new BufferedWriter(new FileWriter(writer.getFD()), bufferSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Object ln) { // byte writes
        try {
            bufferedWriter.write((int) ln);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeLine(String line) {
        for (char ch : line.toCharArray()) {
            write((int) (ch));
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
