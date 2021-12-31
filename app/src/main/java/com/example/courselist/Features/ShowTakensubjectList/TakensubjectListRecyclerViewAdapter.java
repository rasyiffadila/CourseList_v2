package com.example.courselist.Features.ShowTakensubjectList;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.courselist.Database.DatabaseQueryClass;
import com.example.courselist.Features.CreateSubject.Subject;
import com.example.courselist.Features.CreateTakensubject.Takensubject;
import com.example.courselist.R;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import java.util.List;

public class TakensubjectListRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private Context context;
    private List<Takensubject> takensubjectList;
    DatabaseQueryClass databaseQueryClass;

    public TakensubjectListRecyclerViewAdapter(Context context, List<Takensubject> takensubjectList) {
        this.context = context;
        this.takensubjectList = takensubjectList;
        databaseQueryClass = new DatabaseQueryClass(context);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.takensubject_item, parent, false);


        return new CustomViewHolder(view);
    }
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final int itemPosition = position;
        final Takensubject takensubject = takensubjectList.get(position);
        List<Subject> taken = databaseQueryClass.getTakensubject(takensubject.getStudent_id());
        final int takenPosition = position;
        final Subject TSubject = taken.get(takenPosition);
        holder.subjectNameTextView.setText(TSubject.getSubject_name());
        holder.subjectCodeTextView.setText(Long.toString(TSubject.getSubject_code()));
        holder.subjectCreditTextView.setText(Long.toString(TSubject.getSubject_credit()));
        holder.crossButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete this taken subject?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteTakensubject(itemPosition);
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

    }
    private void deleteTakensubject(int position) {
        Takensubject takensubject = takensubjectList.get(position);
        long count = databaseQueryClass.deleteSubjectByFid(takensubject.getStudent_id(), takensubject.getSubject_id());

        if(count>0){
            takensubjectList.remove(position);
            notifyDataSetChanged();
            ((TakensubjectListActivity) context).viewVisibility();
            ((TakensubjectListActivity) context).reloadStatus();
            Toast.makeText(context, "Taken Subject deleted successfully", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(context, "Taken Subject not deleted. Something wrong!", Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return takensubjectList.size();
    }
}
