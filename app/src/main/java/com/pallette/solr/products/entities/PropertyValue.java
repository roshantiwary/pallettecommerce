package com.pallette.solr.products.entities;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Roshan.
 */
@Document(collection = "solr_property_values_index")
public class PropertyValue implements Serializable {

//    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinColumn(foreignKey = @ForeignKey(name = "fk_prop"), nullable = false)
	
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
	
	@DBRef
	private Property property;

    public Property getProperty() {
        return property;
    }

    public void setProperty(final Property property) {
        this.property = property;
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