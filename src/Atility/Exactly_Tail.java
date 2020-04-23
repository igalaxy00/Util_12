package Atility;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;

@SuppressWarnings("WeakerAccess")
public class Exactly_Tail {
    private int lSymbols;
    private int lLines;

    public Exactly_Tail(int lSymbols, int lLines) {
        this.lSymbols = lSymbols;
        this.lLines = lLines;
    }


    public void WholeTail(String inputFile, BufferedWriter to ) throws IOException {
        BufferedReader from;
        if (inputFile == null) {
            System.out.println("Enter text:");
            from = new BufferedReader(new InputStreamReader(System.in));
        } else{
             from = Files.newBufferedReader(Paths.get(inputFile));
    }

        if (lSymbols==0) {
            lastLines(from, to );
        }else
            lastChars(from, to);
        to.write(System.lineSeparator());
    }

    private void AddToDeque(ArrayDeque Deque , BufferedWriter to , boolean isLines) throws IOException {
        while (Deque.peekFirst() != null) {
            to.write(String.valueOf(Deque.pollFirst()));//не null
            if (Deque.size() > 0 && isLines)//переход на новую строку если очередь не пуста
                to.write(System.lineSeparator());
        }
    }

    private void lastChars(BufferedReader from, BufferedWriter to) throws IOException {
        ArrayDeque<Character> symbols = new ArrayDeque<>(lSymbols);
        int ch;
        while ((ch = from.read()) > -1) {
            if (symbols.size() == lSymbols)  symbols.pollFirst();
            symbols.addLast((char) ch);
        }
        AddToDeque(symbols,to , false);
    }

    private void lastLines(BufferedReader from, BufferedWriter to) throws IOException{
        ArrayDeque<String> lines = new ArrayDeque<>(lLines);
        String line;
        while ((line = from.readLine()) != null) {
            if (lines.size() == lLines) lines.pollFirst();
            lines.addLast(line);
        }
        AddToDeque(lines,to , true);
    }
}
