package com.lunova.moonbot.core.service.executors;

import com.lunova.moonbot.core.service.tasks.ServiceTask;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

public interface PausableExecutor extends ExecutorService {

    void pause();

    void resume();

    boolean isPaused();

    BlockingQueue<ServiceTask> getTaskQueue();
}
