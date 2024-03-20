package com.lunova.moonbot.core.service.files;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lunova.moonbot.core.service.executors.DefaultUncaughtExceptionHandler;
import com.lunova.moonbot.core.service.Service;
import com.lunova.moonbot.core.service.executors.ServiceExecutor;
import com.lunova.moonbot.core.service.ServiceInfo;
import com.lunova.moonbot.core.service.tasks.TaskPriority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@ServiceInfo(name = "File Service", critical = false)
public class FileService extends Service<ServiceExecutor> {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    public FileService(String name, boolean critical) {
        super(name, critical);
    }


    public void writeFile(String data) {
        execute(new WriteFileTask(TaskPriority.NORMAL, this, data));
    }


    @Override
    protected ServiceExecutor createExecutor() {
        ThreadFactory f = new ThreadFactoryBuilder().setNameFormat(getName().replace(" ", "-"))
                .setDaemon(false)
                .setPriority(Thread.NORM_PRIORITY)
                .setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler())
                .build();
        return new ServiceExecutor(1, 1, 5, TimeUnit.MINUTES, new PriorityBlockingQueue<Runnable>(10), f);
    }


}
