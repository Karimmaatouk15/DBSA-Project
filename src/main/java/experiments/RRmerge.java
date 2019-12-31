package experiments;

import interfaces.InputStreamInterface;
import interfaces.OutputStreamInterface;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RRmerge {
//    public static void main(String[] args) {
//        String f = "keyword.csv";
//        String path = "C:/Users/karim/Desktop/imdb/";
//        // Vary B
//        int B = 2097152;
//        ArrayList<InputStreamInterface> inputStreams = new ArrayList<>();
//        ArrayList<OutputStreamInterface> outputStreams = new ArrayList<>();
//        ArrayList<String> fileNames = new ArrayList<>();
//        inputStreams.add(new LineReader());
//        inputStreams.add(new MemoryMappedReader(B));
//        outputStreams.add(new CharacterWriter());
//        outputStreams.add(new LineWriter());
//        outputStreams.add(new SizedBufferWriter(B));
//        outputStreams.add(new MemoryMappedWriter(B));
//        fileNames.add("aka_name.csv");
//        fileNames.add("aka_title.csv");
//        fileNames.add("info_type.csv");
//        for (int k = 0; k < fileNames.size(); k++) {
//            fileNames.set(k, path + fileNames.get(k));
//        }
//
//
//        ReadingXWriting(fileNames, StreamType.LINE, StreamType.LINE, B);
//        ReadingXWriting(fileNames, experiments.StreamType.LINE, experiments.StreamType.SIZED_BUFFER, B);
//        ReadingXWriting(fileNames, StreamType.LINE, StreamType.MEMORY_MAPPED, B);
//        ReadingXWriting(fileNames, experiments.StreamType.MEMORY_MAPPED, experiments.StreamType.CHARACTER, B);
//        ReadingXWriting(fileNames, StreamType.MEMORY_MAPPED, StreamType.LINE, B);
//        ReadingXWriting(fileNames, experiments.StreamType.MEMORY_MAPPED, experiments.StreamType.SIZED_BUFFER, B);
//        ReadingXWriting(fileNames, StreamType.MEMORY_MAPPED, StreamType.MEMORY_MAPPED, B);
//    }

    public static void ReadingXWriting(List<String> filePathes, StreamType readType, StreamType writeType, int B) {
        ArrayList<InputStreamInterface> readers = new ArrayList<>();
        OutputStreamInterface writer = ReadersWritersFactory.getNewWriterInstance(writeType, B);

        writer.create("C:/Users/karim/Desktop/outputMerge.csv");
        for (String filePath : filePathes) {
            InputStreamInterface reader = ReadersWritersFactory.getNewReaderInstance(readType, B);
            reader.open(filePath);
            readers.add(reader);
        }
//        long start = System.currentTimeMillis();
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
//        long end = System.currentTimeMillis();
//        System.out.println("Reader " + (readType.ordinal() + 1) + "/Writer " + (writeType.ordinal() + 1) + " :" + (end - start));
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
