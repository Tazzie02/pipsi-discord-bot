package net.pipsi.java.pipsidiscordbot;

import com.tazzie02.tazbotdiscordlib.SendMessage;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServersOutputRefresher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServersOutputRefresher.class);
    private final JDA jda;
    private final String[] serverIds;
    private final TextChannel channel;
    private final int outputRate;

    private boolean running;

    public ServersOutputRefresher(JDA jda, String[] serverIds, TextChannel channel, int outputRate) {
        this.jda = jda;
        this.serverIds = serverIds;
        this.channel = channel;
        // Convert to Milliseconds
        this.outputRate = outputRate * 1000;
    }

    public void start() {
        running = true;
        new Thread(new OutputLoop()).start();
    }

    public void stop() {
        this.running = false;
    }

    private class OutputLoop implements Runnable {

        @Override
        public void run() {
            while (running) {
                Message message = ServersEmbed.build(serverIds);
                SendMessage.sendMessage(channel, message);

                try {
                    Thread.sleep(outputRate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
