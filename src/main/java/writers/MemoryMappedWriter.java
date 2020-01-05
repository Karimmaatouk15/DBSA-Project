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
    private long charsWritten = 0;

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


    @Override
    public void write(Object ln) {  //writes byte
        try {
            if (mappedBuffer.capacity() == mappedBuffer.position()) {
                mappedBuffer = channel.map(FileChannel.MapMode.READ_WRITE, bufferOffset * bufferSize, bufferSize);
                bufferOffset += 1;
            }
            mappedBuffer.put((Byte) ln);
            charsWritten++;
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

    private static void unmap(FileChannel fc, MappedByteBuffer bb) throws Exception {
        Class<?> fcClass = fc.getClass();
        java.lang.reflect.Method unmapMethod = fcClass.getDeclaredMethod("unmap", MappedByteBuffer.class);
        unmapMethod.setAccessible(true);
        unmapMethod.invoke(null, bb);
    }

    @Override
    public void close() {
        try {
            mappedBuffer.force();  //Force writing
            // Clear the buffer
            mappedBuffer.clear();
            unmap(channel, mappedBuffer);
            channel.truncate(charsWritten * Integer.BYTES);
            mappedBuffer = null;
            channel.close();
            writer.close();
            writer = null;
            mappedBuffer = null;
            channel = null;
            bufferOffset = 0;
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
