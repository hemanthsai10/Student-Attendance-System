package com.example.attendancereport;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ViewAttendanceActivity extends AppCompatActivity {

    EditText etDate;
    Button btnView;
    TextView tvResult;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        etDate = findViewById(R.id.etDate);
        btnView = findViewById(R.id.btnView);
        tvResult = findViewById(R.id.tvResult);
        db = new DBHelper(this);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = etDate.getText().toString().trim();

                if (date.isEmpty()) {
                    Toast.makeText(ViewAttendanceActivity.this, "Enter a date", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<String> results = db.getAttendanceByDate(date);

                if (results.isEmpty()) {
                    tvResult.setText("No attendance records found.");
                } else {
                    tvResult.setText(TextUtils.join("\n", results));
                }
            }
        });
    }
}
