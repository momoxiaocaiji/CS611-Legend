import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class Parser {

    /**
     * parse the data in the config file according to the given type
     * the map between type and fileName is maintained in the Constant class
     * @param type
     * @return
     */
    public static List<String> parse(String type){
        String fileName = Constant.FILE_MAP.get(type);
        String filePath = System.getProperty("user.dir") + "/src/ConfigFiles/" + fileName +".txt";
        List<String> lines = Collections.EMPTY_LIST;
        try {
            lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
            // remove the first line
            lines.remove(0);
        } catch (IOException e) {
            System.out.println("Please enter the correct filepath");
            e.printStackTrace();
        }
        return lines;
    }

}
