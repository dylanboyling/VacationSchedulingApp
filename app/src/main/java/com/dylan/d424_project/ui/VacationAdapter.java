package com.dylan.d424_project.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dylan.d424_capstone.R;
import com.dylan.d424_project.entities.Vacation;

import java.util.List;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder> {

    private final Context context;
    private final LayoutInflater mInflater;
    private List<Vacation> mVacations;

    public VacationAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class VacationViewHolder extends RecyclerView.ViewHolder {

        private final TextView vacationTitleView;
        private final TextView vacationDatesView;
        private final TextView vacationLodgingView;

        public VacationViewHolder(@NonNull View itemView) {
            super(itemView);
            vacationTitleView = itemView.findViewById(R.id.vacationName);
            vacationDatesView = itemView.findViewById(R.id.vacationDates);
            vacationLodgingView = itemView.findViewById(R.id.vacationLodging);

            itemView.setOnClickListener(view -> {
                final int position = getAdapterPosition();
                final Vacation selectedVacation = mVacations.get(position);
                Intent intent = new Intent(context, VacationDetails.class);
                intent.putExtra("vacationId", selectedVacation.getId());
                intent.putExtra("title", selectedVacation.getName());
                intent.putExtra("lodging", selectedVacation.getLodging());
                intent.putExtra("startDate", selectedVacation.getStartDate());
                intent.putExtra("endDate", selectedVacation.getEndDate());
                context.startActivity(intent);
            });
        }
    }

    @NonNull
    @Override
    public VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.vacation_list_item, parent, false);
        return new VacationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VacationViewHolder holder, int position) {
        if (mVacations != null) {
            final Vacation currentVacation = mVacations.get(position);
            holder.vacationTitleView.setText(currentVacation.getName());
            holder.vacationDatesView.setText(currentVacation.getStartDate() + " - " + currentVacation.getEndDate());
            holder.vacationLodgingView.setText(currentVacation.getLodging());
        } else {
            holder.vacationTitleView.setText("No vacations");
            holder.vacationDatesView.setText("");
            holder.vacationLodgingView.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return mVacations == null ? 0 : mVacations.size();
    }

    public void setVacations(final List<Vacation> vacations) {
        mVacations = vacations;
        notifyDataSetChanged();
    }
}
