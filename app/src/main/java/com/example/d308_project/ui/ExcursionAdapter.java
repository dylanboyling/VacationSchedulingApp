package com.example.d308_project.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308_project.R;
import com.example.d308_project.entities.Excursion;

import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {

    private final Context context;

    private int parentVacationId;

    private final LayoutInflater mInflater;

    private List<Excursion> mExcursions;

    public ExcursionAdapter(Context context, final int parentVacationId) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.parentVacationId = parentVacationId;
    }

    public class ExcursionViewHolder extends RecyclerView.ViewHolder {

        private final TextView excursionItemViewTitle;

        private final TextView excursionItemViewDate;

        public ExcursionViewHolder(@NonNull View itemView) {
            super(itemView);
            excursionItemViewTitle = itemView.findViewById(R.id.excursionName);
            excursionItemViewDate = itemView.findViewById(R.id.excursionDate);
            itemView.setOnClickListener(view -> {
                final int position = getAdapterPosition();
                final Excursion selectedExcursion = mExcursions.get(position);
                Intent intent = new Intent(context, ExcursionDetails.class);
                intent.putExtra("excursionId", selectedExcursion.getId());
                intent.putExtra("excursionName", selectedExcursion.getName());
                intent.putExtra("excursionDate", selectedExcursion.getDate());
                intent.putExtra("vacationId", parentVacationId);
                context.startActivity(intent);
            });
        }
    }

    @NonNull
    @Override
    public ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.excursion_list_item, parent, false);
        return new ExcursionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionAdapter.ExcursionViewHolder holder, int position) {
        if(mExcursions != null){
            final Excursion currentExcursion = mExcursions.get(position);
            holder.excursionItemViewTitle.setText(currentExcursion.getName());
            holder.excursionItemViewDate.setText(currentExcursion.getDate());
        } else {
            holder.excursionItemViewTitle.setText("No excursion title");
            holder.excursionItemViewDate.setText("No excursion ID");
        }
    }

    @Override
    public int getItemCount() {
        return mExcursions == null ? 0 : mExcursions.size();
    }

    public void setExcursions(final List<Excursion> excursions){
        mExcursions = excursions;
        notifyDataSetChanged();
    }
}
