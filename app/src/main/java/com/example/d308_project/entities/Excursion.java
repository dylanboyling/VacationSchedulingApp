package com.example.d308_project.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursions", foreignKeys = @ForeignKey(entity = Vacation.class, parentColumns = "id", childColumns = "vacationId", onDelete = ForeignKey.RESTRICT), indices = @Index(value = "vacationId") // Ensure this column is indexed for faster queries
)
public class Excursion {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String date;

    private int vacationId;

    public Excursion(){
    }

    public Excursion(final String name, final String date, final int vacationId) {
        this.name = name;
        this.date = date;
        this.vacationId = vacationId;
    }

    public Excursion(final int id, final String name, final String date, final int vacationId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.vacationId = vacationId;
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

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(final int vacationId) {
        this.vacationId = vacationId;
    }
}
