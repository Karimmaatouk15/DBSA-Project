package experiments;

import interfaces.InputStreamInterface;
import interfaces.OutputStreamInterface;
import org.openjdk.jmh.annotations.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class ExtSort {
    public static final String SORTED_FILES_DESTINATION = "C:/Users/karim/Desktop/SortedStreams/";


    public static void extSort(String filePathToRead, final int k, int m, int d, int B, StreamType readerType, StreamType writerType, String fileName) {
        ArrayList<String> buffer;
        Queue<String> sortedFilesQueue = new ArrayDeque<String>();
        buffer = new ArrayList<>();
        InputStreamInterface reader = ReadersWritersFactory.getNewReaderInstance(readerType, B);
        OutputStreamInterface writer = ReadersWritersFactory.getNewWriterInstance(writerType, B);
        reader.open(filePathToRead);

        //Create Comparator to sort on kth column
        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                List<String> a1 = Arrays.asList(s.split("\\s*,\\s*"));
                List<String> a2 = Arrays.asList(t1.split("\\s*,\\s*"));
                return (a1.get(k)).compareTo(a2.get(k));
            }
        };

        int i = 0;
        while (!reader.endOfStream()) {
            int byteSize = 0;
            // Fill the buffer with approx. M bytes at most
            while (byteSize < m && !reader.endOfStream()) {
                String readValue = reader.readLine();
                buffer.add(readValue);
                byteSize += readValue.length();
                int b = byteSize;
            }

            //Sort the buffer
            buffer.sort(comparator);
            //create new file i;
            String fileLoc = SORTED_FILES_DESTINATION + i + ".txt";
            writer.create(fileLoc);
            //write to file
            for (String line : buffer) {
                writer.writeLine(line);
            }

            // add file path to queue
            sortedFilesQueue.offer(fileLoc);
            //empty buffer
            buffer.clear();
            //increment
            i++;
            writer.close();
        }
        merge(sortedFilesQueue, d, B, k, readerType, writerType, fileName);
    }


    public static void merge(Queue<String> sortedFilesQueue, int d, int B, int k, StreamType readerType, StreamType writerType, String fileName) {
        OutputStreamInterface writer = ReadersWritersFactory.getNewWriterInstance(writerType, B);
        // Priority Queue Comparator
        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                List<String> a1 = Arrays.asList(s.split("\\s*,\\s*"));
                List<String> a2 = Arrays.asList(t1.split("\\s*,\\s*"));
                return (a1.get(k)).compareTo(a2.get(k));
            }
        };
        PriorityQueue<String> pQueue = new PriorityQueue<>(comparator);
        ArrayList<String> inputsReference = new ArrayList<>(); // inputs reference is to keep reference which element came from which list (Size is d at most)
        int batchCount = 0;
        ArrayList<InputStreamInterface> readers = new ArrayList<>();
        while (sortedFilesQueue.size() > d) {
            String writerLoc = SORTED_FILES_DESTINATION + "merge_" + batchCount + ".txt";
            writer.create(writerLoc);
            sortedFilesQueue.offer(writerLoc);
            for (int i = 0; i < d; i++) {  // Take first d files and merge them
                InputStreamInterface reader = ReadersWritersFactory.getNewReaderInstance(readerType, B);
                reader.open(sortedFilesQueue.poll());
                readers.add(reader);
            }
            /*
            Read first input from each file
             */
            for (int i = 0; i < readers.size(); i++) { // readers array size is d at most
                if (!readers.get(i).endOfStream()) {
                    inputsReference.add(readers.get(i).readLine());
                    pQueue.offer(inputsReference.get(i));
                } else {
                    readers.remove(i);
                    i--;
                }
            }

            while (!pQueue.isEmpty()) {
                String minimum = pQueue.poll();
                writer.writeLine(minimum);
                int indexOfMinimum = inputsReference.indexOf(minimum);
                if (!readers.get(indexOfMinimum).endOfStream()) {
                    inputsReference.set(indexOfMinimum, readers.get(indexOfMinimum).readLine());
                    pQueue.offer(inputsReference.get(indexOfMinimum));
                }
            }

            for (int i = 0; i < readers.size(); i++) {
                readers.get(i).close();
                readers.remove(i);
            }
            batchCount++;
            writer.close();
            inputsReference.clear();
        }


        // Merge the remaining files
        while (!sortedFilesQueue.isEmpty()) {
            InputStreamInterface reader = ReadersWritersFactory.getNewReaderInstance(readerType, B);
            reader.open(sortedFilesQueue.poll());
            readers.add(reader);
        }
        writer.create(SORTED_FILES_DESTINATION + "SORTED_FILE_" + fileName.replaceAll(".csv", "") + ".txt");

         /*
            Read first input from each file
             */
        for (int i = 0; i < readers.size(); i++) { // readers array size is d at most
            if (!readers.get(i).endOfStream()) {
                inputsReference.add(readers.get(i).readLine());
                pQueue.offer(inputsReference.get(i));
            } else {
                readers.remove(i);
                i--;
            }
        }

        while (!pQueue.isEmpty()) {
            String minimum = pQueue.poll();
            writer.writeLine(minimum);
            int indexOfMinimum = inputsReference.indexOf(minimum);
            if (!readers.get(indexOfMinimum).endOfStream()) {
                inputsReference.set(indexOfMinimum, readers.get(indexOfMinimum).readLine());
                pQueue.offer(inputsReference.get(indexOfMinimum));
            }
        }

        for (int i = 0; i < readers.size(); i++) {
            readers.get(i).close();
            readers.remove(i);
        }

        writer.close();
        inputsReference.clear();
    }

    @Benchmark
    @Warmup(iterations = 1)
    @Fork(value = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public static void LineReaderAndLineWriterSort(ExperimentsConfig.FileName name, ExperimentsConfig.K column,
                                                   ExperimentsConfig.M M, ExperimentsConfig.D D) {
        extSort(ExperimentsConfig.IMDB_DIR + name.name, column.k, M.M, D.d, 0, StreamType.LINE, StreamType.LINE, name.name);
    }
}

