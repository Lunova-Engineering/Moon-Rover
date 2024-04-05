package com.lunova.moonbot.core.service.executors;

import java.util.concurrent.ExecutorService;

public interface PausableExecutor extends ExecutorService {

    void pause();
    void resume();
    boolean isPaused();
}
