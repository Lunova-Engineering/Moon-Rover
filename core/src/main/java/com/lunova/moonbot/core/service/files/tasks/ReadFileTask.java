package com.lunova.moonbot.core.service.files.tasks;

import com.lunova.moonbot.core.service.Service;
import com.lunova.moonbot.core.service.files.FileFormat;
import com.lunova.moonbot.core.service.files.FileOptions;
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

    private final FileOptions options;

    public ReadFileTask(
            TaskPriority taskPriority,
            Service<?> originator,
            Path path,
            FileFormat format,
            Class<T> returnType,
            FileOptions options) {
        super(taskPriority, originator);
        this.path = path;
        this.format = format;
        this.returnType = returnType;
        this.options = options;
    }

    @Override
    protected T onCall() throws Exception {
        return switch (format) {
            case TXT, JSON:
                yield format.getFormatStrategy().readData(path, returnType, options);
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

    public FileOptions getOptions() {
        return options;
    }
}
