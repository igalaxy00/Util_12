import MyUtility.TailLauncher;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TUtil {

    private boolean isEqualToOutput(String real) throws IOException {
        return Files.readAllLines(Paths.get("tests/res/output")).equals(Files.readAllLines(Paths.get(real)));
    }

    private String[] requiredOutputs = {"tests/res/outputExp" , "tests/res/outputExp2" , "tests/res/outputExp3"};

    private String[] testArguments = {"-c 3 -o tests/res/output tests/res/input1",
            "-n 3 -o tests/res/output tests/res/input1",
            "-o tests/res/output tests/res/input1 tests/res/input2"};
    @Test
    void test() throws IOException {
        for (int i = 0; i < 3; i++) {
            TailLauncher.main(testArguments[i].split(" "));
            assertTrue(isEqualToOutput(requiredOutputs[i]));
        }
    }
}
