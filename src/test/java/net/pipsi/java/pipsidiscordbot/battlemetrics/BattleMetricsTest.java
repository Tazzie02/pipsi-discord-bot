package net.pipsi.java.pipsidiscordbot.battlemetrics;

import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BattleMetricsTest {

    @Test
    public void apiTest() {
        final String serverId = "2693802";
        try {
            BattleMetrics battleMetrics = new BattleMetrics(serverId);
            assertEquals(serverId, battleMetrics.getId());
            assertNotNull(battleMetrics.getName());
            assertNotNull(battleMetrics.getIp());
            assertNotNull(battleMetrics.getPort());
            assertNotNull(battleMetrics.getPlayers());
            assertNotNull(battleMetrics.getMaxPlayers());
            assertNotNull(battleMetrics.getRank());
            assertNotNull(battleMetrics.getStatus());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
