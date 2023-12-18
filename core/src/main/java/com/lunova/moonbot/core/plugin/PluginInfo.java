package com.lunova.moonbot.core.plugin;


import jakarta.xml.bind.annotation.*;

/**
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.15.2023
 */
@XmlRootElement(name = "plugin-info")
public class PluginInfo {

    private String name;
    private String version;
    private String mainClass;

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @XmlElement(name = "mainClass")
    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }
}
