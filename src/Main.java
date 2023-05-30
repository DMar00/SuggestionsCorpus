import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    final static int BASE = 28; // Range dei numeri compresi tra 0 e 27
    public record Coppia(char carattere, int numOcc) implements Comparable<Coppia>{
        @Override
        public String toString() {
            return carattere + ":"+ numOcc;
        }

        @Override
        public int compareTo(Coppia o) {
            if(o.carattere == '*' || carattere == '*') {
                if(o.carattere == carattere)
                    return 0;
                return o.carattere == '*' ? -1 : 1;
            }
            return Integer.compare(o.numOcc, numOcc);
        }
    }

    public static void main(String[] args) {
        String corpus = "C:\\Users\\dmari\\Downloads\\eng_news_2020_10K\\eng_news_2020_10K-sentences.txt";
        String result = "C:\\Users\\dmari\\IdeaProjects\\SuggestionsCorpus\\files\\result.txt";

        //sentences contains all strings in lowercase
        List<String> sentences = new ArrayList<>();

        //generate file result.txt
        try {
            sentences = Files.readAllLines(Paths.get(corpus));
            sentences = sentences.stream().map(String::toLowerCase).collect(Collectors.toList());

            int[][] occorrenze = calcola(sentences);

            PrintWriter pw = new PrintWriter(result);

            for(int i=0; i<occorrenze.length; i++) {
                System.out.print(numeroASequenza(i, 4) +'\t');
                Coppia[] oc = new Coppia[occorrenze[i].length];
                for(int j=0 ; j < occorrenze[i].length; j++)
                    oc[j] = new Coppia(numToChar(j), occorrenze[i][j]);
                Arrays.sort(oc);
                System.out.println(Arrays.toString(oc));
                for(int w = 0 ; w<6 ; w++) {
                    pw.print(oc[w].carattere);
                }
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //
        try {
            List<String> resultList = new ArrayList<>();
            resultList = Files.readAllLines(Paths.get(result));
            String s = resultList.get(0);

            String context = "aron";
            int nSuggestions = 4; //[max 28]
            String suggestionsString = getSuggestions(context, s);
            System.out.println(suggestionsString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getSuggestions(String context, String suggestionData) {
        //transform sequence into number
        int sequenceNumber = sequenzaANumero(context);
        return suggestionData.substring(sequenceNumber*6,(sequenceNumber*6)+6 );
    }

    /*****************************************************/
    public static int[][] calcola(List<String> sentences) {
        int res[][] = new int[BASE*BASE*BASE*BASE][];

        for( int i=0; i<res.length; i++)
            res[i] = new int[BASE];

        for(String s : sentences) {
            for(int i=0; i < s.length(); i++) {
                int p = i - 4;
                String spaces = "";
                for(; p<0 ; p++)
                    spaces += " ";
                String cur = spaces + s.substring(p, i);
                res[sequenzaANumero(cur)][charToNum(s.charAt(i))]++;
            }
        }
        return res;
    }

    /*****************************************************/
    public static int sequenzaANumero(String sequenza) {
        int numero = 0;
        for (int i = 0; i < sequenza.length(); i++) {
            char c = sequenza.charAt(i);
            int n = charToNum(c);
            numero += n * Math.pow(BASE, i);
        }
        return numero;
    }

    public static String numeroASequenza(int numero, int lunghezzaSequenza) {
        String sequenza = "";
        while (numero > 0) {
            sequenza += numToChar(numero % BASE);
            numero /= BASE;
        }

        // Aggiungi zeri iniziali se necessario
        while (sequenza.length() < lunghezzaSequenza) {
            sequenza += ' ';
        }

        return sequenza;
    }

    /*****************************************************/
    public static char numToChar(int n) {
        if (n >= 0 && n <= 25)
            return (char) (n + 'a');
        else if (n == 26)
            return ' ';
        else
            return '*';
    }

    public static int charToNum(char c) {
        if (c == ' ')
            return 26;
        else if (c >= 'a' && c <= 'z')
            return c - 'a';
        else
            return 27;
    }
}