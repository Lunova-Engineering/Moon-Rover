package com.lunova.moonbot.core.api.plugin.examples;

import com.lunova.moonbot.core.api.plugin.features.Feature;
import com.lunova.moonbot.core.api.plugin.features.settings.Setting;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingBuilder;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingGroup;
import com.lunova.moonbot.core.api.plugin.features.settings.input.InputType;
import com.lunova.moonbot.core.services.bot.MoonBotService;
import net.dv8tion.jda.api.entities.Role;

import java.util.ArrayList;

public class Item extends Feature {

    public Item(String name) {
        super(name);
    }

    @Override
    protected void install() {

    }

    @Override
    protected void uninstall() {

    }

    @Override
    public SettingGroup defineSettingGroup(SettingGroup.Builder builder) {
        Setting<Role> setting = SettingBuilder.createSelectionSetting(String.class, Role.class, "keY", true, "label", InputType.DROP_DOWN, new ArrayList<>())
                .withTransformation(object -> MoonBotService.getInstance().getBotSession().getRoleById(0)).getSetting();
        Setting<Role> setting2 = SettingBuilder.createUserSetting(String.class, Role.class, "key", true, "label2", InputType.DATE);
        Setting<Role> setting3 = SettingBuilder.createToggleSetting(Boolean.class, Role.class, "key", true, "label", InputType.TOGGLE_SWITCH);
        Setting<Role> setting4 = SettingBuilder.createRangeSetting(Number.class, Role.class, "key", true, "label", InputType.RANGE_SLIDER, 4, 5, 6);
        builder.withSetting(setting, setting2, setting3, setting4);
        return builder.build();
    }

}
