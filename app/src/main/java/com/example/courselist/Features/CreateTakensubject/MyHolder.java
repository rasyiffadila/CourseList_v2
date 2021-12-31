package com.example.courselist.Features.CreateTakensubject;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courselist.R;

public class MyHolder extends RecyclerView.ViewHolder {
    CheckBox checkBox;
    TextView takensubjectNameTextView;
    TextView takensubjectCodeTextView;
    TextView takensubjectCreditTextView;
    public MyHolder(@NonNull View itemView) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.checkbox);
        takensubjectNameTextView = itemView.findViewById(R.id.takensubjectNameTextView);
        takensubjectCodeTextView = itemView.findViewById(R.id.takensubjectCodeTextView);
        takensubjectCreditTextView = itemView.findViewById(R.id.takensubjectCreditTextView);
    }
}
