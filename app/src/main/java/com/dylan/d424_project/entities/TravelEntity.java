package com.dylan.d424_project.entities;

import androidx.room.Ignore;
import androidx.room.PrimaryKey;

public abstract class TravelEntity {

    @PrimaryKey(autoGenerate = true)
    protected int id;

    protected String name;

    @Ignore
    public TravelEntity() {
    }

    public TravelEntity(final String name) {
        this.name = name;
    }

    @Ignore
    public TravelEntity(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
