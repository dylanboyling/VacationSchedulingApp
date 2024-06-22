package com.example.d308_project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.d308_project.R;
import com.example.d308_project.database.Repository;
import com.example.d308_project.entities.Excursion;
import com.example.d308_project.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class VacationList extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_list);

        final FloatingActionButton fab = findViewById(R.id.newVacation);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(VacationList.this, VacationDetails.class);
            startActivity(intent);
        });

        final RecyclerView vacationRecyclerView = findViewById(R.id.vacationRecyclerView);
        repository = new Repository(getApplication());
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        vacationRecyclerView.setAdapter(vacationAdapter);
        vacationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(repository.getAllVacations());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editable_list, menu);
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
        final List<Vacation> vacations = repository.getAllVacations();
        final RecyclerView vacationRecyclerView = findViewById(R.id.vacationRecyclerView);
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        vacationRecyclerView.setAdapter(vacationAdapter);
        vacationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(vacations);
    }
}