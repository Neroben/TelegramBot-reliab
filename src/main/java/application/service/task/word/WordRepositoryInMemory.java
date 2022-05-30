package application.service.task.word;

import application.service.task.WordAnswers;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class WordRepositoryInMemory {
    TreeMap<String, WordAnswers> wordDatabase = new TreeMap<>(Map.of(
            "silly", getWordAnswers( "забавный", "мир", "смешной", "веселый"),
            "peace", getWordAnswers("мир", "смешной", "веселый", "забавный"),
            "happy", getWordAnswers("веселый","глупый", "смешной",  "забавный"),
            "green", getWordAnswers("зеленый","глупый",  "веселый", "забавный"),
            "tree", getWordAnswers("дерево", "глупый", "смешной", "веселый")
    ));

    private WordAnswers getWordAnswers(String... answers) {
        List<String> answers1 = new java.util.ArrayList<>(List.of(answers));
        Collections.shuffle(answers1);
        return new WordAnswers(answers[0], answers1);
    }

    LinkedHashMap<Long, Map.Entry<String, WordAnswers>> userTaskMapping = new LinkedHashMap<>();

    public Map.Entry<String, WordAnswers> getNextTask(Long userId) {
        Map.Entry<String, WordAnswers> any;
        Map.Entry<String, WordAnswers> wordAnswers = userTaskMapping.get(userId);
        if (wordAnswers != null) {
            String taskKey = wordAnswers.getKey();
            any = wordDatabase.higherEntry(taskKey);
            if (any == null) {
                userTaskMapping.put(userId, null);
                return null;
            }
        } else {
            any = wordDatabase.firstEntry();
        }
        userTaskMapping.put(userId, any);
        return any;
    }

    public Map.Entry<String, WordAnswers> getCurrentTask(Long userId) {
        return userTaskMapping.get(userId);
    }

    public Boolean checkTask(Long userId, String answer) {
        Map.Entry<String, WordAnswers> task = userTaskMapping.get(userId);
        return task.getValue().getRightAnswer().equals(answer);
    }
}
