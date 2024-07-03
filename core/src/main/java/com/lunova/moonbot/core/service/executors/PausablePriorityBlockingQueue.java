package com.lunova.moonbot.core.service.executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PausablePriorityBlockingQueue<E> extends PriorityBlockingQueue<E> {

    private static final Logger logger =
            LoggerFactory.getLogger(PausablePriorityBlockingQueue.class);

    private final ReentrantLock pauseLock = new ReentrantLock();
    private final Condition unpaused = pauseLock.newCondition();
    private boolean isPaused = false;

    public void pause() {
        pauseLock.lock();
        try {
            isPaused = true;
        } finally {
            pauseLock.unlock();
        }
    }

    public void resume() {
        pauseLock.lock();
        try {
            isPaused = false;
            unpaused.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }

    @Override
    public E poll() {
        pauseCheck("Unable to poll from this queue while paused.");
        return super.poll();
    }

    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        pauseCheck("Unable to poll from this queue while paused.");
        return super.poll(timeout, unit);
    }

    @Override
    public E peek() {
        pauseCheck("Unable to take peek this queue while paused.");
        return super.peek();
    }

    @Override
    public E take() throws InterruptedException {
        pauseCheck("Unable to take from this queue while paused.");
        return super.take();
    }

    private void pauseCheck(String message) {
        pauseLock.lock();
        try {
            while (isPaused) {
                logger.debug(message);
                unpaused.await();
            }
        } catch (InterruptedException e) {
            logger.warn("Pause for queue has been interrupted!", e);
        } finally {
            pauseLock.unlock();
        }
    }
}
