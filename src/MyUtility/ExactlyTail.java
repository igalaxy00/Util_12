package MyUtility;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;

@SuppressWarnings("WeakerAccess")
public class ExactlyTail {
    private int lastSymbols;
    private int lastLines;

    public ExactlyTail(int lastSymbols, int lastLines) {
        this.lastSymbols = lastSymbols;
        this.lastLines = lastLines;
    }

    public void wholeTail(String inputFile, BufferedWriter to ) throws IOException {
        BufferedReader from;
        if (inputFile == null) {
            System.out.println("Enter text:");
            from = new BufferedReader(new InputStreamReader(System.in));
        } else{
            from = Files.newBufferedReader(Paths.get(inputFile));
        }

        if (lastSymbols ==0)
            lastLines(from, to );
        else
            lastChars(from, to);
        to.write(System.lineSeparator());
    }

    private void addFromDeque(ArrayDeque Deque , BufferedWriter to , boolean isLines) throws IOException {
        while (Deque.peekFirst() != null){
            to.write(String.valueOf(Deque.pollFirst()));
            if (Deque.size() > 0 && isLines)
                to.write(System.lineSeparator());
        }
    }

    private void lastChars(BufferedReader from, BufferedWriter to) throws IOException {
        ArrayDeque<Character> symbols = new ArrayDeque<>(lastSymbols);
        int ch;
        while ((ch = from.read()) > -1) {
            if (symbols.size() == lastSymbols)  symbols.pollFirst();
            symbols.addLast((char) ch);
        }
        addFromDeque(symbols,to , false);
    }

    private void lastLines(BufferedReader from, BufferedWriter to) throws IOException{
        ArrayDeque<String> lines = new ArrayDeque<>(lastLines);
        String line;
        while ((line = from.readLine()) != null) {
            if (lines.size() == lastLines) lines.pollFirst();
            lines.addLast(line);
        }
        addFromDeque(lines,to , true);
    }
}
