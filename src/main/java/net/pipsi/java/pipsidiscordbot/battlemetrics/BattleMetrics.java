package net.pipsi.java.pipsidiscordbot.battlemetrics;

import net.pipsi.java.pipsidiscordbot.util.WebPage;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class BattleMetrics {

    private static final Logger LOGGER = LoggerFactory.getLogger(BattleMetrics.class);

    private static final String URL_BASE = "https://api.battlemetrics.com/servers/";
    private final JSONObject json;
    private final JSONObject jsonAttributes;

    public BattleMetrics(String serverId) throws IOException, JSONException {
        json = getServerInfo(serverId);
        jsonAttributes = json.getJSONObject("data").getJSONObject("attributes");
    }

    private JSONObject getServerInfo(String serverId) throws IOException, JSONException {
        String url = URL_BASE + serverId;
        LOGGER.debug("Querying BattleMetrics API at " + url);

        WebPage page = new WebPage(url);

        // Validate page response code
        if (page.getResponseCode() != 200) {
            IOException e = new IOException("BattleMetrics API returned invalid response code " + page.getResponseCode());
            LOGGER.error("", e);
        }

        JSONObject json = new JSONObject(page.getBody());

        // Validate JSON
        if (!json.has("data")) {
            JSONException e = new JSONException("BattleMetrics API did not return a valid JSON response");
            LOGGER.error(page.getBody(), e);
        }

        return json;
    }

    public String getId() {
        return jsonAttributes.getString("id");
    }

    public String getName() {
        return jsonAttributes.getString("name");
    }

    public String getIp() {
        return jsonAttributes.getString("ip");
    }

    public int getPort() {
        return jsonAttributes.getInt("port");
    }

    public int getPlayers() {
        return jsonAttributes.getInt("players");
    }

    public int getMaxPlayers() {
        return jsonAttributes.getInt("maxPlayers");
    }

    public int getRank() {
        return jsonAttributes.getInt("rank");
    }

    public String getStatus() {
        return jsonAttributes.getString("status");
    }

}
