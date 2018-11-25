[![Build Status](https://travis-ci.org/Tazzie02/pipsi-discord-bot.svg?branch=master)](https://travis-ci.org/Tazzie02/pipsi-discord-bot)

## Pipsi Discord Bot
Pipsi Discord Bot is a Discord Bot designed by Tazzie02 for Pipsi.net.

The bot is used to output information pulled from servers on BattleMetrics.com into the Discord application. The information can be output on command and optionally by a specified time.

![Example Output](https://i.imgur.com/aBkyIru.png)

## Command Line Arguments
|Long|Short|Required|Description|
|---|---|---|---|
|token|t|yes|Discord bot token acquired from [here](https://discordapp.com/developers/applications/).|
|servers|s|yes|One or more Server IDs from [BattleMetrics](https://battlemetrics.com).|
|channel|c|no|Discord text channel ID to automatically output information a rate specified by the outputRate argument. Ignored unless used with outputRate argument.|
|outputRate|r|no|Rate in minutes to automatically output information to the Discord channel specified by the channel argument. Ignored unless used with channel argument.|

## Examples
**Note:** The name of the file should be changed as necessary.

Single server ID
```sh
java -jar PipsiDiscordBot-0.1.0_1-all.jar \
--token [BOT_TOKEN] \
--servers 2693802
```

Multiple server IDs
```sh
java -jar PipsiDiscordBot-0.1.0_1-all.jar \
--token [BOT_TOKEN] \
--servers 2693802 2797943 2693805
```

Output to a channel every 30 minutes
```sh
java -jar PipsiDiscordBot-0.1.0_1-all.jar \
--token [BOT_TOKEN] \
--servers 2693802 2797943 2693805 \
--channel 141092236052922369 \
--outputRate 30
```

