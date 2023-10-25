package com.lunova.moonbot.utility;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.Event;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utility {

    public static String getMoonUserId(User user, Guild guild) {
        return getMoonUserId(user.getId(), guild.getId());
    }

    public static String getMoonUserId(String userId, String guildId) {
        try {
            String combined = userId + "_" + guildId;
            MessageDigest messageDigester = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = messageDigester.digest(combined.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to hash ID", e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
