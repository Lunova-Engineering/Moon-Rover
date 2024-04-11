package com.lunova.moonbot.core.service.files;

import com.lunova.moonbot.core.Constants;
import com.lunova.moonbot.core.service.Service;
import com.lunova.moonbot.core.service.executors.*;
import com.lunova.moonbot.core.service.files.tasks.ReadFileTask;
import com.lunova.moonbot.core.service.files.tasks.WriteFileTask;
import com.lunova.moonbot.core.service.tasks.TaskPriority;
import com.lunova.moonbot.core.utility.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Future;

@ServiceInfo(name = "File Service")
@ThreadFactoryConfig(nameFormat = "File-Service")
public class FileService extends Service<ServiceExecutor> {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    public static final Path TESTING = Paths.get(Constants.CORE_DATA_DIR.toString(), "services", "test");

    public FileService(String name, boolean critical, ServiceExecutor executor) {
        super(name, critical, executor);
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


    //TODO: Use annotations to define executor values and thread factory values
    //TODO: Create a new class using a facade to create the values for the executor and thread factory and supply them
    //TODO: Ensure annotations are safe and do the same thing for any objects that are created as parameters in either



}
