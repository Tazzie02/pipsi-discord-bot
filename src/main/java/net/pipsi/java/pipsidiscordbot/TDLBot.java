package net.pipsi.java.pipsidiscordbot;

import com.tazzie02.tazbotdiscordlib.Command;
import com.tazzie02.tazbotdiscordlib.CommandRegistry;
import com.tazzie02.tazbotdiscordlib.TazbotDiscordLib;
import com.tazzie02.tazbotdiscordlib.TazbotDiscordLibBuilder;
import com.tazzie02.tazbotdiscordlib.commands.HelpCommand;
import com.tazzie02.tazbotdiscordlib.commands.PingCommand;
import com.tazzie02.tazbotdiscordlib.commands.ShutdownCommand;
import com.tazzie02.tazbotdiscordlib.filehandling.LocalFiles;
import com.tazzie02.tazbotdiscordlib.impl.MessageSenderImpl;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.pipsi.java.pipsidiscordbot.commands.ServersCommand;

import javax.security.auth.login.LoginException;
import java.nio.file.Path;

public class TDLBot {

    private final TazbotDiscordLib tdl;

    public TDLBot(String token, Path configPath, Command[] commands) throws LoginException, IllegalArgumentException, InterruptedException, RateLimitedException {
        TazbotDiscordLibBuilder builder = new TazbotDiscordLibBuilder(token);
        // Set the location files will be stored
        builder.setFilePath(configPath);

        this.tdl = builder.build();

        // Create the default MessageSender
        MessageSenderImpl sender = new MessageSenderImpl();
        tdl.setMessageSender(sender);

        LocalFiles filesInstance = LocalFiles.getInstance(tdl.getJDA());

        // Create a CommandRegistry to manage Commands
        CommandRegistry registry = new CommandRegistry();

        registry.setCaseSensitiveCommands(false);
        // Set the owners as per the config file
        registry.setOwners(filesInstance.getConfig());
        // Set the CommandSettings for all
        registry.setDefaultCommandSettings(filesInstance);

        registerCommands(registry, commands);

        // Add the CommandRegistry to the TazbotDiscordLib object
        tdl.addListener(registry);
    }

    private void registerCommands(CommandRegistry registry, Command[] commands) {
        // Commands provided by TDL
        registry.registerCommand(new HelpCommand(registry));
        registry.registerCommand(new PingCommand());
        registry.registerCommand(new ShutdownCommand());

        for (Command command : commands) {
            registry.registerCommand(command);
        }
    }

    public JDA getJDA() {
        return tdl.getJDA();
    }

}
