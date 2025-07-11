package com.structurizr.view;

import com.structurizr.PropertyHolder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractStyle implements PropertyHolder {

    private ColorScheme colorScheme = null;

    private Map<String, String> properties = new HashMap<>();

    /**
     * Gets the color scheme of this style.
     *
     * @return  a ColorScheme, or null if not specified (i.e. applies to light and dark).
     */
    public ColorScheme getColorScheme() {
        return colorScheme;
    }

    /**
     * Sets the color scheme of this style.
     *
     * @param colorScheme  a ColorScheme, or null if not specified (i.e. applies to light and dark).
     */
    void setColorScheme(ColorScheme colorScheme) {
        this.colorScheme = colorScheme;
    }

    /**
     * Gets the collection of name-value property pairs associated with this workspace, as a Map.
     *
     * @return  a Map (String, String) (empty if there are no properties)
     */
    public Map<String, String> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    /**
     * Adds a name-value pair property to this workspace.
     *
     * @param name      the name of the property
     * @param value     the value of the property
     */
    public void addProperty(String name, String value) {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("A property name must be specified.");
        }

        if (value == null || value.trim().length() == 0) {
            throw new IllegalArgumentException("A property value must be specified.");
        }

        properties.put(name, value);
    }

    void setProperties(Map<String, String> properties) {
        if (properties != null) {
            this.properties = new HashMap<>(properties);
        }
    }

}