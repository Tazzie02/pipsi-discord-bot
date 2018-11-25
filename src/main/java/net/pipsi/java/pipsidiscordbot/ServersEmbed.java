package net.pipsi.java.pipsidiscordbot;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.pipsi.java.pipsidiscordbot.battlemetrics.BattleMetrics;

import java.awt.*;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

public class ServersEmbed {

    private static final Color COLOR = new Color(25, 42, 51);
    private static final String ONLINE_ICON = ":white_check_mark:";
    private static final String OFFLINE_ICON = ":x:";

    public static Message build(String[] serverIds) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(getEmbedTitle(), getEmbedUrl());
        eb.setDescription(getEmbedDescription());
        eb.setColor(getEmbedColor());
        eb.setTimestamp(getEmbedTimestamp());
        for (MessageEmbed.Field field : getEmbedFields(serverIds)) {
            eb.addField(field);
        }

        Message message = new MessageBuilder()
                .setEmbed(eb.build())
                .build();

        return message;
    }

    private static String getEmbedTitle() {
        return "DayZ Servers Brought To You By Pipsi.net";
    }

    private static String getEmbedDescription() {
        return "-";
    }

    private static String getEmbedUrl() {
        return "https://www.pipsi.net/";
    }

    private static Color getEmbedColor() {
        return COLOR;
    }

    private static TemporalAccessor getEmbedTimestamp() {
        return ZonedDateTime.now();
    }

    private static List<MessageEmbed.Field> getEmbedFields(String[] serverIds) {
        List<MessageEmbed.Field> fields = new ArrayList<>();

        List<Thread> threads = new ArrayList<>();
        for (String serverId : serverIds) {
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

    private static MessageEmbed.Field getField(String id) throws IOException {
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

}
