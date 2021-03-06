package MyUtility;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.CmdLineException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TailLauncher {

    @Option(name = "-c", metaVar = "Symbols", usage = "Extract last symbols")
    private Integer  lastSymbols;// последние символы

    @Option(name = "-n", metaVar = "Lines", usage = "Extract last lines")
    private Integer lastLines; // последние строки

    @Option(name = "-o", metaVar = "OutputName", usage = "  output file")
    private String outputFileName; // файлы выода

    @Argument( metaVar = "InputName", usage = "Input file name")
    private String[] inputFiles ;// файлы ввода

    public static void main(String[] args) throws IOException {
        new TailLauncher().launch(args);
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

            ExactlyTail tail;
            if (lastSymbols != null && lastLines != null) {
                System.err.println("You can't use -n and -c together");
                return;
            }
            else if (lastSymbols != null){
                tail = new ExactlyTail(lastSymbols, 0);
            }
            else if (lastLines != null){
                tail = new ExactlyTail(0, lastLines);
            }
            else {
                tail = new ExactlyTail(0, 10);
            }

            if (inputFiles == null){
                tail.wholeTail(null, to);
            }
            else if (inputFiles.length > 1){
                for (String inputFile : inputFiles) {
                    to.write(new File(inputFile).getName());
                    to.write(System.lineSeparator());
                    tail.wholeTail(inputFile, to);
                }
            }else{
                tail.wholeTail(inputFiles[0], to);
            }
        }
    }
}

