package com.lunova.moonbot.core.service.files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public abstract class FormatStrategy {

    private static final Logger logger = LoggerFactory.getLogger(FormatStrategy.class);

    private final FormatCodec codec;

    public FormatStrategy(FormatCodec codec) {
        this.codec = codec;
    }

    public FormatCodec getCodec() {
        return codec;
    }

    public abstract <T> void writeData(Path path, T data, FileOptions options);

    public abstract <T> T readData(Path path, Class<T> returnType, FileOptions options);
}
