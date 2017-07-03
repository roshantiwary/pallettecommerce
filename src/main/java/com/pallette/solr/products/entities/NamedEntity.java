package com.pallette.solr.products.entities;

/**
 * Created by Roshan.
 */
public class NamedEntity {

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

        NamedEntity obj = (NamedEntity) that;

        return this == that || id.equals(obj.getId());
    }
}
