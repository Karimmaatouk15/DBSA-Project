package experiments;

import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;


public class ExperimentsConfig {

    public static final String IMDB_DIR = "C:/Users/karim/Desktop/imdb/";

    @State(Scope.Thread)
    public static class FileName {
        @Param({"info_type.csv", "keyword.csv"})
        String name;
    }

    @State(Scope.Thread)
    public static class BlockSize {
        @Param({"4096", "1048576"})
        int size;
    }

    @State(Scope.Thread)
    public static class J {
        @Param({"10", "50", "100"})
        int j;
    }

    @State(Scope.Thread)
    public static class FilesList {
        @Param({"info_type.csv, keyword.csv", "info_type.csv, keyword.csv, comp_cast_type.csv"})
        String filesListString;
    }
}
