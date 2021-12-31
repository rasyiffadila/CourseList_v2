package com.example.courselist.Features.ShowSubjectList;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.courselist.Database.DatabaseQueryClass;
import com.example.courselist.Features.CreateSubject.Subject;
import com.example.courselist.Features.CreateSubject.SubjectCreateDialogFragment;
import com.example.courselist.Features.CreateSubject.SubjectCreateListener;
import com.example.courselist.R;
import com.example.courselist.Util.Config;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import java.util.List;


public class SubjectListActivity extends AppCompatActivity implements SubjectCreateListener {
    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

    private List<Subject> subjectList = new ArrayList<>();
    private TextView subjectListEmptyTextView;
    private RecyclerView recyclerView;
    private SubjectListRecyclerViewAdapter subjectListRecyclerViewAdapter;
    TextView numStudentTextView, numSubjectTextView, numTakenSubjectTextView;
    private DatabaseQueryClass db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Logger.addLogAdapter(new AndroidLogAdapter());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.subjectRecyclerView);
        subjectListEmptyTextView = (TextView) findViewById(R.id.emptySubjectListTextView);

        subjectList.addAll(databaseQueryClass.getAllSubject());

        subjectListRecyclerViewAdapter = new SubjectListRecyclerViewAdapter(this, subjectList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(subjectListRecyclerViewAdapter);

        viewVisibility();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSubjectCreateDialog();
            }
        });
        reloadStatus();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_subject, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }


    private void openSubjectCreateDialog() {
        SubjectCreateDialogFragment subjectCreateDialogFragment = SubjectCreateDialogFragment.newInstance("Create Subject", this);
        subjectCreateDialogFragment.show(getSupportFragmentManager(), Config.CREATE_SUBJECT);
    }

    @Override
    public void onSubjectCreated(Subject subject) {
        subjectList.add(subject);
        subjectListRecyclerViewAdapter.notifyDataSetChanged();
        viewVisibility();
        Logger.d(subject.getSubject_name());
        reloadStatus();
    }
    public void viewVisibility() {
        if(subjectList.isEmpty())
            subjectListEmptyTextView.setVisibility(View.VISIBLE);
        else
            subjectListEmptyTextView.setVisibility(View.GONE);
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