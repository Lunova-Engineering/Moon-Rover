package com.lunova.moonbot.core.service.executors;

import java.util.concurrent.ExecutorService;

public interface PausableExecutor extends ExecutorService {

    abstract void pause();
    abstract void resume();
    abstract boolean isPaused();
}
