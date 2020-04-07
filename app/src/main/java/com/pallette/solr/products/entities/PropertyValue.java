package com.pallette.solr.products.entities;

/**
 * Created by Roshan.
 */
public class PropertyValue {

    private String id;
    
    private long count;

    private String refinementURL;
    
	public String getRefinementURL() {
		return refinementURL;
	}

	public void setRefinementURL(String refinementURL) {
		this.refinementURL = refinementURL;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

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
		// TODO Auto-generated method stub
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

    private Property property;

    public Property getProperty() {
        return property;
    }

    public void setProperty(final Property property) {
        this.property = property;
    }
}
