package com.lunova.moonbot.commands.impl;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PingTest {

    private Ping pingCommand;
    private SlashCommandInteractionEvent mockEvent;
    private ReplyCallbackAction mockReplyCallbackAction;

    @BeforeEach
    public void setup() {
        pingCommand = new Ping("ping");

        // Mock the SlashCommandInteractionEvent
        mockEvent = Mockito.mock(SlashCommandInteractionEvent.class);
        mockReplyCallbackAction = Mockito.mock(ReplyCallbackAction.class);
        when(mockEvent.reply("pong")).thenReturn(mockReplyCallbackAction);
    }

    @Test
    public void testExecute() {
        pingCommand.execute(mockEvent);

        // Verify that the reply method was called with the message "pong"
        verify(mockEvent, times(1)).reply("pong");

        // Also verify that the queue method was called on the mockReplyCallbackAction
        verify(mockReplyCallbackAction, times(1)).queue();
    }

    @Test
    public void testGetRegistryData() {
        SlashCommandData data = pingCommand.getRegistryData();

        assertEquals("ping", data.getName());
        assertEquals("Ping Moon-Bot and get a reply!", data.getDescription());
    }
}
