package com.example.attendancereport;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddStudentActivity extends AppCompatActivity {

    EditText etName, etRoll;
    Button btnSave;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        etName = findViewById(R.id.etName);
        etRoll = findViewById(R.id.etRoll);
        btnSave = findViewById(R.id.btnSave);
        db = new DBHelper(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString().trim();
                String roll = etRoll.getText().toString().trim();

                if (name.isEmpty() || roll.isEmpty()) {
                    Toast.makeText(AddStudentActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean inserted = db.insertStudent(name, roll);
                if (inserted) {
                    Toast.makeText(AddStudentActivity.this, "Student Added", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddStudentActivity.this, "Error Adding Student", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}