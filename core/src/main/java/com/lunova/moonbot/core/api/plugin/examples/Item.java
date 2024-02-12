package com.lunova.moonbot.core.api.plugin.examples;

import com.lunova.moonbot.core.api.plugin.features.Feature;
import com.lunova.moonbot.core.api.plugin.features.settings.Setting;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingBuilder;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingGroup;
import com.lunova.moonbot.core.api.plugin.features.settings.definitions.input.InputType;
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
    public SettingGroup defineSettingGroup() {
        SettingGroup.Builder builder = new SettingGroup.Builder();
        Setting<Role> setting = SettingBuilder.createSelectionSetting(String.class, Role.class, "keY", true, "label", InputType.DROP_DOWN, new ArrayList<>());

        builder.withSetting(setting);
        return builder.build();
    }

}
