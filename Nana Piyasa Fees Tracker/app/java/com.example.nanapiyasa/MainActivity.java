package com.example.nanapiyasa;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    ArrayList<Student> studentList;
    StudentAdapter adapter;
    ListView listView;
    Button btnAdd, btnSendReminders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        listView = findViewById(R.id.listViewStudents);
        btnAdd = findViewById(R.id.btnAddStudent);
        btnSendReminders = findViewById(R.id.btnSendReminders);

        loadStudents();

        btnAdd.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, AddStudentActivity.class);
            startActivity(i);
        });

        btnSendReminders.setOnClickListener(v -> sendWhatsAppReminders());
    }

    private void loadStudents() {
        studentList = db.getAllStudents();
        adapter = new StudentAdapter(this, studentList, db);
        listView.setAdapter(adapter);
    }

    private void sendWhatsAppReminders() {
        ArrayList<Student> unpaidStudents = db.getUnpaidStudents();
        String message = "Please pay your tuition fees for this month. — Nana Piyasa Institute";

        for (Student s : unpaidStudents) {
            String phone = s.getPhone().replace("+", "").trim();
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse("https://wa.me/" + phone + "?text=" + Uri.encode(message)));
            startActivity(sendIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStudents();
    }
}
