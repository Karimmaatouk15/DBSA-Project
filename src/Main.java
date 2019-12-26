import writers.MemoryMappedWriter;
import interfaces.InputStreamInterface;
import interfaces.OutputStreamInterface;
import readers.MemoryMappedReader;

public class Main {


    public static void main(String[] args) {
        // Test reading a file
        InputStreamInterface reader = new MemoryMappedReader(4096);
        OutputStreamInterface writer = new MemoryMappedWriter(4096);
        reader.open("C:/Users/karim/Desktop/imdb/info_type.csv");
        writer.create("C:/Users/karim/Desktop/output.csv");
        while (!reader.endOfStream()) {
            byte readValue = (byte) reader.readln();
            System.out.print(readValue + " ");
            writer.writeln(readValue);
        }

        writer.close();

//        reader.seek(0);
//        System.out.println("\nSTREAM AGAIN");
//        while (!reader.endOfStream()) {
//            System.out.print((reader.readln()) + " ");
//        }
        reader.close();
    }
}
