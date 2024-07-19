package com.dylan.d424_project.database;

import android.app.Application;

import com.dylan.d424_project.dao.ExcursionDao;
import com.dylan.d424_project.dao.VacationDao;
import com.dylan.d424_project.entities.Excursion;
import com.dylan.d424_project.entities.Vacation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Repository {
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private VacationDao mVacationDao;

    private ExcursionDao mExcursionDao;

    public Repository(final Application application) {
        DatabaseBuilder databaseBuilder = DatabaseBuilder.getDatabase(application);
        mVacationDao = databaseBuilder.vacationDao();
        mExcursionDao = databaseBuilder.excursionDao();
    }

    public List<Vacation> getAllVacations() {
        Future<List<Vacation>> future = databaseExecutor.submit(() -> mVacationDao.getAllVacations());
        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Excursion> getAllExcursions() {
        Future<List<Excursion>> future = databaseExecutor.submit(() -> mExcursionDao.getAllExcursions());
        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Excursion> getAssociatedExcursions(final int vacationId) {
        Future<List<Excursion>> future = databaseExecutor.submit(() -> mExcursionDao.getAssociatedExcursions(vacationId));
        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int insert(Vacation vacation) {
        Future<Integer> future = databaseExecutor.submit(() -> (int) mVacationDao.insert(vacation));
        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void update(Vacation vacation) {
        databaseExecutor.execute(() -> mVacationDao.update(vacation));
    }

    public Vacation getVacationById(final int id) {
        Future<Vacation> future = databaseExecutor.submit(() -> mVacationDao.getVacationById(id));
        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteVacationById(final int id) {
        databaseExecutor.execute(() -> mVacationDao.deleteById(id));
    }

    public void insert(Excursion excursion) {
        databaseExecutor.execute(() -> mExcursionDao.insert(excursion));
    }

    public void update(Excursion excursion) {
        databaseExecutor.execute(() -> mExcursionDao.update(excursion));
    }

    public void delete(Excursion excursion) {
        databaseExecutor.execute(() -> mExcursionDao.delete(excursion));
    }

    public void deleteExcursionById(final int id) {
        databaseExecutor.execute(() -> mExcursionDao.deleteById(id));
    }
}
