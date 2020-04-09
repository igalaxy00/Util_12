package Atility;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.CmdLineException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Tail_Launcher {

    @Option(name = "-c", metaVar = "Symbols", usage = "Extract last symbols")
    private Integer  lSymbols;// последние символы
    @Option(name = "-n", metaVar = "Lines", usage = "Extract last lines")
    private Integer  lLines; // последние строки
    @Option(name = "-o", metaVar = "OutputName", usage = "  output file")
    private String outputFileName; // файлы выода
    @Argument(required = true, metaVar = "InputName", usage = "Input file name")
    private String[] inputFiles ;// файлы ввода

    public static void main(String[] args) throws IOException {
        new Tail_Launcher().launch(args);
    }

    private void launch(String[] args)  throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
 
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("Required format is: java -jar Exactly_Tail.jar -c Symbols -n Lines -o OutputName InputName");
            parser.printUsage(System.err);
            return;
        }

        try (BufferedWriter to = outputFileName == null ? new BufferedWriter(new OutputStreamWriter(System.out)) :
                Files.newBufferedWriter(Paths.get(outputFileName))) {

            Exactly_Tail tail;

            if (lSymbols != null && lLines != null) {
                System.err.println("You can't use -n and -c together");
                return;
            } else if (lSymbols != null)
                tail = new Exactly_Tail(lSymbols, 0);
            else if (lLines != null)
                tail = new Exactly_Tail(0, lLines);
            else
                tail = new Exactly_Tail(0, 10);

            for (String inputFile : inputFiles) {
                if (inputFiles.length > 1) {
                    to.write(new File(inputFile).getName());
                    to.newLine();
                }
                tail.WholeTail(inputFile, to);
            }
        }
    }
}