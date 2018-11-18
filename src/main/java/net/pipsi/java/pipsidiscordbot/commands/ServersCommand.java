package net.pipsi.java.pipsidiscordbot.commands;

import com.tazzie02.tazbotdiscordlib.Command;
import com.tazzie02.tazbotdiscordlib.SendMessage;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.pipsi.java.pipsidiscordbot.battlemetrics.BattleMetrics;

import java.awt.*;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServersCommand implements Command {

    private static final String SERVER_IDS[] = {"2693802", "2797943", "2693805", "2801412"};
    private static final String ONLINE_ICON = ":white_check_mark:";
    private static final String OFFLINE_ICON = ":x:";

    @Override
    public void onCommand(MessageReceivedEvent e, String[] args) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(getEmbedTitle(), getEmbedUrl());
        eb.setDescription(getEmbedDescription());
        eb.setColor(getEmbedColor());
        eb.setTimestamp(getEmbedTimestamp());
        for (MessageEmbed.Field field : getEmbedFields()) {
            eb.addField(field);
        }

         Message m = new MessageBuilder()
                .setEmbed(eb.build())
                .build();

        SendMessage.sendMessage(e, m);
    }

    private String[] getServerIds() {
        return SERVER_IDS;
    }

    private String getEmbedTitle() {
        return "DayZ Servers Brought To You By Pipsi.net";
    }

    private String getEmbedDescription() {
        return "-";
    }

    private String getEmbedUrl() {
        return "https://www.pipsi.net/";
    }

    private Color getEmbedColor() {
        return new Color(25, 42, 51);
    }

    private TemporalAccessor getEmbedTimestamp() {
        return ZonedDateTime.now();
    }

    private List<MessageEmbed.Field> getEmbedFields() {
        List<MessageEmbed.Field> fields = new ArrayList<>();

        List<Thread> threads = new ArrayList<>();
        for (String serverId : getServerIds()) {
            Thread thread = new Thread(() -> {
                try {
                    fields.add(getField(serverId));
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return fields;
    }

    private MessageEmbed.Field getField(String id) throws IOException {
        final String ONLINE_TEXT = "online";
        BattleMetrics metric = new BattleMetrics(id);

        String name = String.format("%s %s",
                metric.getStatus().equalsIgnoreCase(ONLINE_TEXT) ? ONLINE_ICON : OFFLINE_ICON,
                metric.getName());

        String value = String.format("%d/%d Players - %s:%d",
                metric.getPlayers(),
                metric.getMaxPlayers(),
                metric.getIp(),
                metric.getPort());

        System.out.println("Name: " + name);
        System.out.println("Value: " + value);

        MessageEmbed.Field field = new MessageEmbed.Field(name, value, false);
        return field;
    }

    @Override
    public CommandAccess getAccess() {
        return CommandAccess.ALL;
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("servers");
    }

    @Override
    public String getDescription() {
        return "Get a list of all the Pipsi servers.";
    }

    @Override
    public String getName() {
        return "Servers command.";
    }

    @Override
    public String getDetails() {
        return "servers - Get a list of all the Pipsi servers.";
    }

    @Override
    public boolean isHidden() {
        return false;
    }
}
