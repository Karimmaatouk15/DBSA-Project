package benchmark;

import experiments.RandJump;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.FileOutputStream;
import java.io.PrintStream;

public class BenchmarkRandJump {
    public static void main(String[] args) throws RunnerException {
        try {
            PrintStream out = new PrintStream(new FileOutputStream("C:/Users/karim/Desktop/results/randjump.txt"));
            System.setOut(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Options opt = new OptionsBuilder()
                .include(RandJump.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();

    }
}
