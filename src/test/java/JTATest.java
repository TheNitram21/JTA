import de.arnomann.martin.jta.api.*;
import de.arnomann.martin.jta.api.entities.*;
import de.arnomann.martin.jta.api.exceptions.ErrorResponseException;
import kotlin.ExceptionsKt;
import org.jibble.pircbot.IrcException;

import java.io.IOException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class JTATest {

    private static Logger l;

    public static User nitram;

    public static JTABot bot;

    public static final String chatOAuth = "oauth:2tpc7vmegc0k3m4anh06wfvew30rmk";

    public static void main(String[] args) {
        JTA.initialize();

        bot = JTABotBuilder.create("f2z5yb5i49k63vi1mlv35a3gd39ctv", "6njlhk8ieyjrpvq5lyorz4harwbzo3");
        bot.setChatOAuthToken(chatOAuth);
        bot.addNeededPermissions(EnumSet.of(Permission.USER_MANAGE_FOLLOWS, Permission.USER_MANAGE_BLOCKED_USERS));

        l = new Logger();

        bot.registerEventListeners(new EventListener());

        nitram = bot.getUserByName("TheNitram21");

        System.out.println("Ich bin" + (nitram.getChannel().isLive().queue() ? " " : " nicht ") + "live.");

        Chat nitramChat = nitram.getChannel().getChat();

        try {
            nitramChat.connect(false);
            nitramChat.clear();
            nitramChat.sendMessage("Hi KonCha");
        } catch (IOException | IrcException e) {
            e.printStackTrace();
        }

        //System.out.println(bot.getClipBySlug("EnticingCorrectDelicataDansGame-ZjHJQXu6ob2R-j19").getTitle());
        System.out.println(bot.getVideoById(1092428530L).getTitle().queue());

        try {
            Team team = bot.getTeamByName("CLASSIFIED");
            List<User> teamMembers = team.getMembers().queue();
        } catch(Exception e) {
            e.printStackTrace();
        }

        StreamSchedule schedule = nitram.getStreamSchedule();
        for(StreamScheduleSegment segment : schedule.getSegments().queue())
            System.out.println(segment.getTitle());

        l.info("Started bot.");
    }

}
