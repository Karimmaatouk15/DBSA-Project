import readers.*;
import writers.MemoryMappedWriter;
import writers.SizedBufferWriter;
import interfaces.InputStreamInterface;
import interfaces.OutputStreamInterface;

public class Main {


    public static void main(String[] args) {
        // Test reading a file
        InputStreamInterface reader = new MemoryMappedReader(4096);
        OutputStreamInterface writer = new MemoryMappedWriter(4096);
        reader.open("C:/Users/karim/Desktop/imdb/info_type.csv");
        writer.create("C:/Users/karim/Desktop/outputBufferread2.csv");
        while (!reader.endOfStream()) {
//            String readValue = ;
//            System.out.print(readValue + " ");
            reader.readLine();
        }


        reader.seek(2);
//        System.out.println("\nSTREAM AGAIN");
//        while (!reader.endOfStream()) {
//            System.out.print((reader.readln()) + " ");
//        }
        while (!reader.endOfStream()) {
//            String readValue = ;
//            System.out.print(readValue + " ");
            writer.writeLine(reader.readLine());
        }
        reader.close();
        writer.close();

    }


}
