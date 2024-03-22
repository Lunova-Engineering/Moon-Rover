package com.lunova.moonbot.core.service.executors;

import com.google.common.base.Optional;
import com.lunova.moonbot.core.service.tasks.CallableServiceTask;
import com.lunova.moonbot.core.service.tasks.RunnableServiceTask;
import com.lunova.moonbot.core.service.tasks.ServiceTask;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class ServiceExecutor extends ThreadPoolExecutor implements PausableExecutor {

    private static final Logger logger = LoggerFactory.getLogger(ServiceExecutor.class);

    private boolean isPaused;

    private final ReentrantLock pauseLock = new ReentrantLock();

    private final Condition unpaused = pauseLock.newCondition();


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
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        if(runnable instanceof RunnableServiceTask)
            return new PriorityFutureTask<>((RunnableServiceTask) runnable, value);
        return super.newTaskFor(runnable, value);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        if(callable instanceof CallableServiceTask<T>)
            return new PriorityFutureTask<>((CallableServiceTask<T>) callable);
        return super.newTaskFor(callable);
    }


    @Override
    public BlockingQueue<Runnable> getQueue() {
        return super.getQueue();
    }

    public BlockingQueue<ServiceTask> getTaskQueue() {
        Optional<Runnable> r = Optional.fromNullable(getQueue().peek());
        if(!r.isPresent())
            return new LinkedBlockingQueue<>();
        if(r.get().getClass().isAssignableFrom(ServiceTask.class))
            return getQueue().stream()
                    .filter(runnable -> runnable instanceof ServiceTask) // Ensure it's a ServiceTask instance
                    .map(runnable -> (ServiceTask) runnable) // Perform the cast
                    .collect(Collectors.toCollection(LinkedBlockingQueue::new)); // Collect into a new BlockingQueue
        return new LinkedBlockingQueue<>();
    }

    private static class PriorityFutureTask<V> extends FutureTask<V> implements Comparable<PriorityFutureTask<?>> {

        private final ServiceTask task;

        public PriorityFutureTask(@NotNull CallableServiceTask<V> callable) {
            super(callable);
            task = callable;
        }

        public PriorityFutureTask(@NotNull RunnableServiceTask runnable, V result) {
            super(runnable, result);
            task = runnable;
        }

        public ServiceTask getTask() {
            return task;
        }

        @Override
        public int compareTo(@NotNull PriorityFutureTask<?> o) {
            return Integer.compare(this.task.getTaskPriority().ordinal(), o.getTask().getTaskPriority().ordinal());
        }

    }

    /*    *//**
     * Executes the given task sometime in the future.  The task
     * may execute in a new thread or in an existing pooled thread.
     * <p>
     * If the task cannot be submitted for execution, either because this
     * executor has been shutdown or because its capacity has been reached,
     * the task is handled by the current {@link RejectedExecutionHandler}.
     *
     * @param command the task to execute
     * @throws RejectedExecutionException at discretion of
     *                                    {@code RejectedExecutionHandler}, if the task
     *                                    cannot be accepted for execution
     * @throws NullPointerException       if {@code command} is null
     *//*
    @Override
    public void execute(@NotNull Runnable command) {
        super.execute(command);
    }


    *//**
     * Returns a {@code RunnableFuture} for the given runnable and default
     * value.
     *
     * @param runnable the runnable task being wrapped
     * @param value    the default value for the returned future
     * @return a {@code RunnableFuture} which, when run, will run the
     * underlying runnable and which, as a {@code Future}, will yield
     * the given value as its result and provide for cancellation of
     * the underlying task
     * @since 1.6
     *//*
    private <T> RunnableFuture<T> newServiceTaskFor(RunnableServiceTask runnable, T value) {
        return new FutureServiceTask<T>(runnable, value);
    }

    *//**
     * Returns a {@code RunnableFuture} for the given callable task.
     *
     * @param callable the callable task being wrapped
     * @return a {@code RunnableFuture} which, when run, will call the
     * underlying callable and which, as a {@code Future}, will yield
     * the callable's result as its result and provide for
     * cancellation of the underlying task
     * @since 1.6
     *//*
    private <T> RunnableFuture<T> newServiceTaskFor(CallableServiceTask<T> callable) {
        return new FutureServiceTask<T>(callable);
    }



    *//**
     * @param task
     * @throws RejectedExecutionException {@inheritDoc}
     * @throws NullPointerException       {@inheritDoc}
     *//*
    @NotNull
    @Override
    public Future<?> submit(Runnable task) {
        if (task == null) throw new NullPointerException();
        //RunnableFuture<Void> ftask = newTaskFor(task, null);
        FutureServiceTask<Void> ftask = getNewTask(task, null);
        logger.info("{}", ftask.getClass().getName());
        execute(ftask);
        return ftask;
    }

    private <T> FutureServiceTask<T> getNewTask(Runnable task, T value) {
        logger.info("{}", task.getClass().getName());
        if(task instanceof RunnableServiceTask)
            return new FutureServiceTask<>((RunnableServiceTask) task, value);
        else if(task instanceof CallableServiceTask<?>)
            return new FutureServiceTask<>((CallableServiceTask<T>) task);
        return null;
    }

    *//**
     * @param task
     * @param result
     * @throws RejectedExecutionException {@inheritDoc}
     * @throws NullPointerException       {@inheritDoc}
     *//*
    @NotNull
    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        if (task == null) throw new NullPointerException();
        RunnableFuture<T> ftask = newTaskFor(task, result);
        execute(ftask);
        return ftask;
    }

    *//**
     * @param task
     * @throws RejectedExecutionException {@inheritDoc}
     * @throws NullPointerException       {@inheritDoc}
     *//*
    @NotNull
    @Override
    public <T> Future<T> submit(Callable<T> task) {
        if (task == null) throw new NullPointerException();
        RunnableFuture<T> ftask = newTaskFor(task);
        execute(ftask);
        return ftask;
    }*/

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        logger.debug("Getting ready to execute a task, before code running.");
        pauseLock.lock();
        try {
            while (isPaused) {
                logger.debug("Thread is paused, awaiting.");
                unpaused.await();
                logger.debug("Thread is done waiting, resuming execution.");
            }
        } catch (InterruptedException ie) {
            logger.error("Thread has been interrupted.", ie);
            t.interrupt();
        } finally {
            logger.debug("pauselock is unlocked.");
            pauseLock.unlock();
        }
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        logger.debug("Completed execution of task.");
        super.afterExecute(r, t);
    }

    @Override
    protected void terminated() {
        super.terminated();
    }

    @Override
    public void pause() {
        pauseLock.lock();
        try {
            isPaused = true;
        } finally {
            pauseLock.unlock();
        }
    }

    @Override
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
    public boolean isPaused() {
        return isPaused;
    }
