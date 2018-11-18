package net.pipsi.java.pipsidiscordbot;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.nio.file.Paths;

public class PipsiDiscordBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(PipsiDiscordBot.class);

    public static void main(String[] args) {
        if (args.length != 1) {
            LOGGER.error("Bot token must be set as the only argument when executing the application");
            return;
        }

        try {
            TDLBot tdlBot = new TDLBot(args[0], Paths.get(""));
            JDA jda = tdlBot.getJDA();
        }
        catch (LoginException e) {
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

}
