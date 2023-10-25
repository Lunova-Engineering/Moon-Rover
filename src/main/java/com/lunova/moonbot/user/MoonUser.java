package com.lunova.moonbot.user;

import com.lunova.moonbot.utility.Utility;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @project Moon-Bot
 * @since 10.12.2023
 */
public class MoonUser {

    private final String userId;

    private final String guildId;

    private final String moonId;

    private int serverLevel;

    private double serverXp;

    private int serverPoints;

    public MoonUser(String userId, String guildId) {
        this(userId, guildId, 1, 0, 0);
    }

    public MoonUser(String userId, String guildId, int serverLevel, double serverXp, int serverPoints) {
        this.userId = userId;
        this.guildId = guildId;
        this.moonId = Utility.getMoonUserId(userId, guildId);
        this.serverLevel = serverLevel;
        this.serverXp = serverXp;
        this.serverPoints = serverPoints;
    }


    public String getUserId() {
        return userId;
    }

    public String getGuildId() {
        return guildId;
    }

    public String getMoonId() {
        return moonId;
    }

    public int getServerLevel() {
        return serverLevel;
    }

    public void setServerLevel(int serverLevel) {
        this.serverLevel = serverLevel;
    }

    public double getServerXp() {
        return serverXp;
    }

    public void setServerXp(double serverXp) {
        this.serverXp = serverXp;
    }

    public int getServerPoints() {
        return serverPoints;
    }

    public void setServerPoints(int serverPoints) {
        this.serverPoints = serverPoints;
    }

}
