import de.arnomann.martin.jta.api.entities.Chat;
import de.arnomann.martin.jta.api.events.Listener;
import de.arnomann.martin.jta.api.events.MessageReceivedEvent;
import org.jibble.pircbot.IrcException;

import java.io.IOException;

public class EventListener implements Listener {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        try {
            event.getMessage().getChannel().connect(JTATest.chatOAuth, false);

            System.out.println(event.toString());

            if (event.getMessage().getContent().equals("!stop")) {
                System.out.println("Should stop");
                Chat c = event.getMessage().getChannel();
                c.sendMessage("Stopping...");
                c.disconnect();
            }
        } catch (IrcException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
