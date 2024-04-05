package com.lunova.moonbot.core.service.files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FormatCodec {

    private static final Logger logger = LoggerFactory.getLogger(FormatCodec.class);

    public abstract void encode();
    public abstract void decode();

}
