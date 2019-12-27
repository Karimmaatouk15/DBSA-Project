package writers;

import interfaces.OutputStreamInterface;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MemoryMappedWriter implements OutputStreamInterface {
    private RandomAccessFile writer = null;
    private MappedByteBuffer mappedBuffer;
    private FileChannel channel;
    private int bufferSize;
    private long bufferOffset;

    public MemoryMappedWriter(int bufferSize) {
        this.bufferSize = bufferSize; // Buffer Size passed should be divisible by 4
    }

    @Override
    public void create(String filePath) {
        try {
            writer = new RandomAccessFile(new File(filePath), "rw"); //Read Mode
            channel = writer.getChannel();
            bufferOffset = 0;
            mappedBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, bufferSize);
            bufferOffset += 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void map() {

    }

    @Override
    public void write(Object ln) {  //writes byte
        try {
            if (mappedBuffer.capacity() == mappedBuffer.position()) {
                mappedBuffer = channel.map(FileChannel.MapMode.READ_WRITE, bufferOffset * bufferSize, bufferSize);
                bufferOffset += 1;
            }
            mappedBuffer.put((byte) ln);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeLine(String line) {
        for (char ch : line.toCharArray()) {
            write((byte) (ch));
        }
    }

    @Override
    public void close() {
        try {
            mappedBuffer.force();  //Force writing
            mappedBuffer.clear();
            channel.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
