package com.madhav.maheshwari.customnavigationdrawer.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.madhav.maheshwari.customnavigationdrawer.Adapter.MyAdapter;
import com.madhav.maheshwari.customnavigationdrawer.Database.Data;
import com.madhav.maheshwari.customnavigationdrawer.Database.Database;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.madhav.maheshwari.customnavigationdrawer.MaterialDialog;
import com.madhav.maheshwari.customnavigationdrawer.R;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton add;
    private MyAdapter adapter;
    MaterialDialog mAnimatedDialog;
    private Database database;
    Activity activity;

    public HomeFragment(Activity activity){
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = v.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        add = v.findViewById(R.id.add_Field);
        database = Room.databaseBuilder(getContext(), Database.class, "attendance_record")
                .allowMainThreadQueries().build();
        setData();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAnimatedDialog = new MaterialDialog.Builder(activity)
                        .setCancelable(true)
                        .setPositiveButton("Submit", R.drawable.ic_tick_one, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                performCheck(dialogInterface);

                            }
                        })
                        .setAnimation("lottie_animation.json")
                        .build();
                mAnimatedDialog.show();
            }
        });
        return v;
    }

    private void setData() {
        ArrayList<Data> arrayList = new ArrayList<>(database.getDataDao().getAllrecord());
        adapter = new MyAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);
    }

    private void performCheck(DialogInterface dialogInterface) {
        final EditText attended_dialog, total_dialog, subName_dialog;

        attended_dialog = mAnimatedDialog.dialogView.findViewById(R.id.attended_lecture);
        total_dialog = mAnimatedDialog.dialogView.findViewById(R.id.total_lecture);
        subName_dialog = mAnimatedDialog.dialogView.findViewById(R.id.subject_name);
        try {
            int attended = Integer.parseInt(attended_dialog.getText().toString());
            int total = Integer.parseInt(total_dialog.getText().toString());
            String name = subName_dialog.getText().toString();
            List<String> nList = database.getDataDao().getAllNames();
            boolean flag = false;
            for (String nListSubName : nList) {
                if (nListSubName.equalsIgnoreCase(name)) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                subName_dialog.setError("Change subject name");
            }
            else if (subName_dialog.getText().toString().isEmpty()) {
                subName_dialog.setError("Enter subject name");
            }
            else if (attended <= total) {
                Data data = new Data(name, attended, total);
                long value = database.getDataDao().addAttendance(data);
                if (value > 0) {
                   // Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Try Again", Toast.LENGTH_SHORT).show();
                }
                setData();
                adapter.notifyDataSetChanged();
                dialogInterface.dismiss();
            }
            else
                attended_dialog.setError("Not more than total lectures");
        } catch (Exception e) {
            if (attended_dialog.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Try Again",
                        Toast.LENGTH_SHORT).show();
            }
            if (total_dialog.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Try Again",
                        Toast.LENGTH_SHORT).show();
            }
            if (subName_dialog.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Try Again",
                        Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getActivity(), "" + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
        }
    }
}