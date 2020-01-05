package experiments;

import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;


public class ExperimentsConfig {

    public static final String IMDB_DIR = "C:/Users/karim/Desktop/imdb/";

    @State(Scope.Thread)
    public static class FileName { // List of files for Experiment 1.1 and Experiment 1.2 to compute their length sum
        @Param({"aka_name.csv", "aka_title.csv", "cast_info.csv", "char_name.csv", "comp_cast_type.csv", "company_name.csv", "company_type.csv", "complete_cast.csv",
                "info_type.csv", "keyword.csv", "kind_type.csv", "link_type.csv", "movie_companies.csv", "movie_info.csv", "movie_info_idx.csv", "movie_keyword.csv", "movie_link.csv",
                "name.csv", "person_info.csv"})
        String name;
    }

    @State(Scope.Thread)
    public static class BlockSize {
        /*
        Block Size is varied as follows :
        */
        @Param({"512", // 0.5 KB
                "4096", // 4 KB
                "32768",//   32 KB
                "102400", // 100 KB
                "512000", // 500 KB
                "1048576", // 1 MB
                "2097152", // 2 MB
                "4194304", // 4MB
                "16777216",//16MB
                "67108864", //64MB
                "268435456",//256 MB
                "1073741824"})//1GB
                int size;
    }

    @State(Scope.Thread)
    public static class J { // J param for Experiment 1.2 RandJump ( Number of times the random jumping happens)
        @Param({"10", "100", "500", "1000", "10000", "50000", "100000"})
        int j;
    }

    @State(Scope.Thread)
    public static class FilesList { // Files list to merge for Experiment 1.3.
        @Param({"kind_type.csv, link_type.csv",
                "kind_type.csv, link_type.csv,info_type.csv",
                "kind_type.csv, link_type.csv,info_type.csv,comp_cast_type.csv",
                "kind_type.csv, link_type.csv,info_type.csv,comp_cast_type.csv, complete_cast.csv",
                "kind_type.csv, link_type.csv,info_type.csv,comp_cast_type.csv, complete_cast.csv, keyword.csv"})
        String filesListString;
    }

    @State(Scope.Thread)
    public static class K { // K param for ExtSort defining the column number that the sort should occur on
        @Param({"0"})
        int k;
    }

    @State(Scope.Thread)
    public static class M { // M param for ExtSort defining the size of the available memory in bytes
        @Param({"10240",
                "32768",//   32 KB
                "512000", // 500 KB)
        })
        int M;
    }

    @State(Scope.Thread)
    public static class D { // d param for ExtSort defining the number of files that the External merge sort should merge
        @Param({"10", "20", "50"})
        int d;
    }
}
