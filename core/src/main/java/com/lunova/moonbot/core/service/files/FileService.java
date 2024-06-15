package com.lunova.moonbot.core.service.files;

import com.lunova.moonbot.core.Constants;
import com.lunova.moonbot.core.service.Service;
import com.lunova.moonbot.core.service.ServiceInfo;
import com.lunova.moonbot.core.service.executors.ServiceExecutor;
import com.lunova.moonbot.core.service.executors.ThreadFactoryConfig;
import com.lunova.moonbot.core.service.files.tasks.ReadFileTask;
import com.lunova.moonbot.core.service.files.tasks.WriteFileTask;
import com.lunova.moonbot.core.service.tasks.TaskPriority;
import com.lunova.moonbot.core.utility.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

@ServiceInfo(name = "File Service", priority = 1)
@ThreadFactoryConfig(nameFormat = "File-Service")
public class FileService extends Service<ServiceExecutor> {

    public static final Path TESTING = Paths.get(Constants.CORE_DATA_PATH.toString(), "services", "test");

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);


    public FileService(String name, boolean critical, ServiceExecutor executor) {
        super(name, critical, executor);
    }


    public <T> Future<?> writeFile(Path path, String name, FileFormat format, T data, OpenOption... options) {
        return writeFile(TaskPriority.NORMAL, path, name, format, data, options);
    }

    public <T> Future<?> writeFile(TaskPriority priority, Path path, String name, FileFormat format, T data, OpenOption... options) {
        name = Utilities.concat(name, ".", format.getExtension());
        return submit(new WriteFileTask<>(priority, this, format, path.resolve(name),
                                    data, FileOptions.create(StandardOpenOption.WRITE, options)));
    }

    public <T> Future<T> readFile(Path path, String name, FileFormat format, Class<T> returnType, OpenOption... options) {
        return readFile(TaskPriority.NORMAL, path, name, format, returnType, options);
    }

    public <T> Future<T> readFile(TaskPriority priority, Path path, String name, FileFormat format, Class<T> returnType, OpenOption... options) {

        name = Utilities.concat(name, ".", format.getExtension());
        return submit(new ReadFileTask<>(priority, this, path.resolve(name),
                                         format, returnType, FileOptions.create(StandardOpenOption.READ, options)));
    }
}
