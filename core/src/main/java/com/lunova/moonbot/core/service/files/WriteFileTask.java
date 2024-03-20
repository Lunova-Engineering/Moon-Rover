package com.lunova.moonbot.core.service.files;

import com.lunova.moonbot.core.service.Service;
import com.lunova.moonbot.core.service.tasks.RunnableServiceTask;
import com.lunova.moonbot.core.service.tasks.TaskPriority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteFileTask extends RunnableServiceTask {

    private static final Logger logger = LoggerFactory.getLogger(WriteFileTask.class);

    private final String data;

    public WriteFileTask(TaskPriority taskPriority, Service<?> originator, String data) {
        super(taskPriority, originator);
        this.data = data;
    }

    @Override
    protected void onRun() {
        logger.info(data);
    }

}
