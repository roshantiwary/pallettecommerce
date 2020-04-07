package com.pallette.solr.products.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Roshan.
 */
public class Property {

	private String id;
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	private boolean selected;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("%s{id=%d, name=%s}", getClass(), getId(), getName());
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(final Object that) {
		if (that == null || getClass() != that.getClass()) {
			return false;
		}

		PropertyValue obj = (PropertyValue) that;

		return this == that || id.equals(obj.getId());
	}

	private Set<PropertyValue> values = new HashSet<>();

	private Map<String, PropertyValue> searchableValues;

	public Set<PropertyValue> getValues() {
		return values;
	}

	public PropertyValue getValue(String id) {
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
