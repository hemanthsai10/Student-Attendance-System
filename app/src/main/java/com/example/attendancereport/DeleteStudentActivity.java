package com.example.attendancereport;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteStudentActivity extends AppCompatActivity {

    EditText etRollToDelete;
    Button btnDelete;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_student);

        etRollToDelete = findViewById(R.id.etRollToDelete);
        btnDelete = findViewById(R.id.btnDelete);
        db = new DBHelper(this);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String roll = etRollToDelete.getText().toString().trim();
                if (roll.isEmpty()) {
                    Toast.makeText(DeleteStudentActivity.this, "Enter roll number", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean deleted = db.deleteStudent(roll);
                if (deleted) {
                    Toast.makeText(DeleteStudentActivity.this, "Student deleted", Toast.LENGTH_SHORT).show();
                    etRollToDelete.setText("");
                } else {
                    Toast.makeText(DeleteStudentActivity.this, "Student not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

