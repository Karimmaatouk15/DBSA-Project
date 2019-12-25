import interfaces.InputStreamInterface;
import readers.CharacterReader;
import readers.LineReader;

public class Main {


    public static void main(String[] args) {
        // Test reading a file
        InputStreamInterface reader = new CharacterReader();


        reader.open("C:/Users/karim/Desktop/imdb/info_type.csv");

        while (!reader.endOfStream()) {
            System.out.print(reader.readln() + " ");
        }

        reader.seek(0);
        System.out.println("\nSTREAM AGAIN");
        while (!reader.endOfStream()) {
            System.out.print((reader.readln()) + " ");
        }
        reader.close();
    }
}
