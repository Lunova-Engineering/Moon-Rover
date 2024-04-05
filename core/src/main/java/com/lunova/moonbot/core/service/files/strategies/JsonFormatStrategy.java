package com.lunova.moonbot.core.service.files.strategies;

import com.lunova.moonbot.core.exceptions.JsonDeserializationException;
import com.lunova.moonbot.core.service.Person;
import com.lunova.moonbot.core.service.files.FormatStrategy;
import com.lunova.moonbot.core.service.files.codecs.JsonFormatCodec;
import com.lunova.moonbot.core.service.files.json.JsonHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class JsonFormatStrategy extends FormatStrategy {

    private static final Logger logger = LoggerFactory.getLogger(JsonFormatStrategy.class);

    public JsonFormatStrategy() {
        super(new JsonFormatCodec());
    }

    @Override
    public void writeData(Path path, Object data) {
        try {
            data = JsonHandler.serialize(data);

            if (path.getParent() != null && !Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            // Write data to file in append mode with specified charset
            try (BufferedWriter out = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                out.write(data.toString());
                out.newLine();
            }
        } catch (Exception e) {
            logger.error("Error writing to file: {}", path, e);
        }
    }

    @Override
    public <T> T readData(Path path, Class<T> returnType) {
        try {
            if (!Files.exists(path)) {
                throw new IOException("File does not exist.");
            }
            // Read the entire file content into a String
            String content = Files.readString(path);
            Person person = (Person) JsonHandler.deserialize(content, returnType);
            return JsonHandler.deserialize(content, returnType);
        } catch (IOException | JsonDeserializationException e) {
            logger.warn("Error deserializing - {}", path.toString(), e);
            throw new RuntimeException(e);
        }
    }


}
