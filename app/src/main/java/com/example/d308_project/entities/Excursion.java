package com.example.d308_project.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

@Entity(tableName = "excursions", foreignKeys = @ForeignKey(entity = Vacation.class, parentColumns = "id", childColumns = "vacationId", onDelete = ForeignKey.RESTRICT), indices = @Index(value = "vacationId") // Ensure this column is indexed for faster queries
)
public class Excursion extends TravelEntity {

    private String date;

    private int vacationId;

    public Excursion(final String name, final String date, final int vacationId) {
        super(name);
        this.date = date;
        this.vacationId = vacationId;
    }

    @Ignore
    public Excursion(final int id, final String name, final String date, final int vacationId) {
        super(id, name);
        this.date = date;
        this.vacationId = vacationId;
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
