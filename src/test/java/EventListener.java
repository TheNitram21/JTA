import de.arnomann.martin.jta.api.entities.Chat;
import de.arnomann.martin.jta.api.events.Listener;
import de.arnomann.martin.jta.api.events.MessageReceivedEvent;
import org.jibble.pircbot.IrcException;

import java.io.IOException;

public class EventListener implements Listener {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        try {
            event.getMessage().getChannel().connect(false);

            System.out.println(event);

            if (event.getMessage().getContent().equals("!stop")) {
                System.out.println("Should stop");
                Chat c = event.getMessage().getChannel();
                c.sendMessage("Stopping...");
                c.disconnect();
                JTATest.bot.stop();
            }
        } catch (IrcException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
