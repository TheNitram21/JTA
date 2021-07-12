# JTA (Java Twitch API)
A Java Wrapper for the Twitch API. **The API is in development currently, so there can be a lot of problems.**

## Summary
1. [Download](#download)
2. [Building your first Bot](#building-your-first-bot)
3. [Sending Messages](#sending-messages)
4. [Events](#events)
5. [Clips](#clips)

## Download
Currently, to get the API, you need to go to [JitPack](https://jitpack.io/#NitramMann21/JTA) and get the dependency there. In the future, I want to switch to another maven repository.

## Building your first Bot
First of all, go to the [Twitch Developer Console](https://dev.twitch.tv/console/apps) and create an application. **2FA (Two Factor Authentication) has to be enabled for that.** Then you can start coding.

We tried to make the library as easy as possible, so to create a bot, you only need to do the following things:
  1. Initialize the library using `JTA.initialize()`.
  2. Create a bot instance using the builder.
This looks like this:
```java
public static void main(String[] args) {
  JTA.initialize();
  
  JTABot bot = JTABotBuilder.create("CLIENTID", "CLIENTSECRET");
}
```
This creates a bot instance, however, you see the two Strings. To make it work, you need to replace them. Replace `CLIENTID` with your client id and `CLIENTSECRET` with your client secret.

## Sending Messages
To send a message, you first need to get the chat of a user's channel. Then connect to the chat. Now you can send the message.
```java
public static void main(String[] args) {
  // Initialize the library.
  JTA.initialize();
  
  // Create a bot.
  JTABot bot = JTABotBuilder.create("CLIENTID", "CLIENTSECRET");
  
  // Get the user. In this case, I chose myself.
  User user = bot.getUserByName("TheNitram21");
  
  // Get the chat of the user's channel.
  Chat chat = user.getChannel().getChat();
  
  try {
    // Connect to the chat.
    chat.connect("OAUTH", false);
    
    // Send the message.
    chat.sendMessage("Hi KonCha");
    
    // Disconnect from chat.
    chat.disconnect();
  } catch(IrcException | IOExcecption e) {
    e.printStackTrace();
  }
}
```
Here you can again see a String you need to fill in, `OAUTH`. You need to put the OAuth-Chat-Token of your user in there. Log into twitch with the user which should send the messages, then go to [Twitchapps](https://www.twitchapps.com/tmi) and get your token there.

## Events
To receive messages, you need an event listener. To create one, simply create a class implementing `Listener`. Then add it to the bot.
```java
public static void main(String[] args) {
  // Initialize the library.
  JTA.initialize();
  
  // Create a bot.
  JTABot bot = JTABotBuilder.create("CLIENTID", "CLIENTSECRET");
  
  // Add the listener.
  bot.addEventListeners(new MessageListener());
  
  // Get the user. In this case, I chose myself.
  User user = bot.getUserByName("TheNitram21");
  
  // Get the chat of the user.
  Chat chat = user.getChannel().getChat();
  
  try {
    // Connect to the chat.
    chat.connect("OAUTH", false);
    
    // Send the message.
    chat.sendMessage("Hi KonCha");
  } catch(IrcException | IOExcecption e) {
    e.printStackTrace();
  }
}

public static class MessageListener implements Listener {
  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    // Print the event.
    System.out.println(event.toString());
  }
}
```
In this code example, you may ask: "Why don't we disconnect from the chat?" That's because you can only receive messages from chats you're connected to. Imagine getting all messages from a 100k viewers streamer! (Your computer would make pew)

## Clips
To get a clip, you only need to call `JTABot#getClipBySlug(String)` and you have your clip.
```java
public static void main(String[] args) {
    // Create the bot
    JTABot bot = JTABotBuilder.create(clientId, clientSecret);
    
    // Get the clip
    Clip clip = bot.getClipBySlug("EnticingCorrectDelicataDansGame-ZjHJQXu6ob2R-j19");
    
    // Print the clip information
    System.out.println("Clip '" + clip.getSlug() + "': " + clip.getTitle());
}
```
