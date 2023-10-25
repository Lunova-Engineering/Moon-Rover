package com.lunova.moonbot.commands.impl;

import com.lunova.moonbot.annotations.CommandAnnotation;
import com.lunova.moonbot.commands.BotCommand;
import com.lunova.moonbot.user.MoonUser;
import com.lunova.moonbot.user.MoonUserManager;
import com.lunova.moonbot.utility.Utility;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

@CommandAnnotation(name = "moonuser")
public class MoonUserCommand extends BotCommand {

    /**
     * Constructs a new bot command with the given name.
     *
     * @param name the unique name of this bot command.
     */
    public MoonUserCommand(String name) {
        super(name);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        System.out.println("MOON USER INFO\n");
        System.out.printf("User ID: %s \n Member ID: %s \n Moon ID: %s", event.getUser().getId(), event.getGuild().getId(), Utility.getMoonUserId(event.getUser(), event.getGuild()));
        MoonUserManager.MOON_USERS.put(Utility.getMoonUserId(event.getUser(), event.getGuild()), new MoonUser(event.getUser().getId(), event.getGuild().getId()));
        MoonUser user = MoonUserManager.MOON_USERS.get(Utility.getMoonUserId(event.getUser(), event.getGuild()));
        System.out.printf("\n Moon User Info - %s \n User ID: %s \n Guild ID: %s \n Moon ID: %s \n Level: %s \n XP: %s \n Points: %s", event.getJDA().getUserById(user.getUserId()).getName(), user.getUserId(), user.getGuildId(), user.getMoonId(), user.getServerLevel(), user.getServerXp(), user.getServerPoints());
        event.reply("Command Executed!").setEphemeral(true).queue();
    }


    @Override
    public SlashCommandData getRegistryData() {
        return Commands.slash("moonuser", "Generate a moon user for this person - TESTING");
    }

}
