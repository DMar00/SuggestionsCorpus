import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        String result = "C:\\Users\\dmari\\IdeaProjects\\SuggestionsCorpus\\files\\result.txt";

        //test
        try {
            List<String> resultList = new ArrayList<>();
            resultList = Files.readAllLines(Paths.get(result));
            String s = resultList.get(0);

            String context = "aron";
            int nSuggestions = 4; //[max 28]
            String suggestionsString = getSuggestions(context, s, nSuggestions);
            System.out.println(suggestionsString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getSuggestions(String context, String suggestionData, int n) {
        //transform sequence into number
        int sequenceNumber = Main.sequenzaANumero(context);
        return suggestionData.substring(sequenceNumber*6,(sequenceNumber*6)+6 );
    }
}
