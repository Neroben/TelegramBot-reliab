package application.bot.utils.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Message {

    private String text;

    private List<String> keyboard;

    private String supportMessage;

    private String backKey;

}
