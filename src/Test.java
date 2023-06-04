import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
        String result = "C:\\Users\\dmari\\IdeaProjects\\SuggestionsCorpus\\files\\result.txt";

        List<String> listData = Files.readAllLines(Paths.get(result));
        String data = listData.get(0);
        String input = "how ";

        String suggestions = Main.getSuggestions(input,data);
        System.out.println("Suggestions: "+suggestions);
    }
}
