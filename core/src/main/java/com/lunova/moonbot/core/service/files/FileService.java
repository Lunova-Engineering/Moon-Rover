package com.lunova.moonbot.core.service.files;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lunova.moonbot.core.Constants;
import com.lunova.moonbot.core.service.PausablePriorityBlockingQueue;
import com.lunova.moonbot.core.service.Service;
import com.lunova.moonbot.core.service.ServiceInfo;
import com.lunova.moonbot.core.service.executors.DefaultUncaughtExceptionHandler;
import com.lunova.moonbot.core.service.executors.ServiceExecutor;
import com.lunova.moonbot.core.service.files.tasks.ReadFileTask;
import com.lunova.moonbot.core.service.files.tasks.WriteFileTask;
import com.lunova.moonbot.core.service.tasks.TaskPriority;
import com.lunova.moonbot.core.utility.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@ServiceInfo(name = "File Service", critical = false)
public class FileService extends Service<ServiceExecutor> {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    public static final Path TESTING = Paths.get(Constants.CORE_DATA_DIR.toString(), "services", "test");

    public FileService(String name, boolean critical) {
        super(name, critical);
    }

    public <T> void writeFile(Path path, String name, FileFormat format, T data) {
        writeFile(TaskPriority.NORMAL, path, name, format, data);
    }

    public <T> void writeFile(TaskPriority priority, Path path, String name, FileFormat format, T data) {
        name = Utilities.concat(name, ".", format.getExtension());
        execute(new WriteFileTask<>(priority, this, format, path.resolve(name), data));
    }


    public <T> Future<?> writeFileWithResult(Path path, String name, FileFormat format, T data) {
        return writeFileWithResult(TaskPriority.NORMAL, path, name, format, data);
    }

    public <T> Future<?> writeFileWithResult(TaskPriority priority, Path path, String name, FileFormat format, T data) {
        name = Utilities.concat(name, ".", format.getExtension());
        return submit(new WriteFileTask<>(priority, this, format, path.resolve(name), data));
    }


    public <T> Future<T> readFile(TaskPriority priority, Path path, String name, FileFormat format, Class<T> returnType) {
        name = Utilities.concat(name, ".", format.getExtension());
        ReadFileTask<T> task = new ReadFileTask<>(priority, this, path.resolve(name), format, returnType);
        return submit(task);
    }


    @Override
    protected ServiceExecutor createExecutor() {
        ThreadFactory f = new ThreadFactoryBuilder().setNameFormat(getName().replace(" ", "-"))
                .setDaemon(false)
                .setPriority(Thread.NORM_PRIORITY)
                .setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(this))
                .build();
        ServiceExecutor executor = new ServiceExecutor(0, 4, 0, TimeUnit.NANOSECONDS, new PausablePriorityBlockingQueue<>(), f);
        executor.prestartCoreThread();
        return executor;
    }


}
