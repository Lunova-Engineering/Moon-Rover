package com.lunova.moonbot.core.service.executors;

import com.lunova.moonbot.core.service.tasks.CallableServiceTask;
import com.lunova.moonbot.core.service.tasks.PriorityFutureTask;
import com.lunova.moonbot.core.service.tasks.RunnableServiceTask;
import com.lunova.moonbot.core.service.tasks.ServiceTask;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//TODO: Note - The reason the first task always executes first without regard to priority values is most likely due to the pause function
// acquires the task to execute prior to recognizing that it is paused then proceeds to wait until being unpaused and executing the runnable
public class ServiceExecutor extends ThreadPoolExecutor implements PausableExecutor {

    private static final Logger logger = LoggerFactory.getLogger(ServiceExecutor.class);

    private final ReentrantLock pauseLock = new ReentrantLock();

    private final Condition unpaused = pauseLock.newCondition();

    private boolean isPaused;


    public ServiceExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, @NotNull TimeUnit unit, @NotNull BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public ServiceExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, @NotNull TimeUnit unit, @NotNull BlockingQueue<Runnable> workQueue, @NotNull ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public ServiceExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, @NotNull TimeUnit unit, @NotNull BlockingQueue<Runnable> workQueue, @NotNull RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public ServiceExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, @NotNull TimeUnit unit, @NotNull BlockingQueue<Runnable> workQueue, @NotNull ThreadFactory threadFactory, @NotNull RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    public void pause() {
        pauseLock.lock();
        if(getQueue() instanceof PausablePriorityBlockingQueue)
            ((PausablePriorityBlockingQueue) getQueue()).pause();
        try {
            isPaused = true;
        } finally {
            pauseLock.unlock();
        }
    }

    @Override
    public void resume() {
        pauseLock.lock();
        if(getQueue() instanceof PausablePriorityBlockingQueue)
            ((PausablePriorityBlockingQueue) getQueue()).resume();
        try {
            isPaused = false;
            unpaused.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }

    @Override
    public boolean isPaused() {
        return isPaused;
    }

    @Override
    public void shutdown() {
        super.shutdown();
    }

    @Override
    public void execute(@NotNull Runnable command) {
        super.execute(command);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        pauseLock.lock();
        try {
            while (isPaused) {
                unpaused.await();
            }
        } catch (InterruptedException ie) {
            t.interrupt();
        } finally {
            pauseLock.unlock();
        }
        super.beforeExecute(t, r);
    }



    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
    }

    @Override
    protected void terminated() {
        super.terminated();
    }


    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        if (runnable instanceof RunnableServiceTask)
            return new PriorityFutureTask<>((RunnableServiceTask) runnable, value);
        return super.newTaskFor(runnable, value);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        if (callable instanceof CallableServiceTask<T>)
            return new PriorityFutureTask<>((CallableServiceTask<T>) callable);
        return super.newTaskFor(callable);
    }

    public BlockingQueue<ServiceTask> getTaskQueue() {
        return getQueue().stream()
                .flatMap(runnable -> {
                    if (runnable instanceof ServiceTask) {
                        // Directly cast and return as stream if it's a ServiceTask
                        return Stream.of((ServiceTask) runnable);
                    } else if (runnable instanceof PriorityFutureTask) {
                        // If it's a PriorityFutureTask, extract the ServiceTask and return as stream
                        return Stream.of(((PriorityFutureTask<?>) runnable).getTask());
                    }
                    // Filter out non-matching types by returning an empty stream
                    return Stream.empty();
                })
                .collect(Collectors.toCollection(LinkedBlockingQueue::new)); // Collect into a new BlockingQueue
    }

    public ArrayList<ServiceTask> getTaskList() {
        return  null;
    }




}
