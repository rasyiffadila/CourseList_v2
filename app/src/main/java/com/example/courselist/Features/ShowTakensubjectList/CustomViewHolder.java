package com.example.courselist.Features.ShowTakensubjectList;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.courselist.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    TextView subjectNameTextView;
    TextView subjectCodeTextView;
    TextView subjectCreditTextView;
    ImageView crossButtonImageView;

    public CustomViewHolder(View itemView) {
        super(itemView);
        subjectNameTextView = itemView.findViewById(R.id.takensubjectNameTextView);
        subjectCodeTextView = itemView.findViewById(R.id.takensubjectCodeTextView);
        subjectCreditTextView = itemView.findViewById(R.id.takensubjectCreditTextView);
        crossButtonImageView = itemView.findViewById(R.id.crossImageView);
    }

}