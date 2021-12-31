package com.example.courselist.Features.ShowStudentList;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courselist.Database.DatabaseQueryClass;
import com.example.courselist.Features.CreateStudent.Student;
import com.example.courselist.Features.ShowTakensubjectList.TakensubjectListActivity;
import com.example.courselist.Features.UpdateStudentInfo.StudentUpdateDialogFragment;
import com.example.courselist.Features.UpdateStudentInfo.StudentUpdateListener;
import com.example.courselist.R;
import com.example.courselist.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

public class StudentListRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private Context context;
    private List<Student> studentList;
    private DatabaseQueryClass databaseQueryClass;

    public StudentListRecyclerViewAdapter(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
        databaseQueryClass = new DatabaseQueryClass(context);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_item, parent, false);
        return new CustomViewHolder(view);
    }
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final int itemPosition = position;
        final Student student = studentList.get(position);

        holder.nameTextView.setText(student.getName());
        holder.registrationNumTextView.setText(String.valueOf(student.getRegistrationNumber()));
        holder.phoneTextView.setText(student.getPhoneNumber());
        holder.emailTextView.setText(student.getEmail());

        holder.crossButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete this student?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteStudent(itemPosition);

                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        holder.editButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentUpdateDialogFragment studentUpdateDialogFragment = StudentUpdateDialogFragment.newInstance(student.getRegistrationNumber(), itemPosition, new StudentUpdateListener() {
                    @Override
                    public void onStudentInfoUpdated(Student student, int position) {
                        studentList.set(position, student);
                        notifyDataSetChanged();
                    }
                });
                studentUpdateDialogFragment.show(((StudentListActivity) context).getSupportFragmentManager(), Config.UPDATE_STUDENT);
            }
        });
        holder.studentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TakensubjectListActivity.class);
                String id_student = String.valueOf(student.getId());
                intent.putExtra("studentID", id_student);
                view.getContext().startActivity(intent);
            }
        });
    }
    private void deleteStudent(int position) {
        Student student = studentList.get(position);
        long count = databaseQueryClass.deleteStudentById(student.getId());
        if(count>0){
            studentList.remove(position);
            notifyDataSetChanged();
            ((StudentListActivity) context).viewVisibility();
            ((StudentListActivity) context).reloadStatus();
            Toast.makeText(context, "Student deleted successfully", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(context, "Student not deleted. Something wrong!", Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}
