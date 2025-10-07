package com.example.nanapiyasa;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class StudentAdapter extends BaseAdapter {

    Activity context;
    ArrayList<Student> list;
    DatabaseHelper db;

    public StudentAdapter(Activity context, ArrayList<Student> list, DatabaseHelper db) {
        this.context = context;
        this.list = list;
        this.db = db;
    }

    @Override
    public int getCount() { return list.size(); }

    @Override
    public Object getItem(int i) { return list.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false);
        }

        TextView txtName = view.findViewById(R.id.txtName);
        TextView txtPhone = view.findViewById(R.id.txtPhone);
        Button btnToggle = view.findViewById(R.id.btnToggle);

        Student s = list.get(i);

        txtName.setText(s.getName());
        txtPhone.setText(s.getPhone());
        btnToggle.setText(s.isPaid() ? "Paid" : "Not Paid");

        btnToggle.setOnClickListener(v -> {
            boolean newStatus = !s.isPaid();
            db.updatePaymentStatus(s.getId(), newStatus);
            s = new Student(s.getId(), s.getName(), s.getPhone(), newStatus);
            list.set(i, s);
            notifyDataSetChanged();
        });

        return view;
    }
}
