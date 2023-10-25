package com.lunova.moonbot.user;

import com.lunova.moonbot.MoonBot;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MoonUserManager extends ListenerAdapter {

    public static final Map<String, MoonUser> MOON_USERS = new HashMap<>();

    private static final Logger USER_LOGGER = LoggerFactory.getLogger(MoonUserManager.class);

    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    private static final Path USER_PATH = Paths.get("data", "users");


    /**
     * TODO: Create a configuration system for server specific settings that allows for channel configurations
     *
     * @param event
     */
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        MoonUser user = new MoonUser(event.getUser().getId(), event.getGuild().getId());
    }


}
