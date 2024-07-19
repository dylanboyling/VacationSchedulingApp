package com.dylan.d424_project.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.dylan.d424_project.entities.Excursion;

import java.util.List;

@Dao
public interface ExcursionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Excursion excursion);

    @Update
    void update(Excursion excursion);

    @Delete
    void delete(Excursion excursion);

    @Query("DELETE FROM excursions WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT * FROM excursions ORDER BY id ASC")
    List<Excursion> getAllExcursions();

    @Query("SELECT * FROM excursions WHERE vacationId = :vacationId ORDER BY id ASC")
    List<Excursion> getAssociatedExcursions(final int vacationId);
}
