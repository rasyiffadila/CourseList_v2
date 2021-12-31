package com.example.courselist.Features.CreateTakensubject;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.courselist.Database.DatabaseQueryClass;
import com.example.courselist.Features.CreateSubject.Subject;
import com.example.courselist.Util.Config;
import com.example.courselist.R;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class TakensubjectCheckboxDialogFragment extends DialogFragment implements MyListener{
    private static TakensubjectCheckboxListener takensubjectCheckboxListener;
    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(getContext());
    private Button updateButton;
    private Button cancelButton;
    RecyclerView takensubjectcheckboxRecyclerView;
    private List<Subject> subjectList = new ArrayList<>();
    private ArrayList<Long> takensubjectList;
    MyAdapter adapter;
    private TextView emptySubjectListTextView;

    public TakensubjectCheckboxDialogFragment() {
        // Required empty public constructor
    }
    public static TakensubjectCheckboxDialogFragment newInstance(String title, TakensubjectCheckboxListener listener){
        takensubjectCheckboxListener = listener;
        TakensubjectCheckboxDialogFragment takensubjectCheckboxDialogFragment = new TakensubjectCheckboxDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        takensubjectCheckboxDialogFragment.setArguments(args);

        takensubjectCheckboxDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return takensubjectCheckboxDialogFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_takensubject_checkbox_dialog, container, false);
        subjectList.addAll(databaseQueryClass.getAllSubject());
        String title = getArguments().getString(Config.TITLE);
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);
        getDialog().getWindow().setLayout(width, height);
        getDialog().setTitle("Subject Assign to Student");
        emptySubjectListTextView = view.findViewById(R.id.emptySubjectListTextView);
        takensubjectcheckboxRecyclerView = view.findViewById(R.id.takensubjectcheckboxRecyclerView);
        updateButton = view.findViewById(R.id.updateButton);
        cancelButton = view.findViewById(R.id.cancelButton);
        if (subjectList.isEmpty() == true){
            emptySubjectListTextView.setVisibility(View.VISIBLE);
            updateButton.setVisibility(View.GONE);
        }
        takensubjectcheckboxRecyclerView.setHasFixedSize(true);
        takensubjectcheckboxRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        Bundle bundle = getActivity().getIntent().getExtras();
        long student_id = Long.parseLong(bundle.getString("studentID"));
        adapter = new MyAdapter(this.getActivity(), subjectList, this, student_id);
        takensubjectcheckboxRecyclerView.setAdapter(adapter);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(getContext());
                databaseQueryClass.updateTakensubjectInfo(takensubjectList, student_id);
                takensubjectCheckboxListener.onTakensubjectCreated(takensubjectList);
                getDialog().dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = (int)(getResources().getDisplayMetrics().widthPixels*0.95);
            int height = (int)(getResources().getDisplayMetrics().heightPixels*0.95);
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onMyChange(ArrayList<Long> takensubjectList) {
        this.takensubjectList = takensubjectList;
    }
}
