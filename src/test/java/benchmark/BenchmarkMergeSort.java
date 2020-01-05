package benchmark;


import experiments.ExtSort;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.FileOutputStream;
import java.io.PrintStream;

public class BenchmarkMergeSort {

    public static void main(String[] args) throws RunnerException {
        try {
            PrintStream out = new PrintStream(new FileOutputStream("C:/Users/karim/Desktop/results/extsort.txt"));
            System.setOut(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Options opt = new OptionsBuilder()
                .include(ExtSort.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();

    }
}
