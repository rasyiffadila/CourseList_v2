package com.example.courselist.Features.CreateTakensubject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courselist.Database.DatabaseQueryClass;
import com.example.courselist.Features.CreateSubject.Subject;
import com.example.courselist.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {
    View view;
    Context context;
    List<Subject> subjectList;

    MyListener myListener;
    ArrayList<Long> arrayList_0 = new ArrayList<>();
    long student_id;

    public MyAdapter(Context context, List<Subject> subjectList, MyListener myListener, long student_id) {
        this.context = context;
        this.subjectList = subjectList;
        this.myListener = myListener;
        this.student_id = student_id;
    }

    public View getView() {
        return view;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.takensubject_checkbox, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        DatabaseQueryClass databaseQueryClass;
        databaseQueryClass = new DatabaseQueryClass(view.getContext());
        List<Takensubject> taken = new ArrayList<>();
        taken.addAll(databaseQueryClass.getAllTakensubject(student_id));
        if (subjectList != null && subjectList.size() > 0) {
            final Subject subject = subjectList.get(position);
//            holder.checkBox.setText(subject.getSubject_name());
            holder.takensubjectNameTextView.setText(subject.getSubject_name());
            holder.takensubjectCodeTextView.setText(String.valueOf(subject.getSubject_code()));
            holder.takensubjectCreditTextView.setText(String.valueOf(subject.getSubject_credit()));

            if (taken != null && taken.size() > 0) {
                for (int i = 0; i < taken.size(); i++){
                    if (taken.get(i).getSubject_id() == subject.getId()){
                        holder.checkBox.setChecked(true);
                        arrayList_0.add(subject.getId());
                        myListener.onMyChange(arrayList_0);
                    }
                }
            }

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.checkBox.isChecked()){
                        arrayList_0.add(subject.getId());
                    }else {
                        arrayList_0.remove(subject.getId());
                    }
                    myListener.onMyChange(arrayList_0);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }
}
