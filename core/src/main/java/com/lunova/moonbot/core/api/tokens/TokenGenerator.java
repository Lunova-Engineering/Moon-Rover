package com.lunova.moonbot.core.api.tokens;

import java.util.UUID;

public class TokenGenerator {

    public static UUID generateToken() {
        return UUID.randomUUID();
    }

}
