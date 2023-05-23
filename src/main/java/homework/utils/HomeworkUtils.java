package homework.utils;

import com.solvd.banking.fileHandling.FileHandling;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class HomeworkUtils {

    private static final String FILE_PATH = "src/main/resources/homeworkUtils/hwFileReading.txt";
    private static final String OUTPUT_PATH = "src/main/resources/homeworkUtils/output.txt";

    public static void main(String[] args) {
        calculateUniqueWords();
    }

    private static void calculateUniqueWords() {
        String content = FileHandling.readFile(FILE_PATH);
        assert content != null;
        String[] wordsArray = content.split("[\\s,.]+");        // split the words by spaces , .

        Map<String, Integer> wordMap = new TreeMap<>();

        Arrays.stream(wordsArray).forEach(word ->
                {
                    word = word.toLowerCase();

                    if (!wordMap.containsKey(word)) {
                        wordMap.put(word, 1);
                    } else {
                        wordMap.put(word, wordMap.get(word) + 1);
                    }
                }
        );

        content = wordMap.entrySet().stream()
                        .map(entry -> entry.getKey() + " ~> " + entry.getValue())
                                .collect(Collectors.joining("\n"));


        if(!FileHandling.readFile(OUTPUT_PATH).isBlank()) FileHandling.writeFile(OUTPUT_PATH, "", false );
        FileHandling.writeFile(OUTPUT_PATH, "Unique words: " + wordMap.size()+ "\n", true);
        FileHandling.writeFile(OUTPUT_PATH, content, true);
    }

}
