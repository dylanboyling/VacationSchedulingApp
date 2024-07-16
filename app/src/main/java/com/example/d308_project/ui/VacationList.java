package com.example.d308_project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.d308_project.R;
import com.example.d308_project.Utils;
import com.example.d308_project.database.Repository;
import com.example.d308_project.entities.Excursion;
import com.example.d308_project.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class VacationList extends AppCompatActivity {

    public Repository repository;

    private VacationAdapter vacationAdapter;

    public String currentQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_list);

        final FloatingActionButton fab = findViewById(R.id.newVacation);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(VacationList.this, VacationDetails.class);
            startActivity(intent);
        });

        repository = new Repository(getApplication());
        vacationAdapter = new VacationAdapter(this);

        final RecyclerView vacationRecyclerView = findViewById(R.id.vacationRecyclerView);
        vacationRecyclerView.setAdapter(vacationAdapter);
        vacationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(repository.getAllVacations());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        Menu item for adding sample data
//        getMenuInflater().inflate(R.menu.menu_editable_list, menu);

        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        // Set up the search view
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String query) {
                final String sanitizedQuery = Utils.sanitizeString(query);
                currentQuery = sanitizedQuery;
                filterVacations(sanitizedQuery);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sample) {
            Toast.makeText(VacationList.this, "Sample vacation created!", Toast.LENGTH_LONG).show();
            repository = new Repository(getApplication());
            final Vacation vacation = new Vacation("Sample Vacation", "Hotel", "2024-6-15",
                    "2024-7-15" );
            final int vacationId = repository.insert(vacation);

            final Excursion excursion = new Excursion("Sample Excursion 1", "2024-6-21", vacationId);
            repository.insert(excursion);
            this.finish();
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();

        refreshVacationList();

        final RecyclerView vacationRecyclerView = findViewById(R.id.vacationRecyclerView);
        vacationRecyclerView.setAdapter(vacationAdapter);
        vacationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void refreshVacationList() {
        if (currentQuery.isEmpty()) {
            vacationAdapter.setVacations(repository.getAllVacations());
        } else {
            filterVacations(currentQuery);
        }
    }

    public void filterVacations(final String query) {
        final List<Vacation> mVacations = repository.getAllVacations();
        final List<Vacation> filteredVacations = new ArrayList<>();

        for (final Vacation vacation : mVacations) {
            if (vacation.getName().toLowerCase().contains(query.toLowerCase()) ||
                    vacation.getLodging().toLowerCase().contains(query.toLowerCase()) ||
                    vacation.getStartDate().toLowerCase().contains(query.toLowerCase()) ||
                    vacation.getEndDate().toLowerCase().contains(query.toLowerCase())) {
                filteredVacations.add(vacation);
            }
        }

        vacationAdapter.setVacations(filteredVacations);
    }

}