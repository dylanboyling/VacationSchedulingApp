package com.dylan.d424_project.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dylan.d424_project.dao.ExcursionDao;
import com.dylan.d424_project.dao.VacationDao;
import com.dylan.d424_project.entities.Excursion;
import com.dylan.d424_project.entities.Vacation;


@Database(entities = {Excursion.class, Vacation.class}, version = 1, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract VacationDao vacationDao();

    public abstract ExcursionDao excursionDao();

    private static volatile DatabaseBuilder INSTANCE;

    public static DatabaseBuilder getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (DatabaseBuilder.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DatabaseBuilder.class, "database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}
