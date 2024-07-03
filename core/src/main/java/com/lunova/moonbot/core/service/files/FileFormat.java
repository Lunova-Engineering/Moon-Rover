package com.lunova.moonbot.core.service.files;

import com.lunova.moonbot.core.service.files.strategies.JsonFormatStrategy;
import com.lunova.moonbot.core.service.files.strategies.TextFormatStrategy;

public enum FileFormat {
    TXT("text/plain", "txt", new TextFormatStrategy()),
    JSON("application/json", "json", new JsonFormatStrategy()),;
    /*    LOG,
    MP3,
    MP4,
    PNG,
    JPG,
    SVG,*/

    private final String mimeType;

    private final String extension;

    private final FormatStrategy formatStrategy;

    FileFormat(String mimeType, String extension, FormatStrategy formatStrategy) {
        this.mimeType = mimeType;
        this.extension = extension;
        this.formatStrategy = formatStrategy;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getExtension() {
        return extension;
    }

    public FormatStrategy getFormatStrategy() {
        return formatStrategy;
    }
}
