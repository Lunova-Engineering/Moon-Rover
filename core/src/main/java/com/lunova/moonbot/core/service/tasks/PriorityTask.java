package com.lunova.moonbot.core.service.tasks;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface PriorityTask extends Comparable<PriorityTask> {

     static final Logger logger = LoggerFactory.getLogger(PriorityTask.class);

    int getPriority();

    @Override
    default int compareTo(@NotNull PriorityTask o) {

        int result = Integer.compare(getPriority(), o.getPriority());
        //logger.debug("Comparing: (this) {} against (other) {} ---- Result: {}", getPriority(), o.getPriority(), result);
        return result;
    }

}
