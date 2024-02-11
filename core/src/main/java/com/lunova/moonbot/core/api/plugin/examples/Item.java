package com.lunova.moonbot.core.api.plugin.examples;

import com.lunova.moonbot.core.api.plugin.features.Feature;
import com.lunova.moonbot.core.api.plugin.features.settings.Setting;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingBuilder;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingGroup;
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
/*
        SettingGroup.Builder settingGroup = new SettingGroup.Builder();

        InputDefinition definition = new InputDefinition("cooldown", DataType.INTEGER, InputSelectionType.TEXT_FIELD)
                .withCustomValidation().withTransformation();

        InputDefinition definition2 = new InputDefinition("permissions", DataType.BOOLEAN, InputSelectionType.TEXT_FIELD);

        Map<String, InputDefinition> defs = new HashMap<>();
        MoonBotService.getInstance().getBotSession().getGuilds().getFirst().getRoles().forEach(role -> {
            defs.put(role.getName(), new InputDefinition(role.getName(), DataType.INTEGER, InputSelectionType.DROP_DOWN));
        });

        OptionDefinition optionDefinition = new OptionDefinition("1", definition);
        optionDefinition.withAdditionalPairs(defs);

        //Add withAdditionalInput for multiple input definitions
        //Add withOption for supplying an option that changes the input definition displayed
        Setting setting = new Setting.Builder("name", true, definition).withOption(optionDefinition).build();
        Setting setting2 = new Setting.Builder("new_setting", false, definition2).build();
        settingGroup.withSetting(setting).withSetting(setting2);
        return settingGroup.build();*/

        SettingGroup.Builder builder = new SettingGroup.Builder();
/*        Setting setting = new Setting.Builder("Basic Setting", true,
                new SelectionSettingDefinition<TextFieldInputDefinition<Role>>("New Role",
                        new TextFieldInputDefinition<Role>(InputType.TEXT_FIELD)
                                .withTransformation(new Transformation<String, Role>() {
                            @Override
                            public Role transform(String object) {
                                //Logic to transform String into a Role
                                return null;
                            }
                        }).withValidation()  //withValidation isn't implemented yet, but it would look similar to withTransformation which is not good
                    )).build();*/

        Setting<Role> setting = SettingBuilder.createSelectionSetting(String.class, Role.class, "keY", true, "labl", new ArrayList<>());

        builder.withSetting(setting);



        return builder.build();
    }

}
