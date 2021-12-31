package com.example.courselist.Features.ShowStudentList;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courselist.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    TextView nameTextView;
    TextView registrationNumTextView;
    TextView emailTextView;
    TextView phoneTextView;
    ImageView crossButtonImageView;
    ImageView editButtonImageView;
    ConstraintLayout studentCard;
    public CustomViewHolder(View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.nameTextView);
        registrationNumTextView = itemView.findViewById(R.id.registrationNumTextView);
        emailTextView = itemView.findViewById(R.id.emailTextView);
        phoneTextView = itemView.findViewById(R.id.phoneTextView);
        crossButtonImageView = itemView.findViewById(R.id.crossImageView);
        editButtonImageView = itemView.findViewById(R.id.editImageView);
        studentCard = itemView.findViewById(R.id.studentCard);
    }

}