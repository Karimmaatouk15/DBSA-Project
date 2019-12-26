package Writers;

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
    public void writeln(Object ln) {
        try {
            bufferedWriter.write((String) ln);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
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
