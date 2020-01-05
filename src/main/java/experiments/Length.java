package experiments;

import interfaces.InputStreamInterface;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

public class Length {

    public static int length(InputStreamInterface reader, String filePath) {
        int sum = 0;
        reader.open(filePath);
        while (!reader.endOfStream()) {
            sum += reader.readLine().length();
        }
        reader.close();
        return sum;
    }

    @Benchmark
    @Warmup(iterations = 1)
    @Fork(value = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public static void characterReader(ExperimentsConfig.FileName filePath) {
        length(ReadersWritersFactory.getNewReaderInstance(StreamType.CHARACTER, 0), ExperimentsConfig.IMDB_DIR + filePath.name);
    }

    @Benchmark
    @Warmup(iterations = 1)
    @Fork(value = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public static void lineReader(ExperimentsConfig.FileName filePath) {
        length(ReadersWritersFactory.getNewReaderInstance(StreamType.LINE, 0), ExperimentsConfig.IMDB_DIR + filePath.name);
    }

    @Benchmark
    @Warmup(iterations = 1)
    @Fork(value = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public static void sizedBufferReader(ExperimentsConfig.FileName filePath, ExperimentsConfig.BlockSize b) {
        length(ReadersWritersFactory.getNewReaderInstance(StreamType.SIZED_BUFFER, b.size), ExperimentsConfig.IMDB_DIR + filePath.name);
    }

    @Benchmark
    @Warmup(iterations = 1)
    @Fork(value = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public static void memoryMappedReader(ExperimentsConfig.FileName filePath, ExperimentsConfig.BlockSize b) {
        length(ReadersWritersFactory.getNewReaderInstance(StreamType.MEMORY_MAPPED, b.size), ExperimentsConfig.IMDB_DIR + filePath.name);
    }
}
