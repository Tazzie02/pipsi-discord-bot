package net.pipsi.java.pipsidiscordbot;

import org.apache.commons.cli.*;

public class CommandLineOptions {

    private final Options options;
    private final String helpArgName = "help";
    private final String tokenArgName = "token";
    private final String serversArgName = "servers";
    private final String channelArgName = "channel";
    private final String outputRateArgName = "outputRate";

    public CommandLineOptions() {
        this.options = createOptions();
    }

    public CommandLineParsed parse(String[] args) throws ParseException {
        return new CommandLineParsed(options, args);
    }

    private Options createOptions() {
        Options options = new Options();

        Option help = Option.builder("h")
                .longOpt(helpArgName)
                .desc("print this message")
                .build();
        Option token = Option.builder("t")
                .longOpt(tokenArgName)
                .desc("discord bot token (required)")
                .hasArg()
                .argName(tokenArgName)
                .required()
                .build();
        Option servers = Option.builder("s")
                .longOpt(serversArgName)
                .desc("servers ids on BattleMetrics to output (required)")
                .hasArgs()
                .argName(serversArgName)
                .required()
                .build();
        Option channel = Option.builder("c")
                .longOpt(channelArgName)
                .desc("channel id for automatic output")
                .hasArg()
                .argName(channelArgName)
                .build();
        Option outputRate = Option.builder("r")
                .longOpt(outputRateArgName)
                .desc("output rate in minutes for automatic output into the specified channel")
                .hasArg()
                .argName(outputRateArgName)
                .type(Integer.class)
                .build();

        options.addOption(help);
        options.addOption(token);
        options.addOption(servers);
        options.addOption(channel);
        options.addOption(outputRate);

        return options;
    }

    public void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(helpArgName, options);
    }

    public class CommandLineParsed {

        private final CommandLine commandLine;

        public CommandLineParsed(Options options, String[] args) throws ParseException {
            CommandLineParser parser = new DefaultParser();
            this.commandLine = parser.parse(options, args);
        }

        public String getToken() {
            return this.commandLine.getOptionValue(tokenArgName);
        }

        public String[] getServers() {
            return this.commandLine.getOptionValues(serversArgName);
        }

        public String getChannelId() {
            return this.commandLine.getOptionValue(channelArgName);
        }

        public int getOutputRate() throws ParseException{
            try {
                return Integer.parseInt(this.commandLine.getOptionValue(outputRateArgName, "0"));
            }
            catch (NumberFormatException e) {
                throw new ParseException("Output Rate must be an Integer");
            }
        }

    }
}
