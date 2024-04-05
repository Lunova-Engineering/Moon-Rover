package com.lunova.moonbot.core.service.files.json;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.Separators;

import java.io.IOException;

public class GsonPrettyPrinter extends DefaultPrettyPrinter {

    public GsonPrettyPrinter() {
        _arrayIndenter = DefaultIndenter.SYSTEM_LINEFEED_INSTANCE;
        _objectIndenter = DefaultIndenter.SYSTEM_LINEFEED_INSTANCE;
    }

    public GsonPrettyPrinter(DefaultPrettyPrinter base) {
        super(base);
    }

    @Override
    public GsonPrettyPrinter createInstance() {
        if (getClass() != GsonPrettyPrinter.class) {
            throw new IllegalStateException("Failed `createInstance()`: " + getClass().getName()
                    + " does not override method; it has to");
        }
        return new GsonPrettyPrinter(this);
    }

    @Override
    public GsonPrettyPrinter withSeparators(Separators separators) {
        this._separators = separators;
        this._objectFieldValueSeparatorWithSpaces = separators.getObjectFieldValueSeparator() + " ";
        return this;
    }

    @Override
    public void writeEndArray(JsonGenerator g, int nrOfValues) throws IOException {
        if (!_arrayIndenter.isInline()) {
            --_nesting;
        }
        if (nrOfValues > 0) {
            _arrayIndenter.writeIndentation(g, _nesting);
        }
        g.writeRaw(']');
    }

    @Override
    public void writeEndObject(JsonGenerator g, int nrOfEntries) throws IOException {
        if (!_objectIndenter.isInline()) {
            --_nesting;
        }
        if (nrOfEntries > 0) {
            _objectIndenter.writeIndentation(g, _nesting);
        }
        g.writeRaw('}');
    }
}
