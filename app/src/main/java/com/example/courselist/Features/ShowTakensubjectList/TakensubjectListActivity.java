package com.example.courselist.Features.ShowTakensubjectList;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.courselist.Database.DatabaseQueryClass;
import com.example.courselist.Features.CreateStudent.Student;
import com.example.courselist.Features.CreateTakensubject.Takensubject;
import com.example.courselist.Features.CreateTakensubject.TakensubjectCheckboxDialogFragment;
import com.example.courselist.Features.CreateTakensubject.TakensubjectCheckboxListener;
import com.example.courselist.R;
import com.example.courselist.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import java.util.List;


public class TakensubjectListActivity extends AppCompatActivity implements TakensubjectCheckboxListener {
    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

    private List<Takensubject> takensubjectList = new ArrayList<>();

    private TextView takensubjectListEmptyTextView;
    private RecyclerView recyclerView;
    private TakensubjectListRecyclerViewAdapter takensubjectListRecyclerViewAdapter;
    TextView numStudentTextView, numSubjectTextView, numTakenSubjectTextView;

    private DatabaseQueryClass db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takensubject_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Logger.addLogAdapter(new AndroidLogAdapter());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.takensubjectRecyclerView);
        takensubjectListEmptyTextView = (TextView) findViewById(R.id.emptyTakenSubjectListTextView);
        Bundle extras = getIntent().getExtras();
        takensubjectList.addAll(databaseQueryClass.getAllTakensubject(Long.parseLong(extras.getString("studentID"))));


        takensubjectListRecyclerViewAdapter = new TakensubjectListRecyclerViewAdapter(this, takensubjectList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(takensubjectListRecyclerViewAdapter);


        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        TextView registrationNumTextView = (TextView) findViewById(R.id.registrationNumTextView);
        TextView phoneTextView = (TextView) findViewById(R.id.phoneTextView);
        TextView emailTextView = (TextView) findViewById(R.id.emailTextView);
        db = new DatabaseQueryClass(this);
        Student students = db.getStudentById(Long.parseLong(extras.getString("studentID")));
        nameTextView.setText(students.getName());
        registrationNumTextView.setText(String.valueOf(students.getRegistrationNumber()));
        phoneTextView.setText(String.valueOf(students.getPhoneNumber()));
        emailTextView.setText(String.valueOf(students.getEmail()));

        viewVisibility();

        Button add_button = (Button) findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTakensubjectCheckboxDialog();
            }
        });
        reloadStatus();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_takensubject, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }


    private void openTakensubjectCheckboxDialog() {
        TakensubjectCheckboxDialogFragment takensubjectCheckboxDialogFragment = TakensubjectCheckboxDialogFragment.newInstance("Create Taken Subject", this);
        takensubjectCheckboxDialogFragment.show(getSupportFragmentManager(), Config.CREATE_TAKEN_SUBJECT);

    }

    @Override
    public void onTakensubjectCreated(List<Long> takensubjectList) {
        Bundle extras = getIntent().getExtras();
        this.takensubjectList.clear();
//        this.databaseQueryClass = new DatabaseQueryClass(this);
        this.takensubjectList.addAll(databaseQueryClass.getAllTakensubject(Long.parseLong(extras.getString("studentID"))));
        takensubjectListRecyclerViewAdapter.notifyDataSetChanged();
        viewVisibility();
        reloadStatus();
    }
    public void viewVisibility() {
        if(takensubjectList.isEmpty())
            takensubjectListEmptyTextView.setVisibility(View.VISIBLE);
        else
            takensubjectListEmptyTextView.setVisibility(View.GONE);
    }
    public void reloadStatus(){
        numStudentTextView = (TextView) findViewById(R.id.numStudentTextView);
        numSubjectTextView = (TextView) findViewById(R.id.numSubjectTextView);
        numTakenSubjectTextView = (TextView) findViewById(R.id.numTakenSubjectTextView);
        db = new DatabaseQueryClass(this);
        numStudentTextView.setText(Integer.toString(db.studentCount()));
        numSubjectTextView.setText(Integer.toString(db.subjectCount()));
        numTakenSubjectTextView.setText(Integer.toString(db.takensubjectCount()));
    }
}