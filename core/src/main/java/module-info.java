/**
 * Defines the core module for the Moon Bot application.
 *
 * <p>This module provides the foundational classes and interfaces that constitute the central
 * framework of the application. It is designed to support plugin-style additions, allowing for
 * extensibility and modular feature integration.
 *
 * <p>Future modules can depend on this core module to build additional features and plugins for the
 * Moon Bot application.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 11.11.2023
 */
open module com.lunova.moonbot.core {
    // Requires
    requires net.dv8tion.jda;
    requires spark.core;
    requires com.google.gson;
    requires jakarta.xml.bind;
    requires jakarta.activation;
    requires org.slf4j;
    requires ch.qos.logback.classic;
    requires ch.qos.logback.core;
    requires com.google.common;
    requires annotations;
    requires jakarta.el;
    requires jakarta.validation;
    requires org.hibernate.validator;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires org.apache.maven.resolver;
    requires org.apache.maven.resolver.util;
    requires org.apache.maven.resolver.impl;
    requires org.apache.maven.resolver.spi;
    requires org.apache.maven.resolver.named;
    requires org.apache.maven.resolver.transport.file;
    requires org.apache.maven.resolver.transport.http;
    requires org.apache.maven.resolver.supplier;
    requires com.fasterxml.jackson.datatype.guava;
    requires com.fasterxml.jackson.datatype.jdk8;
    requires reflections;

    // Exports
    exports com.lunova.moonbot.core.service.plugin;
    exports com.lunova.moonbot.core.api.plugin;
    exports com.lunova.moonbot.core.exceptions;
    exports com.lunova.moonbot.core.service.plugin.resolver;
    exports com.lunova.moonbot.core.api.plugin.features.settings;
    exports com.lunova.moonbot.core.api.plugin.examples;
    exports com.lunova.moonbot.core.api.plugin.features;
    exports com.lunova.moonbot.core.api.plugin.common;
    exports com.lunova.moonbot.core.api.plugin.features.settings.transformation;
    exports com.lunova.moonbot.core.api.plugin.features.settings.validation;
    exports com.lunova.moonbot.core.api.plugin.features.settings.input;
    exports com.lunova.moonbot.core.api.plugin.features.settings.input.impl;
    exports com.lunova.moonbot.core.api.plugin.features.settings.validation.rules;
/*
    // Opens
    opens com.lunova.moonbot.core.service.plugin to com.google.gson, org.hibernate.validator;
    opens com.lunova.moonbot.core.api.plugin to jakarta.xml.bind, com.google.gson, com.fasterxml.jackson.databind;
    opens com.lunova.moonbot.core.service.plugin.resolver to com.google.gson, org.hibernate.validator;
    opens com.lunova.moonbot.core.api.plugin.features.settings to jakarta.xml.bind, com.google.gson, com.fasterxml.jackson.databind;
    opens com.lunova.moonbot.core.api.plugin.examples to jakarta.xml.bind, com.google.gson, com.fasterxml.jackson.databind;
    opens com.lunova.moonbot.core.api.plugin.features to jakarta.xml.bind, com.google.gson, com.fasterxml.jackson.databind;
    opens com.lunova.moonbot.core.api.plugin.common to jakarta.xml.bind, com.google.gson, com.fasterxml.jackson.databind;
    opens com.lunova.moonbot.core.api.plugin.features.settings.validation.rules to jakarta.xml.bind, com.google.gson, com.fasterxml.jackson.databind;
    opens com.lunova.moonbot.core.api.plugin.features.settings.validation to jakarta.xml.bind, com.google.gson, com.fasterxml.jackson.databind;
    opens com.lunova.moonbot.core.api.plugin.features.settings.input to com.fasterxml.jackson.databind, com.google.gson, jakarta.xml.bind;
    opens com.lunova.moonbot.core.api.plugin.features.settings.input.impl to com.fasterxml.jackson.databind, com.google.gson, jakarta.xml.bind;*/
}
