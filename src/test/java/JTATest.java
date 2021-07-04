import de.arnomann.martin.jta.api.JTA;
import de.arnomann.martin.jta.api.JTABot;
import de.arnomann.martin.jta.api.JTABotBuilder;
import de.arnomann.martin.jta.api.Logger;
import de.arnomann.martin.jta.api.entities.Chat;
import de.arnomann.martin.jta.api.entities.User;
import org.jibble.pircbot.IrcException;

import java.io.IOException;

public class JTATest {

    private static Logger l;

    public static User nitram;

    public static final String chatOAuth = "oauth:2tpc7vmegc0k3m4anh06wfvew30rmk";

    public static void main(String[] args) {
        JTA.initialize();

        JTABot bot = JTABotBuilder.create("f2z5yb5i49k63vi1mlv35a3gd39ctv", "6njlhk8ieyjrpvq5lyorz4harwbzo3");

        l = new Logger();

        bot.registerEventListeners(new EventListener());

        nitram = bot.getUserByName("TheNitram21");

        System.out.println(nitram.isLive().queue());

        Chat nitramChat = nitram.getChat();

        try {
            nitramChat.connect("oauth:2tpc7vmegc0k3m4anh06wfvew30rmk", false);
            nitramChat.clear();
            nitramChat.sendMessage("Hi KonCha");
        } catch (IOException | IrcException e) {
            e.printStackTrace();
        }

        System.out.println(bot.getClipBySlug("EnticingCorrectDelicataDansGame-ZjHJQXu6ob2R-j19").getTitle());

        l.info("Started bot.");
    }

}
