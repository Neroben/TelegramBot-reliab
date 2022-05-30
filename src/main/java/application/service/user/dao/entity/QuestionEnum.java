package application.service.user.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public enum QuestionEnum {

    WORDS("Слова"),
    GRAMMAR("Грамматика"),
    READING("Чтение"),
    LISTENING("Слушание");

    private static final Map<String, QuestionEnum> map = Map.of(WORDS.name, WORDS,
            GRAMMAR.name, GRAMMAR,
            READING.name, READING,
            LISTENING.name, LISTENING);
    private final String name;

    public static QuestionEnum of(String name) {
        return map.get(name);
    }
}
