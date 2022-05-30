package application.service.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WordAnswers {
    String rightAnswer;
    List<String> answers;
}
