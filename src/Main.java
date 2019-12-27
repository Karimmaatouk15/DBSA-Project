import readers.CharacterReader;
import readers.LineReader;
import readers.SizedBufferReader;
import writers.CharacterWriter;
import writers.LineWriter;
import writers.MemoryMappedWriter;
import interfaces.InputStreamInterface;
import interfaces.OutputStreamInterface;
import readers.MemoryMappedReader;
import writers.SizedBufferWriter;

import javax.sound.sampled.Line;

public class Main {


    public static void main(String[] args) {
        // Test reading a file
        InputStreamInterface reader = new SizedBufferReader(142000);
        OutputStreamInterface writer = new SizedBufferWriter(142000);
        reader.open("C:/Users/karim/Desktop/imdb/keyword.csv");
        writer.create("C:/Users/karim/Desktop/output.csv");
        while (!reader.endOfStream()) {
//            String readValue = ;
//            System.out.print(readValue + " ");
            writer.writeLine(reader.readLine());
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
