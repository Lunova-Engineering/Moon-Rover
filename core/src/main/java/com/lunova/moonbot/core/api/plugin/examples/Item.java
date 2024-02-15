package com.lunova.moonbot.core.api.plugin.examples;

import com.lunova.moonbot.core.api.plugin.features.Feature;
import com.lunova.moonbot.core.api.plugin.features.settings.Setting;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingCreator;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingGroup;
import com.lunova.moonbot.core.api.plugin.features.settings.input.InputType;
import com.lunova.moonbot.core.api.plugin.features.settings.transformation.Transformation;
import com.lunova.moonbot.core.api.plugin.features.settings.validation.NumberValidation;
import com.lunova.moonbot.core.api.plugin.features.settings.validation.StringValidation;
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
                .withTransformation(new Transformation<String, Role>() {
                    @Override
                    public Role transform(String object) {
                        return MoonBotService.getInstance().getBotSession().getGuilds().getFirst().getRolesByName(object, true).getFirst();
                    }

                    @Override
                    public void processTransformation(Role object) {
                        System.out.println(object.getName() + " role has been processed after transformation!");
                    }
                }).withValidation(new StringValidation.Builder().setMinimumLength(5).build()).build();


        Setting<Role> setting2 = SettingCreator.createToggleSetting(Boolean.class, Role.class, "choose_permission", true, "Select Role: ", InputType.DROP_DOWN)
                .withTransformation(new Transformation<Boolean, Role>() {
                    @Override
                    public Role transform(Boolean object) {
                        return object ? MoonBotService.getInstance().getBotSession().getGuilds().getFirst().getRoles().getFirst() : MoonBotService.getInstance().getBotSession().getGuilds().getFirst().getRoles().getLast();
                    }

                    @Override
                    public void processTransformation(Role object) {
                        System.out.println("Successfully processed role " + object.getName() + " for boolean transformation.");
                    }
                }).build();

        Setting<Double> setting3 = SettingCreator.createUserSetting(Double.class, Double.class, "", true, "", InputType.NUMBER_INPUT)
                .withValidation(new NumberValidation.Builder<Double>().build()).build();
        //SettingCreator.testFoo(new TypeToken<String>() {}, new TypeToken<Role>() {});

        builder.withSetting(setting, setting2);

        return builder.build();
    }

}
