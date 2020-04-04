package com.pallette.solr.products.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Roshan.
 */

@Document(collection = "solr_product_index")
public class Product implements Serializable {

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
    
    private Long price;

    public Long getPrice() {
        return price;
    }

    public void setPrice(final Long price) {
        this.price = price;
    }

//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
//    @JoinTable(name = "items_values",
//        joinColumns = {
//            @JoinColumn(
//                    name = "item_id",
//                    referencedColumnName = "id"
//            )
//        },
//        inverseJoinColumns = {
//            @JoinColumn(
//                    name = "value_id",
//                    referencedColumnName = "id"
//            )
//        }
//    )
    private Set<PropertyValue> values = new HashSet<PropertyValue>();

    public Set<PropertyValue> getValues() {
        return values;
    }

    public void setValues(final Set<PropertyValue> values) {
        this.values = values;
    }

    public void addValue(final PropertyValue value) {
        values.add(value);
    }

    public void removeValue(final PropertyValue value) {
        values.remove(value);
    }

    public Set<Property> getProperties() {
        final Set<Property> properties = new HashSet<>();

        values.forEach(value -> properties.add(value.getProperty()));

        return properties;
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