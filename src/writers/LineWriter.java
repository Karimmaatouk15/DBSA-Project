package writers;

import interfaces.OutputStreamInterface;

import java.io.*;

public class LineWriter implements OutputStreamInterface {
    private RandomAccessFile writer = null;
    private BufferedWriter bufferedWriter = null;

    @Override
    public void create(String filePath) {
        try {
            writer = new RandomAccessFile(new File(filePath), "rw");
            bufferedWriter = new BufferedWriter(new FileWriter(writer.getFD()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Object ln) {
        try {
            bufferedWriter.write((String) ln);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeLine(String line) {
        write(line);
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
