package com.lunova.moonbot.core.api.plugin.examples;

import com.lunova.moonbot.core.api.plugin.features.Feature;
import com.lunova.moonbot.core.api.plugin.features.settings.Setting;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingCreator;
import com.lunova.moonbot.core.api.plugin.features.settings.SettingGroup;
import com.lunova.moonbot.core.api.plugin.features.settings.input.InputType;
import com.lunova.moonbot.core.api.plugin.features.settings.transformation.Transformation;
import com.lunova.moonbot.core.api.plugin.features.settings.validation.NumberValidator;
import com.lunova.moonbot.core.api.plugin.features.settings.validation.StringValidator;
import com.lunova.moonbot.core.services.bot.MoonBotService;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.GenericEvent;
import org.jetbrains.annotations.NotNull;

public class Item extends Feature {

    public Item(String name) {
        super(name);
    }

    @Override
    public void onGenericEvent(@NotNull GenericEvent event) {
        event.getJDA().shutdown();
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
                .withTransformation(new Transformation<>() {
                    @Override
                    public Role transform(String object) {
                        return MoonBotService.getInstance().getBotSession().getGuilds().getFirst().getRolesByName(object, true).getFirst();
                    }

                    @Override
                    public void processTransformation(Role object) {
                        System.out.println(object.getName() + " role has been processed after transformation!");
                    }
                }).withValidation(new StringValidator.Builder().setMinimumLength(5).setMaximumLength(10).setRangeLength(5, 10).setRegex("^[a-zA-Z0-9]+$").build()).build();


        Setting<Role> setting2 = SettingCreator.createToggleSetting(Boolean.class, Role.class, "choose_permission", true, "Select Role: ", InputType.DROP_DOWN)
                .withTransformation(new Transformation<>() {
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
                .withValidation(new NumberValidator.Builder<Double>().build()).build();

        builder.withSetting(setting, setting2);

        return builder.build();
    }

}
