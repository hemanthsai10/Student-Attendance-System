package com.example.attendancereport;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "AttendanceDB", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE students(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, roll TEXT)");
        db.execSQL("CREATE TABLE attendance(id INTEGER PRIMARY KEY AUTOINCREMENT, student_id INTEGER, date TEXT, status TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS students");
        db.execSQL("DROP TABLE IF EXISTS attendance");
        onCreate(db);
    }
    public boolean insertStudent(String name, String roll) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("roll", roll);
        long res = db.insert("students", null, cv);
        return res != -1;
    }
    public void markAttendance(int studentId, String date, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("student_id", studentId);
        cv.put("date", date);
        cv.put("status", status);
        db.insert("attendance", null, cv);
    }
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM students", null);
        while (c.moveToNext()) {
            list.add(new Student(c.getInt(0), c.getString(1), c.getString(2)));
        }
        c.close();
        return list;
    }
    public boolean isAttendanceMarked(int studentId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM attendance WHERE student_id = ? AND date = ?", new String[]{String.valueOf(studentId), date});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public List<String> getAttendanceByDate(String date) {
        List<String> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT s.name, s.roll, a.status FROM attendance a JOIN students s ON s.id = a.student_id WHERE a.date = ?", new String[]{date});
        while (c.moveToNext()) {
            result.add(c.getString(0) + " (" + c.getString(1) + "): " + c.getString(2));
        }
        c.close();
        return result;
    }
    public boolean deleteStudent(String rollNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete("students", "roll = ?", new String[]{rollNumber});
        return deletedRows > 0;
    }
    public void exportAttendanceToPDF(Context context) {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
        File file = new File(path, "AttendanceReport.pdf");
        try {
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Attendance Report"));

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT s.name, s.roll, a.date, a.status FROM attendance a JOIN students s ON s.id = a.student_id", null);
            while (c.moveToNext()) {
                String line = c.getString(0) + " (" + c.getString(1) + ") - " + c.getString(2) + ": " + c.getString(3);
                document.add(new Paragraph(line));
            }
            c.close();
            document.close();

            Toast.makeText(context, "PDF Exported", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error exporting PDF", Toast.LENGTH_SHORT).show();
        }
    }
}