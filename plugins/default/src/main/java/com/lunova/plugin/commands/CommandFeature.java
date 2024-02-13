package com.lunova.plugin.commands;

import com.lunova.moonbot.core.api.plugin.features.Feature;
import com.lunova.moonbot.core.api.plugin.features.settings.Setting;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingBuilder;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingGroup;
import com.lunova.moonbot.core.api.plugin.features.settings.input.InputType;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

public class CommandFeature extends Feature {

    public CommandFeature(String name) {
        super(name);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);
    }

    @Override
    protected void install() {

    }

    @Override
    protected void uninstall() {

    }

    @Override
    protected SettingGroup defineSettingGroup(SettingGroup.Builder builder) {
        Setting<Role> setting = SettingBuilder.createUserSetting(String.class, Role.class, "key", true, "label", InputType.TEXT_FIELD);
        builder.withSetting(setting);
        return builder.build();
    }

}
