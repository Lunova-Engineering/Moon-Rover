package com.lunova.moonbot.core.service.files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileOptions {

    private static final Logger logger = LoggerFactory.getLogger(FileOptions.class);

    private final Charset charset;

    private final OpenOption[] options;

    private FileOptions(Charset charset, OpenOption[] options) {
        this.charset = charset;
        this.options = options;
    }

    public static FileOptions create(OpenOption accessMode, OpenOption... options) {
        return create(StandardCharsets.UTF_8, accessMode, options);
    }

    public static FileOptions create(
            Charset charset, OpenOption accessMode, OpenOption... options) {
        return new FileOptions(charset, validateAndModifyOptions(join(accessMode, options)));
    }

    private static OpenOption[] validateAndModifyOptions(OpenOption[] options) {
        List<OpenOption> optionList = new ArrayList<>(Arrays.asList(options));

        if (!optionList.contains(StandardOpenOption.READ)
                && !optionList.contains(StandardOpenOption.WRITE)) {
            throw new UnsupportedOperationException(
                    "At least one of READ or WRITE options must be provided.");
        }

        if (optionList.contains(StandardOpenOption.READ)
                && optionList.contains(StandardOpenOption.WRITE)) {
            throw new UnsupportedOperationException(
                    "Both READ and WRITE options cannot be provided.");
        }

        // If no APPEND or TRUNCATE_EXISTING option is provided, add TRUNCATE_EXISTING
        if (!optionList.contains(StandardOpenOption.APPEND)
                && !optionList.contains(StandardOpenOption.TRUNCATE_EXISTING)) {
            optionList.add(StandardOpenOption.TRUNCATE_EXISTING);
        }

        // If no CREATE or CREATE_NEW option is provided, add CREATE
        if (!optionList.contains(StandardOpenOption.CREATE)
                && !optionList.contains(StandardOpenOption.CREATE_NEW)) {
            optionList.add(StandardOpenOption.CREATE);
        }

        return optionList.toArray(new OpenOption[0]);
    }

    public Charset getCharset() {
        return charset;
    }

    public OpenOption[] getOptions() {
        return options;
    }

    @SafeVarargs
    public static <T> T[] join(T singleValue, T... varargs) {
        T[] newArray = Arrays.copyOf(varargs, varargs.length + 1);
        System.arraycopy(newArray, 0, newArray, 1, varargs.length);
        newArray[0] = singleValue;
        return newArray;
    }
}
