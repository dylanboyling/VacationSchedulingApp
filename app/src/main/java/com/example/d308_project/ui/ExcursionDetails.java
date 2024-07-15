package com.example.d308_project.ui;

import static com.example.d308_project.Utils.sanitizeString;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.d308_project.R;
import com.example.d308_project.Utils;
import com.example.d308_project.database.Repository;
import com.example.d308_project.entities.Excursion;
import com.example.d308_project.entities.Vacation;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

public class ExcursionDetails extends AppCompatActivity {

    private Repository repository;

    private int excursionId;

    private int parentVacationId;

    private TextInputEditText nameEditText;

    private TextInputEditText dateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);

        excursionId = getIntent().getIntExtra("excursionId", -1);
        parentVacationId = getIntent().getIntExtra("vacationId", -1);

        nameEditText = findViewById(R.id.excursionName);
        dateEditText = findViewById(R.id.excursionDate);
        nameEditText.setText(getIntent().getStringExtra("excursionName"));
        dateEditText.setText(getIntent().getStringExtra("excursionDate"));
        dateEditText.setOnClickListener(view -> Utils.showDatePickerDialog(this, dateEditText));

        repository = new Repository(getApplication());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            if (excursionId == -1) {
                parentVacationId = getIntent().getIntExtra("vacationId", -1);
                insertExcursion(parentVacationId);
            } else {
                updateExcursion(excursionId, parentVacationId);
            }
        } else if (item.getItemId() == R.id.delete) {
            if (excursionId != -1) {
                deleteExcursion(excursionId);
                Toast.makeText(this, "Excursion reminder set!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Cannot delete an excursion that doesn't exist!", Toast.LENGTH_SHORT).show();
            }
        } else if (item.getItemId() == R.id.remindExcursion) {
            if (excursionId != -1) {
                setExcursionReminder();
                Toast.makeText(this, "Excursion reminder set!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Cannot set a reminder for an excursion that doesn't exist!", Toast.LENGTH_SHORT).show();
            }
        } else if (item.getItemId() == android.R.id.home) {
            this.finish();
        }

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void insertExcursion(final int parentVacationId) {
        String title = Utils.sanitizeString(nameEditText.getText().toString());
        String date = dateEditText.getText().toString();

        if (title.isEmpty()) {
            title = "No Title";
        }

        if (date.isEmpty()){
            Toast.makeText(ExcursionDetails.this, "Excursion date cannot be empty!", Toast.LENGTH_LONG).show();
            return;
        }

        if (validateDate()) {
            final Excursion excursion = new Excursion(title, date, parentVacationId);
            repository.insert(excursion);
            Toast.makeText(this, "Excursion created", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(ExcursionDetails.this, "Excursion start date must be "
                    + "between vacation start and end dates!", Toast.LENGTH_LONG).show();
        }
    }

    private void updateExcursion(final int excursionId, final int parentVacationId) {
        String title = Utils.sanitizeString(nameEditText.getText().toString());
        String date = dateEditText.getText().toString();

        if (title.isEmpty()) {
            title = "No Title";
        }

        if(date.isEmpty()){
            Toast.makeText(ExcursionDetails.this, "Excursion date cannot be empty!", Toast.LENGTH_LONG).show();
            return;
        }

        if (validateDate()) {
            final Excursion excursion = new Excursion(excursionId, title, date, parentVacationId);
            repository.insert(excursion);
            Toast.makeText(this, "Excursion updated", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(ExcursionDetails.this, "Excursion start date must be "
                    + "between vacation start and end dates!", Toast.LENGTH_LONG).show();
        }
    }

    private void deleteExcursion(final int excursionId) {
        repository.deleteExcursionById(excursionId);
        Toast.makeText(this, "Excursion deleted", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void setExcursionReminder(){
        final Date excursionDate = Utils.parseDate(dateEditText.getText().toString());
        final Long triggerExcursionStart = excursionDate.getTime();

        final Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
        intent.putExtra("reminderMessage", "Excursion " + nameEditText.getText().toString()
                + " starts today!");

        PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this,
                ++MainActivity.numberOfAlerts, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerExcursionStart, sender);
    }

    private boolean validateDate() {
        final Vacation parentVacation = repository.getVacationById(parentVacationId);

        final Date excursionDate = Utils.parseDate(dateEditText.getText().toString());
        final Date vacationStart = Utils.parseDate(parentVacation.getStartDate());
        final Date vacationEnd = Utils.parseDate(parentVacation.getEndDate());

        if (excursionDate.before(vacationStart) || (excursionDate.after(vacationEnd)))
            return false;

        return true;
    }
}
