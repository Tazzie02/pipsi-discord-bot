package net.pipsi.java.pipsidiscordbot.commands;

import com.tazzie02.tazbotdiscordlib.Command;
import com.tazzie02.tazbotdiscordlib.SendMessage;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.pipsi.java.pipsidiscordbot.ServersEmbed;

import java.util.Arrays;
import java.util.List;

public class ServersCommand implements Command {

    private final String[] serverIds;

    public ServersCommand(String[] serverIds) {
        this.serverIds = serverIds;
    }

    @Override
    public void onCommand(MessageReceivedEvent e, String[] args) {
        Message message = ServersEmbed.build(serverIds);
        SendMessage.sendMessage(e, message);
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
