package com.example.d308_project.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.d308_project.entities.Vacation;

import java.util.List;

@Dao
public interface VacationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(final Vacation vacation);

    @Update
    void update(final Vacation vacation);

    @Delete
    void delete(final Vacation vacation);

    @Query("DELETE FROM vacations WHERE id = :id")
    void deleteById(final int id);

    @Query("SELECT * FROM vacations ORDER BY id ASC")
    List<Vacation> getAllVacations();

    @Query("SELECT * FROM vacations WHERE id = :id")
    Vacation getVacationById(final int id);
}

