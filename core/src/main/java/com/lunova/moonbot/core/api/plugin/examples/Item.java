package com.lunova.moonbot.core.api.plugin.examples;

import com.lunova.moonbot.core.api.plugin.features.Feature;
import com.lunova.moonbot.core.api.plugin.features.configuration.*;
import com.lunova.moonbot.core.services.bot.MoonBotService;

import java.util.HashMap;
import java.util.Map;

public class Item extends Feature {


    @Override
    protected void install() {

    }

    @Override
    protected void uninstall() {

    }

    @Override
    public SettingGroup defineSettingGroup() {

        SettingGroup.Builder settingGroup = new SettingGroup.Builder();

        InputDefinition definition = new InputDefinition("cooldown", InputDefinitionType.INTEGER, InputRenderType.TEXT_FIELD)
                .withCustomValidation().withTransformation();

        InputDefinition definition2 = new InputDefinition("permissions", InputDefinitionType.BOOLEAN, InputRenderType.TEXT_FIELD);

        Map<String, InputDefinition> defs = new HashMap<>();
        MoonBotService.getInstance().getBotSession().getGuilds().getFirst().getRoles().forEach(role -> {
            defs.put(role.getName(), new InputDefinition(role.getName(), InputDefinitionType.INTEGER, InputRenderType.DROP_DOWN));
        });

        OptionDefinition optionDefinition = new OptionDefinition("1", definition);
        optionDefinition.withAdditionalPairs(defs);

        //Add withAdditionalInput for multiple input definitions
        //Add withOption for supplying an option that changes the input definition displayed
        Setting setting = new Setting.Builder("name", true, definition).build();
        Setting setting2 = new Setting.Builder("new_setting", false, definition2).build();
        settingGroup.withSetting(setting).withSetting(setting2);
        return settingGroup.build();
    }

}
