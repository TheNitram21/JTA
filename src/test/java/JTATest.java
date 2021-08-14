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
        bot.addNeededPermissions(EnumSet.of(Permission.CHANNEL_MANAGE_STREAM));
        bot.setChatOAuthToken(chatOAuth);

        System.out.println(bot.getUserAccessTokenLink());

        nitram = bot.getUserByName("TheNitram21");

        bot.setUserAccessToken(nitram, "q1433wkx68c3gbl419olg0bitdg4j8");

        l = new Logger();

        bot.registerEventListeners(new EventListener());

        System.out.println("Ich bin" + (nitram.getChannel().isLive().queue() ? " " : " nicht ") + "live.");
        System.out.println("Nitram user creation date: " + nitram.getCreationTime().getDayOfMonth() + "." + nitram.getCreationTime().getMonthValue() + "." +
                nitram.getCreationTime().getYear());

        nitram.getChannel().setStreamTitle("JTA!!!");

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
            System.out.println(Arrays.toString(teamMembers.toArray()));
        } catch(Exception e) {
            e.printStackTrace();
        }

        try {
            StreamSchedule schedule = nitram.getStreamSchedule();
            for (StreamScheduleSegment segment : schedule.getSegments().queue())
                System.out.println(segment.getTitle());
        } catch(ErrorResponseException e) {
            e.printStackTrace();
        }

        User tjc = bot.getUserByName("thejocraft_live");

        List<ChatBadge> chatBadges = tjc.getChannel().getChatBadges();
        for(int i = 0; i < chatBadges.size(); i++) {
            System.out.print(chatBadges.get(i).getId());
            if(i + 1 != chatBadges.size())
                System.out.print(", ");
            else
                System.out.println();
        }

        List<ChatBadge> globalChatBadges = bot.getGlobalChatBadges();
        for(int i = 0; i < globalChatBadges.size(); i++) {
            System.out.print(globalChatBadges.get(i).getId());
            if(i + 1 != globalChatBadges.size())
                System.out.print(", ");
            else
                System.out.println();
        }

        List<Emote> emotes = tjc.getChannel().getCustomEmotes();
        for(int i = 0; i < emotes.size(); i++) {
            System.out.print(emotes.get(i).getName());
            if(i + 1 != emotes.size())
                System.out.print(", ");
            else
                System.out.println();
        }

        List<Emote> globalEmotes = bot.getGlobalEmotes();
        for(int i = 0; i < globalEmotes.size(); i++) {
            System.out.print(globalEmotes.get(i).getName());
            if(i + 1 != globalEmotes.size())
                System.out.print(", ");
            else
                System.out.println();
        }

        l.info("Successfully tested API.");
        bot.stop();
    }

}
