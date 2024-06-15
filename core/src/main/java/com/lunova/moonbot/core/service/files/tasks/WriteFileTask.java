package com.lunova.moonbot.core.service.files.tasks;

import com.lunova.moonbot.core.service.Service;
import com.lunova.moonbot.core.service.files.FileFormat;
import com.lunova.moonbot.core.service.files.FileOptions;
import com.lunova.moonbot.core.service.tasks.RunnableServiceTask;
import com.lunova.moonbot.core.service.tasks.TaskPriority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class WriteFileTask<T> extends RunnableServiceTask {

    private static final Logger logger = LoggerFactory.getLogger(WriteFileTask.class);

    private final FileFormat format;

    private final Path path;

    private final T data;

    private final FileOptions options;

    public WriteFileTask(
            TaskPriority taskPriority,
            Service<?> originator,
            FileFormat format,
            Path path,
            T data,
            FileOptions options) {
        super(taskPriority, originator);
        this.format = format;
        this.path = path;
        this.data = data;
        this.options = options;
    }

    public FileFormat getFormat() {
        return format;
    }

    public Path getPath() {
        return path;
    }

    public T getData() {
        return data;
    }

    public FileOptions getOptions() {
        return options;
    }

    @Override
    protected void onRun() {
        switch (format) {
            case TXT:
            case JSON:
                getFormat().getFormatStrategy().writeData(path, data, options);
                break;

            default:
                throw new UnsupportedOperationException(
                        "Format not supported for file operations.");
        }
        // logger.info("PRIORITY: {} - ORIGINATOR {} - DATA: {}", getTaskPriority().name(),
        // getOriginator().getName(), data);
    }
}
