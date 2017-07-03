package com.pallette.solr.products.entities;

/**
 * Created by Roshan.
 */
public class PropertyValue extends NamedEntity {

    private Property property;

    public Property getProperty() {
        return property;
    }

    public void setProperty(final Property property) {
        this.property = property;
    }
}
