package com.pallette.solr.products.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Roshan.
 */
public class Property extends NamedEntity {

    private Set<PropertyValue> values = new HashSet<>();

    private Map<Long, PropertyValue> searchableValues;

    public Set<PropertyValue> getValues() {
        return values;
    }

    public PropertyValue getValue(Long id) {
        if (null == searchableValues) {
            searchableValues = new HashMap<>();

            getValues().forEach(value -> searchableValues.put(value.getId(), value));
        }

        return searchableValues.get(id);
    }

    public void setValues(final Set<PropertyValue> values) {
        values.forEach(value -> value.setProperty(this));
        this.values = values;
    }

    public void addValue(final PropertyValue value) {
        value.setProperty(this);
        values.add(value);
    }

    public void removeValue(final PropertyValue value) {
        value.setProperty(null);
        values.remove(value);
    }
}
