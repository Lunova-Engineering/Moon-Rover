package com.lunova.moonbot.core.servold.files;

import com.lunova.moonbot.core.exceptions.ServiceLoadingException;
import com.lunova.moonbot.core.servold.BotService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileService extends BotService {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private static final FileService INSTANCE = new FileService("File Writing Service");

    public static FileService getInstance() {
        return INSTANCE;
    }


    public FileService(String serviceName) {
        super(serviceName);
    }

    @Override
    protected void initialize() throws ServiceLoadingException {
        LOGGER.info("Initialized File Writing Service!");
    }

    public void writeFile(String data, Path path) {
        File file = path.toFile();
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {

            }
        }
        try (FileWriter writer = new FileWriter(file)){
            writer.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
