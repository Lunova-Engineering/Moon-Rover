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

    /**
     * Sets up mocked instances and initial configurations before each test case.
     */
    @BeforeEach
    public void setup() {
        pingCommand = new Ping("ping");

        // Mock the SlashCommandInteractionEvent
        mockEvent = Mockito.mock(SlashCommandInteractionEvent.class);
        mockReplyCallbackAction = Mockito.mock(ReplyCallbackAction.class);
        when(mockEvent.reply("pong")).thenReturn(mockReplyCallbackAction);
    }

    /**
     * Test to verify that executing the Ping command responds with "pong".
     * <p>
     * This test ensures that when the {@code execute} method of the {@code Ping} class is called,
     * the expected "pong" message is returned as a response.
     * </p>
     */
    @Test
    public void testExecute() {
        pingCommand.execute(mockEvent);

        // Verify that the reply method was called with the message "pong"
        verify(mockEvent, times(1)).reply("pong");

        // Also verify that the queue method was called on the mockReplyCallbackAction
        verify(mockReplyCallbackAction, times(1)).queue();
    }

    /**
     * Test to ensure the command's registry data is correctly set.
     * <p>
     * This test checks if the command's name and description in the registry data match the expected values.
     * </p>
     */
    @Test
    public void testGetRegistryData() {
        SlashCommandData data = pingCommand.getRegistryData();

        assertEquals("ping", data.getName());
        assertEquals("Ping Moon-Bot and get a reply!", data.getDescription());
    }
}
