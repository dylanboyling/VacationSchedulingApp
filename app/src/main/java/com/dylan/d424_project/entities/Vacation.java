package com.dylan.d424_project.entities;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "vacations")
public class Vacation extends TravelEntity {

    private String lodging;

    private String startDate;

    private String endDate;

    public Vacation(final String name, final String lodging, final String startDate, final String endDate) {
        this.name = name;
        this.lodging = lodging;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Ignore
    public Vacation(final int id, final String name, final String lodging, final String startDate, final String endDate) {
        this.id = id;
        this.name = name;
        this.lodging = lodging;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getLodging() {
        return lodging;
    }

    public void setLodging(String lodging) {
        this.lodging = lodging;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
