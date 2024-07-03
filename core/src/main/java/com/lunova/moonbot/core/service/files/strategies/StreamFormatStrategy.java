package com.lunova.moonbot.core.service.files.strategies;

import com.lunova.moonbot.core.service.files.FileOptions;
import com.lunova.moonbot.core.service.files.FormatStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class StreamFormatStrategy extends FormatStrategy {

    private static final Logger logger = LoggerFactory.getLogger(StreamFormatStrategy.class);

    public StreamFormatStrategy() {
        super(null);
    }

    @Override
    public <T> void writeData(Path path, T data, FileOptions options) {}

    @Override
    public <T> T readData(Path path, Class<T> returnType, FileOptions options) {
        return null;
    }
}
