package Atility;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Objects;

@SuppressWarnings("WeakerAccess")
public class Exactly_Tail {
    private int lSymbols;
    private int lLines;

    public Exactly_Tail(int lSymbols, int lLines) {
        this.lSymbols = lSymbols;
        this.lLines = lLines;
    }

    public void WholeTail(String inputFile, BufferedWriter to ) throws IOException{
        BufferedReader from = Files.newBufferedReader(Paths.get(inputFile));
        if (lSymbols==0) {
            lastLines(from, to );
        }else
            lastChars(from, to);
        to.newLine();
    }

    private void AddToDeque(ArrayDeque Deque , BufferedWriter to , boolean isLines) throws IOException {
        while (Deque.peekFirst() != null) {
            to.write(String.valueOf(Objects.requireNonNull(Deque.pollFirst())));
            if (Deque.size() > 0 && isLines)
                to.newLine();
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
            lines.addLast(line);
            if (lines.size() > lLines) lines.pollFirst();
        }
        AddToDeque(lines,to , true);
    }
}
