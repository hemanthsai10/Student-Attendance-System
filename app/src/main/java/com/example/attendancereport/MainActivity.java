package com.example.attendancereport;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnAddStudent, btnTakeAttendance, btnViewAttendance,btnDeleteStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddStudent = findViewById(R.id.btnAddStudent);
        btnTakeAttendance = findViewById(R.id.btnTakeAttendance);
        btnViewAttendance = findViewById(R.id.btnViewAttendance);
        btnDeleteStudent = findViewById(R.id.btnDeleteStudent);
        btnDeleteStudent.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, DeleteStudentActivity.class);
            startActivity(intent);
        });

        btnAddStudent.setOnClickListener(v -> startActivity(new Intent(this, AddStudentActivity.class)));
        btnTakeAttendance.setOnClickListener(v -> startActivity(new Intent(this, TakeAttendanceActivity.class)));
        btnViewAttendance.setOnClickListener(v -> startActivity(new Intent(this, ViewAttendanceActivity.class)));
    }
}
