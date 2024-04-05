package com.lunova.moonbot.core.service.files.strategies;

import com.lunova.moonbot.core.service.files.FormatStrategy;
import com.lunova.moonbot.core.service.files.codecs.TextFormatCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class TextFormatStrategy extends FormatStrategy {

    private static final Logger logger = LoggerFactory.getLogger(TextFormatStrategy.class);

    public TextFormatStrategy() {
        super(new TextFormatCodec());
    }

    @Override
    public void writeData(Path path, Object data) {
        try {
            if (path.getParent() != null && !Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }

            // Write data to file in append mode with specified charset
            try (BufferedWriter out = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                out.write(data.toString());
            }
        } catch (Exception e) {
            logger.error("Error writing to file: {}", path, e);
        }
    }

    @Override
    public <T> T readData(Path path, Class<T> returnType) {
        try {
            return (T) Files.readString(path);
        } catch (IOException e) {
            logger.info("Error reading file", e);
            throw new RuntimeException(e);
        }
    }

}
