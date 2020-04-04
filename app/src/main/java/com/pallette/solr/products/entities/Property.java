package com.pallette.solr.products.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Roshan.
 */

@Document(collection = "solr_properties_index")
public class Property implements Serializable{

	@Id
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
//    @OneToMany(mappedBy = "property", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    
    @DBRef
    private Set<PropertyValue> values = new HashSet<>();

    @Transient
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
    
    @Override
    public String toString() {
        return String.format("%s{id=%d, name=%s}", getClass(), getId(), getName());
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public boolean equals(final Object that) {
        if (that == null || getClass() != that.getClass()) {
            return false;
        }

        Product obj = (Product) that;

        return this == that || id.equals(obj.getId());
    }
}