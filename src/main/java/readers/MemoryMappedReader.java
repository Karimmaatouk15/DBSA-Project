package readers;

import interfaces.InputStreamInterface;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MemoryMappedReader implements InputStreamInterface {
    private RandomAccessFile reader = null;
    private MappedByteBuffer mappedBuffer;
    private FileChannel channel;
    private int bufferSize;
    private long bufferOffset;

    public MemoryMappedReader(int bufferSize) {
        this.bufferSize = bufferSize; // Buffer Size passed should be divisible by 4
    }

    @Override
    public void open(String filePath) {
        try {
            reader = new RandomAccessFile(filePath, "r"); //Read Mode
            channel = reader.getChannel();
            long chunkSize = Math.min(bufferSize, channel.size());
            mappedBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, chunkSize);
            bufferOffset = chunkSize;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object read() {
        if (!mappedBuffer.hasRemaining()) {
            mapFile();
        }
        if (!mappedBuffer.hasRemaining()) {
            return -1;
        }
        return mappedBuffer.get(); //returns byte
    }

    @Override
    public String readLine() {
        String line = "";
        int r;
        do {
            r = (Byte) read();
            line += (char) r;
        } while (r != LINE_FEED_BYTE);
        return line;
    }

    @Override
    public void seek(long pos) {
        mappedBuffer.clear();
        this.bufferOffset = pos;
        mapFile();
    }

    @Override
    public boolean endOfStream() {
        if (!mappedBuffer.hasRemaining()) {
            mapFile();
        }
        return !mappedBuffer.hasRemaining();
    }

    @Override
    public void close() {
        try {
            bufferOffset = 0;
            channel.close();
            mappedBuffer.clear();
            reader.close();
            mappedBuffer = null;
            reader = null;
            channel = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mapFile() {  // calls made to this function must make sure the buffer is empty before invoking
        try {
            if (bufferOffset < channel.size()) {
                long chunkSize = Math.min(bufferSize, channel.size() - bufferOffset);
                mappedBuffer = channel.map(FileChannel.MapMode.READ_ONLY, bufferOffset, chunkSize);
                bufferOffset = bufferOffset + chunkSize;
            }
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
