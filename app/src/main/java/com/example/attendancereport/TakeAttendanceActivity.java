package com.example.attendancereport;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TakeAttendanceActivity extends AppCompatActivity {

    ListView listView;
    Button btnSubmit;
    DBHelper db;
    ArrayList<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);

        listView = findViewById(R.id.listView);
        btnSubmit = findViewById(R.id.btnSubmit);
        db = new DBHelper(this);
        studentList = db.getAllStudents();

        ArrayList<String> displayList = new ArrayList<>();
        for (Student s : studentList) {
            displayList.add(s.name + " (" + s.roll + ")");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, displayList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // Initially, no item is checked — All Absent

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                boolean alreadyMarked = false;

                for (int i = 0; i < studentList.size(); i++) {
                    Student s = studentList.get(i);

                    // Check for duplicates
                    if (db.isAttendanceMarked(s.id, date)) {
                        alreadyMarked = true;
                        continue;
                    }

                    String status = listView.isItemChecked(i) ? "Present" : "Absent";
                    db.markAttendance(s.id, date, status);
                }

                if (alreadyMarked) {
                    Toast.makeText(TakeAttendanceActivity.this, "Some attendance already submitted. Duplicates skipped.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(TakeAttendanceActivity.this, "Attendance Submitted", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });
    }
}
