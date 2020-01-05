package experiments;

import interfaces.InputStreamInterface;
import interfaces.OutputStreamInterface;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RRmerge {

    public static void ReadingXWriting(List<String> filePathes, StreamType readType, StreamType writeType, int B) {
        ArrayList<InputStreamInterface> readers = new ArrayList<>();
        OutputStreamInterface writer = ReadersWritersFactory.getNewWriterInstance(writeType, B);

        writer.create("C:/Users/karim/Desktop/outputMerge.csv");
        for (String filePath : filePathes) {
            InputStreamInterface reader = ReadersWritersFactory.getNewReaderInstance(readType, B);
            reader.open(filePath);
            readers.add(reader);
        }
        while (!allFilesEndOfStream(readers)) {
            for (int i = 0; i < readers.size(); i++) {
                if (!readers.get(i).endOfStream()) {
                    writer.writeLine(readers.get(i).readLine());
                }
            }
        }

        for (int i = 0; i < readers.size(); i++) {
            readers.get(i).close();
            readers.remove(i);
        }
        writer.close();
    }


    public static boolean allFilesEndOfStream(ArrayList<InputStreamInterface> inputStreams) {
        boolean endOfStream = true;
        for (int i = 0; i < inputStreams.size(); i++) {
            endOfStream = endOfStream & inputStreams.get(i).endOfStream();
        }
        return endOfStream;
    }

    @Benchmark
    @Warmup(iterations = 1)
    @Fork(value = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void lineReadCharacterWrite(ExperimentsConfig.FilesList fileNames) {
        ReadingXWriting(fileNamesStringToArrayListFullPath(fileNames.filesListString), experiments.StreamType.LINE, experiments.StreamType.CHARACTER, 0);
    }

    @Benchmark
    @Warmup(iterations = 1)
    @Fork(value = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void lineReadLineWrite(ExperimentsConfig.FilesList fileNames) {
        ReadingXWriting(fileNamesStringToArrayListFullPath(fileNames.filesListString), experiments.StreamType.LINE, experiments.StreamType.LINE, 0);
    }

    @Benchmark
    @Warmup(iterations = 1)
    @Fork(value = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void lineReadSizedBufferWrite(ExperimentsConfig.FilesList fileNames, ExperimentsConfig.BlockSize b) {
        ReadingXWriting(fileNamesStringToArrayListFullPath(fileNames.filesListString), experiments.StreamType.LINE, StreamType.SIZED_BUFFER, b.size);
    }

    @Benchmark
    @Warmup(iterations = 1)
    @Fork(value = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void lineReadMemoryMappedWrite(ExperimentsConfig.FilesList fileNames, ExperimentsConfig.BlockSize b) {
        ReadingXWriting(fileNamesStringToArrayListFullPath(fileNames.filesListString), experiments.StreamType.LINE, StreamType.MEMORY_MAPPED, b.size);
    }

    @Benchmark
    @Warmup(iterations = 1)
    @Fork(value = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void memoryMappedReadCharacterWrite(ExperimentsConfig.FilesList fileNames, ExperimentsConfig.BlockSize b) {
        ReadingXWriting(fileNamesStringToArrayListFullPath(fileNames.filesListString), experiments.StreamType.MEMORY_MAPPED, experiments.StreamType.CHARACTER, b.size);
    }

    @Benchmark
    @Warmup(iterations = 1)
    @Fork(value = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void memoryMappedReadLineWrite(ExperimentsConfig.FilesList fileNames, ExperimentsConfig.BlockSize b) {
        ReadingXWriting(fileNamesStringToArrayListFullPath(fileNames.filesListString), experiments.StreamType.MEMORY_MAPPED, experiments.StreamType.LINE, b.size);
    }

    @Benchmark
    @Warmup(iterations = 1)
    @Fork(value = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void memoryMappedReadSizedBufferWrite(ExperimentsConfig.FilesList fileNames, ExperimentsConfig.BlockSize b) {
        ReadingXWriting(fileNamesStringToArrayListFullPath(fileNames.filesListString), experiments.StreamType.MEMORY_MAPPED, StreamType.SIZED_BUFFER, b.size);
    }

    @Benchmark
    @Warmup(iterations = 1)
    @Fork(value = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void memoryMappedReadMemoryMappedWrite(ExperimentsConfig.FilesList fileNames, ExperimentsConfig.BlockSize b) {
        ReadingXWriting(fileNamesStringToArrayListFullPath(fileNames.filesListString), experiments.StreamType.MEMORY_MAPPED, experiments.StreamType.MEMORY_MAPPED, b.size);
    }

    private List<String> fileNamesStringToArrayListFullPath(String str) {
        List<String> list = Arrays.asList(str.split("\\s*,\\s*"));
        for (int i = 0; i < list.size(); i++) {
            list.set(i, ExperimentsConfig.IMDB_DIR + list.get(i));
        }
        return list;
    }
}
