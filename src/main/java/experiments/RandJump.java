package experiments;

import interfaces.InputStreamInterface;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class RandJump {
//    public static void main(String[] args) {
//        String f = "keyword.csv";
//        String path = "C:/Users/karim/Desktop/imdb/";
//        ArrayList<InputStreamInterface> inputStreams = new ArrayList();
//        inputStreams.add(new CharacterReader());
//        inputStreams.add(new LineReader());
//        // Vary B
//        int B = 2097152;
//        int j = 100;
//        inputStreams.add(new SizedBufferReader(B));
//        inputStreams.add(new MemoryMappedReader(B));
//        String filePath = path + f;
//        for (int i = 0; i < 4; i++) {
//            long start = System.currentTimeMillis();
//            System.out.println("Experiment " + (i + 1) + " :" + randJump(filePath, inputStreams.get(i), j));
//            long end = System.currentTimeMillis();
//            System.out.println("Duration " + (i + 1) + " :" + (end - start));
//        }
//    }


    public static int randJump(String filePath, InputStreamInterface reader, int j) {
        int sum = 0;
        reader.open(filePath);
        for (int i = 0; i < j; i++) {
            long p = ThreadLocalRandom.current().nextLong(0, reader.fileLength() + 1);
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
        randJump(ExperimentsConfig.IMDB_DIR + filePath.name, ReadersWritersFactory.getNewReaderInstance(StreamType.SIZED_BUFFER, 0), j.j);
    }

    @Benchmark
    @Warmup(iterations = 1)
    @Fork(value = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public static void lineReader(ExperimentsConfig.FileName filePath, ExperimentsConfig.J j) {
        randJump(ExperimentsConfig.IMDB_DIR + filePath.name, ReadersWritersFactory.getNewReaderInstance(StreamType.SIZED_BUFFER, 0), j.j);
    }

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
        randJump(ExperimentsConfig.IMDB_DIR + filePath.name, ReadersWritersFactory.getNewReaderInstance(StreamType.SIZED_BUFFER, b.size), j.j);
    }
}
