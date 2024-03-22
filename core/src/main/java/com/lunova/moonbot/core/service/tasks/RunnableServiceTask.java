package com.lunova.moonbot.core.service.tasks;

import com.lunova.moonbot.core.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RunnableServiceTask extends ServiceTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RunnableServiceTask.class);

    public RunnableServiceTask(TaskPriority taskPriority, Service<?> originator) {
        super(taskPriority, originator);
    }

    @Override
    public void run() {
        try {
            setStartTime(System.currentTimeMillis());
            setTaskState(TaskState.RUNNING);
            logger.debug("Running task code now.");
            onRun();
            setTaskState(TaskState.COMPLETED);
        } catch(RuntimeException e) {
            setTaskState(TaskState.COMPLETED_WITH_ERRORS);
        } catch (Exception e) {
            setTaskState(TaskState.FAILED);
        } finally {
            setCompleteTime(System.currentTimeMillis());
            StringBuilder logEntry = new StringBuilder();
            logEntry.append("Task Details - ")
                    .append("Priority: ").append(this.getTaskPriority())
                    .append(", Originator: ").append(this.getOriginator().getName())
                    .append(", State: ").append(this.getTaskState())
                    .append(", Submission Time: ").append(this.getSubmissionTime())
                    .append(", Start Time: ").append(this.getStartTime())
                    .append(", Complete Time: ").append(this.getCompleteTime());
            logger.debug(logEntry.toString());
        }
    }

    protected abstract void onRun();

}
