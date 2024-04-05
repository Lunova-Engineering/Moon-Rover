package com.lunova.moonbot.core.service.files.tasks;

import com.lunova.moonbot.core.service.Service;
import com.lunova.moonbot.core.service.files.FileFormat;
import com.lunova.moonbot.core.service.tasks.CallableServiceTask;
import com.lunova.moonbot.core.service.tasks.TaskPriority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class ReadFileTask<T> extends CallableServiceTask<T> {

    private static final Logger logger = LoggerFactory.getLogger(ReadFileTask.class);

    private final Path path;

    private final FileFormat format;

    private final Class<T> returnType;


    public ReadFileTask(TaskPriority taskPriority, Service<?> originator, Path path, FileFormat format, Class<T> returnType) {
        super(taskPriority, originator);
        this.path = path;
        this.format = format;
        this.returnType = returnType;
    }

    @Override
    protected T onCall() throws Exception {
        return switch (format) {
            case TXT, JSON :
                yield format.getFormatStrategy().readData(path, returnType);
            default:
                throw new UnsupportedOperationException("Unsupported file format supplied");
        };
    }

    public Path getPath() {
        return path;
    }

    public FileFormat getFormat() {
        return format;
    }

    public Class<T> getReturnType() {
        return returnType;
    }

}