/*    private static class FutureServiceTask<V> extends FutureTask<V> implements Comparable<TaskPriority> {

        private final ServiceTask task;

        *//**
         * Creates a {@code FutureTask} that will, upon running, execute the
         * given {@code Callable}.
         *
         * @param callable the callable task
         * @throws NullPointerException if the callable is null
         *//*
        public FutureServiceTask(@NotNull CallableServiceTask<V> callable) {
            super(callable);
            this.task = callable;
        }

        *//**
         * Creates a {@code FutureTask} that will, upon running, execute the
         * given {@code Runnable}, and arrange that {@code get} will return the
         * given result on successful completion.
         *
         * @param runnable the runnable task
         * @param result   the result to return on successful completion. If
         *                 you don't need a particular result, consider using
         *                 constructions of the form:
         *                 {@code Future<?> f = new FutureTask<Void>(runnable, null)}
         * @throws NullPointerException if the runnable is null
         *//*
        public FutureServiceTask(@NotNull RunnableServiceTask runnable, V result) {
            super(runnable, result);
            this.task = runnable;
        }

        *//**
         * Compares this object with the specified object for order.  Returns a
         * negative integer, zero, or a positive integer as this object is less
         * than, equal to, or greater than the specified object.
         *
         * <p>The implementor must ensure {@link Integer#signum
         * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
         * all {@code x} and {@code y}.  (This implies that {@code
         * x.compareTo(y)} must throw an exception if and only if {@code
         * y.compareTo(x)} throws an exception.)
         *
         * <p>The implementor must also ensure that the relation is transitive:
         * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
         * {@code x.compareTo(z) > 0}.
         *
         * <p>Finally, the implementor must ensure that {@code
         * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
         * == signum(y.compareTo(z))}, for all {@code z}.
         *
         * @param o the object to be compared.
         * @return a negative integer, zero, or a positive integer as this object
         * is less than, equal to, or greater than the specified object.
         * @throws NullPointerException if the specified object is null
         * @throws ClassCastException   if the specified object's type prevents it
         *                              from being compared to this object.
         * @apiNote It is strongly recommended, but <i>not</i> strictly required that
         * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
         * class that implements the {@code Comparable} interface and violates
         * this condition should clearly indicate this fact.  The recommended
         * language is "Note: this class has a natural ordering that is
         * inconsistent with equals."
         *//*
        @Override
        public int compareTo(@NotNull TaskPriority o) {
            return Integer.compare(o.ordinal(), task.getTaskPriority().ordinal());
        }

    }*/
}
