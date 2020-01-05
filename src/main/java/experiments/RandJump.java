package experiments;

import interfaces.InputStreamInterface;
import org.openjdk.jmh.annotations.*;
import readers.CharacterReader;
import readers.LineReader;
import readers.MemoryMappedReader;
import readers.SizedBufferReader;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class RandJump {



    public static int randJump(String filePath, InputStreamInterface reader, int j) {
        int sum = 0;
        reader.open(filePath);
        for (int i = 0; i < j; i++) {
            long p = ThreadLocalRandom.current().nextLong(0, reader.fileLength());
            reader.seek(p);
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
    public static void characterReader(ExperimentsConfig.FileName filePath, ExperimentsConfig.J j) {
        randJump(ExperimentsConfig.IMDB_DIR + filePath.name, ReadersWritersFactory.getNewReaderInstance(StreamType.CHARACTER, 0), j.j);
    }

    @Benchmark
    @Warmup(iterations = 1)
    @Fork(value = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public static void lineReader(ExperimentsConfig.FileName filePath, ExperimentsConfig.J j) {
        randJump(ExperimentsConfig.IMDB_DIR + filePath.name, ReadersWritersFactory.getNewReaderInstance(StreamType.LINE, 0), j.j);
    }
//
    @Benchmark
    @Warmup(iterations = 1)
    @Fork(value = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public static void sizedBufferReader(ExperimentsConfig.FileName filePath, ExperimentsConfig.BlockSize b, ExperimentsConfig.J j) {
        randJump(ExperimentsConfig.IMDB_DIR + filePath.name, ReadersWritersFactory.getNewReaderInstance(StreamType.SIZED_BUFFER, b.size), j.j);
    }

    @Benchmark
    @Warmup(iterations = 1)
    @Fork(value = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public static void memoryMappedReader(ExperimentsConfig.FileName filePath, ExperimentsConfig.BlockSize b, ExperimentsConfig.J j) {
        randJump(ExperimentsConfig.IMDB_DIR + filePath.name, ReadersWritersFactory.getNewReaderInstance(StreamType.MEMORY_MAPPED, b.size), j.j);
    }
}
