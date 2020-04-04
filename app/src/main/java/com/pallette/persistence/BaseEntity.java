package com.pallette.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public abstract class BaseEntity implements Serializable {

	private int version;

    @Id
    private Long id;

    private Date timeCreated;

    public BaseEntity() {
        this(UUID.randomUUID());
    }

    public BaseEntity(UUID guid) {
        Assert.notNull(guid, "UUID is required");
        id = guid.getMostSignificantBits() & Long.MAX_VALUE;
        this.timeCreated = new Date();
    }

    public Long getId() {
        return id;
    }

    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity that = (BaseEntity) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    public int getVersion() {
        return version;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

}
