package com.example.d308_project.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308_project.R;
import com.example.d308_project.Utils;
import com.example.d308_project.database.Repository;
import com.example.d308_project.entities.Excursion;
import com.example.d308_project.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;
import java.util.List;

public class VacationDetails extends AppCompatActivity {

    private Repository repository;

    private int vacationId;

    private TextInputEditText nameEditText;

    private TextInputEditText lodgingEditText;

    private TextInputEditText startDateEditText;

    private TextInputEditText endDateEditText;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);

        vacationId = getIntent().getIntExtra("vacationId", -1);

        final FloatingActionButton fab = findViewById(R.id.newExcursion);
        if (vacationId == -1) {
            fab.setVisibility(View.INVISIBLE);
        } else {
            fab.setOnClickListener(view -> {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vacationId", vacationId);
                startActivity(intent);
            });
        }

        nameEditText = findViewById(R.id.nameText);
        lodgingEditText = findViewById(R.id.lodgingText);
        startDateEditText = findViewById(R.id.startDate);
        endDateEditText = findViewById(R.id.endDate);

        nameEditText.setText(getIntent().getStringExtra("title"));
        lodgingEditText.setText(getIntent().getStringExtra("lodging"));
        startDateEditText.setText(getIntent().getStringExtra("startDate"));
        endDateEditText.setText(getIntent().getStringExtra("endDate"));

        startDateEditText.setOnClickListener(view -> Utils.showDatePickerDialog(this, startDateEditText));
        endDateEditText.setOnClickListener(view -> Utils.showDatePickerDialog(this, endDateEditText));

        recyclerView = findViewById(R.id.excursionRecyclerView);
        repository = new Repository(getApplication());

        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this, vacationId);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        excursionAdapter.setExcursions(repository.getAssociatedExcursions(vacationId));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // NOTE: I don't like how I handle some toasts in helper methods and some in the onOptionsItemSelected.
        //       Will I do anything about it? Maybe not.
        if (item.getItemId() == R.id.save) {
            if (vacationId == -1) {
                insertVacation();
            } else {
                updateVacation(vacationId);
            }
        } else if (item.getItemId() == R.id.delete) {
            if (vacationId != -1) {
                deleteVacation(vacationId);
            } else {
                Toast.makeText(this, "Cannot delete vacation that doesn't exist!", Toast.LENGTH_SHORT).show();
            }
        } else if (item.getItemId() == R.id.share) {
            if (vacationId != -1) {
                shareVacation();
            } else {
                Toast.makeText(this, "Cannot share vacation that doesn't exist!", Toast.LENGTH_SHORT).show();
            }
        } else if (item.getItemId() == R.id.remindVacation) {
            if (vacationId != -1) {
                setVacationReminder();
                Toast.makeText(this, "Vacation reminder set!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Cannot set a reminder for a vacation that doesn't exist!", Toast.LENGTH_SHORT).show();
            }
        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this, vacationId);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        excursionAdapter.setExcursions(repository.getAssociatedExcursions(vacationId));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void shareVacation() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);

        final String title = nameEditText.getText().toString();
        final String lodging = lodgingEditText.getText().toString();
        final String startDate = startDateEditText.getText().toString();
        final String endDate = endDateEditText.getText().toString();

        final String vacationDetails = "Vacation Name: " + title + System.lineSeparator()
                + "Lodging: " + lodging + System.lineSeparator()
                + "Dates: " + startDate + " to " + endDate;

        final StringBuilder excursionDetails = new StringBuilder();
        for (final Excursion excursion : repository.getAssociatedExcursions(vacationId))
            excursionDetails.append(excursion.getName()).append(" on ").append(excursion.getDate())
                    .append(System.lineSeparator());
        excursionDetails.delete(excursionDetails.length() - 1, excursionDetails.length());

        sendIntent.putExtra(Intent.EXTRA_SUBJECT, title + " from " + startDate + " to " + endDate);
        sendIntent.putExtra(Intent.EXTRA_TEXT, vacationDetails + System.lineSeparator() + System.lineSeparator()
                + "Excursions: " + System.lineSeparator() + excursionDetails);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    private void setVacationReminder() {
        final Date vacationStart = Utils.parseDate(startDateEditText.getText().toString());
        final Date vacationEnd = Utils.parseDate(endDateEditText.getText().toString());
        final Long triggerStartOfVacation = vacationStart.getTime();
        final Long triggerEndOfVacation = vacationEnd.getTime();

        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        final Intent startIntent = new Intent(VacationDetails.this, MyReceiver.class);
        if (vacationStart.before(new Date())) {
            startIntent.putExtra("reminderMessage", nameEditText.getText().toString()
                    + " has already started!");
        } else {
            startIntent.putExtra("reminderMessage", nameEditText.getText().toString()
                    + " starts today!");
        }
        final PendingIntent startSender = PendingIntent.getBroadcast(VacationDetails.this,
                ++MainActivity.numberOfAlerts, startIntent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerStartOfVacation, startSender);

        final Intent endIntent = new Intent(VacationDetails.this, MyReceiver.class);
        endIntent.putExtra("reminderMessage", nameEditText.getText().toString()
                + " ends today!");
        final PendingIntent endSender = PendingIntent.getBroadcast(VacationDetails.this,
                ++MainActivity.numberOfAlerts, endIntent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerEndOfVacation, endSender);
    }

    private void insertVacation() {
        String title = nameEditText.getText().toString();
        String lodging = lodgingEditText.getText().toString();
        final String startDate = startDateEditText.getText().toString();
        final String endDate = endDateEditText.getText().toString();

        if (title.isEmpty()) {
            title = "No Title";
        }
        if (lodging.isEmpty()) {
            lodging = "No Lodging";
        }

        final Vacation vacation = new Vacation(title, lodging, startDate, endDate);
        if (validateDates(vacation)) {
            repository.insert(vacation);
            Toast.makeText(this, "Vacation created", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void updateVacation(final int vacationId) {
        String title = nameEditText.getText().toString();
        String lodging = lodgingEditText.getText().toString();
        final String startDate = startDateEditText.getText().toString();
        final String endDate = endDateEditText.getText().toString();

        if (title.isEmpty()) {
            title = "No Title";
        }
        if (lodging.isEmpty()) {
            lodging = "No Lodging";
        }

        final Vacation vacation = new Vacation(vacationId, title, lodging, startDate, endDate);
        if (validateDates(vacation)) {
            repository.update(vacation);
            Toast.makeText(this, "Vacation updated", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void deleteVacation(final int vacationId) {
        if (!repository.getAssociatedExcursions(vacationId).isEmpty()) {
            Toast.makeText(VacationDetails.this, "Cannot delete vacation with associated excursions!", Toast.LENGTH_LONG).show();
            return;
        }

        repository.deleteVacationById(vacationId);
        Toast.makeText(this, "Vacation deleted", Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean validateDates(final Vacation vacation) {
        final Date vacationStart = Utils.parseDate(vacation.getStartDate());
        final Date vacationEnd = Utils.parseDate(vacation.getEndDate());

        if (vacationEnd.before(vacationStart)) {
            Toast.makeText(VacationDetails.this, "Vacation start date must be before end date!", Toast.LENGTH_LONG).show();
            return false;
        }

        final List<Excursion> excursions = repository.getAssociatedExcursions(vacation.getId());
        for (final Excursion excursion : excursions) {
            final Date excursionDate = Utils.parseDate(excursion.getDate());
            if (excursionDate.before(vacationStart) || excursionDate.after(vacationEnd)) {
                Toast.makeText(VacationDetails.this, "One or more excursions are outside of vacation range!", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return true;
    }
}