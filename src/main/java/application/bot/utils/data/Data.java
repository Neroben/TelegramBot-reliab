package application.bot.utils.data;

import application.user.dao.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Data {

    private User user;

    private String message;

    private List<String> keyboard = new ArrayList<>(5);

}
