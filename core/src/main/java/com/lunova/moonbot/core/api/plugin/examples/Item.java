package com.lunova.moonbot.core.api.plugin.examples;

import com.lunova.moonbot.core.api.plugin.features.Feature;
import com.lunova.moonbot.core.api.plugin.features.settings.Setting;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingCreator;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingGroup;
import com.lunova.moonbot.core.api.plugin.features.settings.input.InputType;
import com.lunova.moonbot.core.services.bot.MoonBotService;
import net.dv8tion.jda.api.entities.Role;

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
        Setting<Role> setting = SettingCreator.createSelectionSetting(String.class, Role.class, "choose_permission", true, "Select Role: ", InputType.DROP_DOWN, MoonBotService.getInstance().getBotSession().getGuilds().getFirst().getRoles().stream().map(Role::getName).toList())
                .withTransformation(object -> MoonBotService.getInstance().getBotSession().getGuilds().getFirst().getRolesByName(object, true).getFirst()).build();
        //SettingCreator.testFoo(new TypeToken<String>() {}, new TypeToken<Role>() {});

        builder.withSetting(setting);

        return builder.build();
    }

}
