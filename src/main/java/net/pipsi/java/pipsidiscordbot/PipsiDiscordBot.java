package net.pipsi.java.pipsidiscordbot;

import com.tazzie02.tazbotdiscordlib.Command;
import net.dv8tion.jda.core.entities.TextChannel;
import net.pipsi.java.pipsidiscordbot.CommandLineOptions.CommandLineParsed;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.pipsi.java.pipsidiscordbot.commands.ServersCommand;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.nio.file.Paths;

public class PipsiDiscordBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(PipsiDiscordBot.class);

    public static void main(String[] args) {
        CommandLineOptions options = new CommandLineOptions();
        try {
            CommandLineParsed parsed = options.parse(args);
            LOGGER.info("Bot token set to {}", parsed.getToken());
            LOGGER.info("Servers set to {}", (Object) parsed.getServers());
            if (parsed.getChannelId() == null) {
                LOGGER.info("Channel ID not set");
            }
            else {
                LOGGER.info("Channel ID set to {}", parsed.getChannelId());
            }
            if (parsed.getOutputRate() == 0) {
                LOGGER.info("Output Rate not set");
            }
            else {
                LOGGER.info("Output Rate set to {}", parsed.getOutputRate());
            }

            Command[] commands = {new ServersCommand(parsed.getServers())};

            TDLBot tdlBot = new TDLBot(parsed.getToken(), Paths.get(""), commands);
            JDA jda = tdlBot.getJDA();

            // Start the automatic Output Refresher
            startServersOutputRefresher(jda, parsed.getServers(), parsed.getChannelId(), parsed.getOutputRate());
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
            options.printHelp();
//            e.printStackTrace();
        } catch (LoginException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RateLimitedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void startServersOutputRefresher(JDA jda, String[] serverIds, String channelId, int outputRate) {
        if (channelId == null || outputRate == 0) {
            LOGGER.info("Not using automatic server output as channel and outputRate must both be set");
            return;
        }

        TextChannel channel = jda.getTextChannelById(channelId);
        if (channel == null) {
            LOGGER.error("Could not find TextChannel with the ID of Channel");
            return;
        }

        int outputRateSeconds = outputRate * 60;

        ServersOutputRefresher outputRefresher = new ServersOutputRefresher(jda, serverIds, channel, outputRateSeconds);
        outputRefresher.start();
    }

}
