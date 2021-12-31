package com.example.courselist.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
//import android.util.Config;
import android.widget.Toast;

import com.example.courselist.Features.CreateTakensubject.Takensubject;
import com.example.courselist.Util.Config;
import com.example.courselist.Features.CreateStudent.Student;
import com.example.courselist.Features.CreateSubject.Subject;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseQueryClass {
    private Context context;

    public DatabaseQueryClass(Context context){
        this.context = context;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public long insertStudent(Student student){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_STUDENT_NAME, student.getName());
        contentValues.put(Config.COLUMN_STUDENT_REGISTRATION, student.getRegistrationNumber());
        contentValues.put(Config.COLUMN_STUDENT_PHONE, student.getPhoneNumber());
        contentValues.put(Config.COLUMN_STUDENT_EMAIL, student.getEmail());

        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_STUDENT, null, contentValues);
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public List<Student> getAllStudent(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_STUDENT, null, null, null, null, null, null, null);

            /**
             // If you want to execute raw query then uncomment below 2 lines. And comment out above line.

             String SELECT_QUERY = String.format("SELECT %s, %s, %s, %s, %s FROM %s", Config.COLUMN_STUDENT_ID, Config.COLUMN_STUDENT_NAME, Config.COLUMN_STUDENT_REGISTRATION, Config.COLUMN_STUDENT_EMAIL, Config.COLUMN_STUDENT_PHONE, Config.TABLE_STUDENT);
             cursor = sqLiteDatabase.rawQuery(SELECT_QUERY, null);
             */

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<Student> studentList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_STUDENT_ID));
                        String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_NAME));
                        long registrationNumber = cursor.getLong(cursor.getColumnIndex(Config.COLUMN_STUDENT_REGISTRATION));
                        String email = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_EMAIL));
                        String phone = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_PHONE));

                        studentList.add(new Student(id, name, registrationNumber, phone, email));
                    }   while (cursor.moveToNext());

                    return studentList;
                }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public Student getStudentByRegNum(long registrationNum){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        Student student = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_STUDENT, null,
                    Config.COLUMN_STUDENT_REGISTRATION + " = ? ", new String[]{String.valueOf(registrationNum)},
                    null, null, null);

            /**
             // If you want to execute raw query then uncomment below 2 lines. And comment out above sqLiteDatabase.query() method.

             String SELECT_QUERY = String.format("SELECT * FROM %s WHERE %s = %s", Config.TABLE_STUDENT, Config.COLUMN_STUDENT_REGISTRATION, String.valueOf(registrationNum));
             cursor = sqLiteDatabase.rawQuery(SELECT_QUERY, null);
             */

            if(cursor.moveToFirst()){
                int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_STUDENT_ID));
                String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_NAME));
                long registrationNumber = cursor.getLong(cursor.getColumnIndex(Config.COLUMN_STUDENT_REGISTRATION));
                String phone = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_PHONE));
                String email = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_EMAIL));

                student = new Student(id, name, registrationNumber, phone, email);
            }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return student;
    }

    public Student getStudentById(long student_id){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        Student student = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_STUDENT, null,
                    Config.COLUMN_STUDENT_ID + " = ? ", new String[]{String.valueOf(student_id)},
                    null, null, null);

            /**
             // If you want to execute raw query then uncomment below 2 lines. And comment out above sqLiteDatabase.query() method.

             String SELECT_QUERY = String.format("SELECT * FROM %s WHERE %s = %s", Config.TABLE_STUDENT, Config.COLUMN_STUDENT_REGISTRATION, String.valueOf(registrationNum));
             cursor = sqLiteDatabase.rawQuery(SELECT_QUERY, null);
             */

            if(cursor.moveToFirst()){
                int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_STUDENT_ID));
                String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_NAME));
                long registrationNumber = cursor.getLong(cursor.getColumnIndex(Config.COLUMN_STUDENT_REGISTRATION));
                String phone = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_PHONE));
                String email = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_EMAIL));

                student = new Student(id, name, registrationNumber, phone, email);
            }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return student;
    }

    public long updateStudentInfo(Student student){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_STUDENT_NAME, student.getName());
        contentValues.put(Config.COLUMN_STUDENT_REGISTRATION, student.getRegistrationNumber());
        contentValues.put(Config.COLUMN_STUDENT_PHONE, student.getPhoneNumber());
        contentValues.put(Config.COLUMN_STUDENT_EMAIL, student.getEmail());

        try {
            rowCount = sqLiteDatabase.update(Config.TABLE_STUDENT, contentValues,
                    Config.COLUMN_STUDENT_ID + " = ? ",
                    new String[] {String.valueOf(student.getId())});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    public long deleteStudentById(long student_id) {
        long deletedRowCount = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        try {
            deletedRowCount = sqLiteDatabase.delete(Config.TABLE_TAKEN_SUBJECT,
                    Config.COLUMN_STUDENT_ID_TAKEN + " = ?", new String[]{String.valueOf(student_id)});
            deletedRowCount = sqLiteDatabase.delete(Config.TABLE_STUDENT,
                    Config.COLUMN_STUDENT_ID + " = ? ",
                    new String[]{ String.valueOf(student_id)});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deletedRowCount;
    }

    public boolean deleteAllStudents(){
        boolean deleteStatus = false;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            //for "1" delete() method returns number of deleted rows
            //if you don't want row count just use delete(TABLE_NAME, null, null)
            sqLiteDatabase.delete(Config.TABLE_STUDENT, null, null);

            long count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_STUDENT);

            if(count==0)
                deleteStatus = true;

        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deleteStatus;
    }



//    SUBJECT

    public long insertSubject(Subject subject){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_SUBJECT_NAME, subject.getSubject_name());
        contentValues.put(Config.COLUMN_SUBJECT_CODE, subject.getSubject_code());
        contentValues.put(Config.COLUMN_SUBJECT_CREDIT, subject.getSubject_credit());

        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_SUBJECT, null, contentValues);
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public List<Subject> getAllSubject(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_SUBJECT, null, null, null, null, null, null, null);

            /**
             // If you want to execute raw query then uncomment below 2 lines. And comment out above line.

             String SELECT_QUERY = String.format("SELECT %s, %s, %s, %s, %s FROM %s", Config.COLUMN_STUDENT_ID, Config.COLUMN_STUDENT_NAME, Config.COLUMN_STUDENT_REGISTRATION, Config.COLUMN_STUDENT_EMAIL, Config.COLUMN_STUDENT_PHONE, Config.TABLE_STUDENT);
             cursor = sqLiteDatabase.rawQuery(SELECT_QUERY, null);
             */

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<Subject> subjectList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_SUBJECT_ID));
                        String subject_name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_SUBJECT_NAME));
                        long subject_code = cursor.getLong(cursor.getColumnIndex(Config.COLUMN_SUBJECT_CODE));
                        long subject_credit = cursor.getLong(cursor.getColumnIndex(Config.COLUMN_SUBJECT_CREDIT));
                        subjectList.add(new Subject(id, subject_name, subject_code, subject_credit));
                    }   while (cursor.moveToNext());

                    return subjectList;
                }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public Subject getSubjectByCode(long code){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        Subject subject = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_SUBJECT, null,
                    Config.COLUMN_SUBJECT_CODE + " = ? ", new String[]{String.valueOf(code)},
                    null, null, null);

            /**
             // If you want to execute raw query then uncomment below 2 lines. And comment out above sqLiteDatabase.query() method.

             String SELECT_QUERY = String.format("SELECT * FROM %s WHERE %s = %s", Config.TABLE_STUDENT, Config.COLUMN_STUDENT_REGISTRATION, String.valueOf(registrationNum));
             cursor = sqLiteDatabase.rawQuery(SELECT_QUERY, null);
             */

            if(cursor.moveToFirst()){
                int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_SUBJECT_ID));
                String subject_name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_SUBJECT_NAME));
                long subject_code = cursor.getLong(cursor.getColumnIndex(Config.COLUMN_SUBJECT_CODE));
                long subject_credit = cursor.getLong(cursor.getColumnIndex(Config.COLUMN_SUBJECT_CREDIT));

                subject = new Subject(id, subject_name, subject_code, subject_credit);
            }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return subject;
    }
    public long updateSubjectInfo(Subject subject){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_SUBJECT_NAME, subject.getSubject_name());
        contentValues.put(Config.COLUMN_SUBJECT_CODE, subject.getSubject_code());
        contentValues.put(Config.COLUMN_SUBJECT_CREDIT, subject.getSubject_credit());

        try {
            rowCount = sqLiteDatabase.update(Config.TABLE_SUBJECT, contentValues,
                    Config.COLUMN_SUBJECT_ID + " = ? ",
                    new String[] {String.valueOf(subject.getId())});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    public long deleteSubjectById(long subject_id) {
        long deletedRowCount = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            deletedRowCount = sqLiteDatabase.delete(Config.TABLE_TAKEN_SUBJECT,
                    Config.COLUMN_SUBJECT_ID_TAKEN + " = ?", new String[]{String.valueOf(subject_id)});
            deletedRowCount = sqLiteDatabase.delete(Config.TABLE_SUBJECT,
                    Config.COLUMN_SUBJECT_ID + " = ? ",
                    new String[]{ String.valueOf(subject_id)});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deletedRowCount;
    }

    public boolean deleteAllSubjects(){
        boolean deleteStatus = false;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            //for "1" delete() method returns number of deleted rows
            //if you don't want row count just use delete(TABLE_NAME, null, null)
            sqLiteDatabase.delete(Config.TABLE_SUBJECT, null, null);

            long count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_SUBJECT);

            if(count==0)
                deleteStatus = true;

        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deleteStatus;
    }


//    TAKENSUBJECT
public long insertTakensubject(Takensubject takensubject){

    long id = -1;
    DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
    SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

    ContentValues contentValues = new ContentValues();
    contentValues.put(Config.COLUMN_STUDENT_ID_TAKEN, takensubject.getStudent_id());
    contentValues.put(Config.COLUMN_SUBJECT_ID_TAKEN, takensubject.getSubject_id());

    try {
        id = sqLiteDatabase.insertOrThrow(Config.TABLE_TAKEN_SUBJECT, null, contentValues);
    } catch (SQLiteException e){
        Logger.d("Exception: " + e.getMessage());
        Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
    } finally {
        sqLiteDatabase.close();
    }

    return id;
}

    public List<Takensubject> getAllTakensubject(long id_student){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.rawQuery("SELECT * FROM taken_subject WHERE student_id = ?", new String[]{String.valueOf(id_student)});

            /**
             // If you want to execute raw query then uncomment below 2 lines. And comment out above line.

             String SELECT_QUERY = String.format("SELECT %s, %s, %s, %s, %s FROM %s", Config.COLUMN_STUDENT_ID, Config.COLUMN_STUDENT_NAME, Config.COLUMN_STUDENT_REGISTRATION, Config.COLUMN_STUDENT_EMAIL, Config.COLUMN_STUDENT_PHONE, Config.TABLE_STUDENT);
             cursor = sqLiteDatabase.rawQuery(SELECT_QUERY, null);
             */

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<Takensubject> TakensubjectList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TAKEN_SUBJECT_ID));
                        int student_id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_STUDENT_ID_TAKEN));
                        int subject_id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_SUBJECT_ID_TAKEN));
                        TakensubjectList.add(new Takensubject(id, student_id, subject_id));
                    }   while (cursor.moveToNext());

                    return TakensubjectList;
                }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public List<Subject> getTakensubject(long student_id){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.rawQuery("SELECT subject._id, subject.subject_name, subject.subject_code, subject.subject_credit FROM subject, taken_subject WHERE subject._id = taken_subject.subject_id AND taken_subject.student_id = ?", new String[]{String.valueOf(student_id)});

            /**
             // If you want to execute raw query then uncomment below 2 lines. And comment out above line.

             String SELECT_QUERY = String.format("SELECT %s, %s, %s, %s, %s FROM %s", Config.COLUMN_STUDENT_ID, Config.COLUMN_STUDENT_NAME, Config.COLUMN_STUDENT_REGISTRATION, Config.COLUMN_STUDENT_EMAIL, Config.COLUMN_STUDENT_PHONE, Config.TABLE_STUDENT);
             cursor = sqLiteDatabase.rawQuery(SELECT_QUERY, null);
             */


            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<Subject> takensubjectList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_SUBJECT_ID));
                        String subject_name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_SUBJECT_NAME));
                        long subject_code = cursor.getLong(cursor.getColumnIndex(Config.COLUMN_SUBJECT_CODE));
                        long subject_credit = cursor.getLong(cursor.getColumnIndex(Config.COLUMN_SUBJECT_CREDIT));
                        takensubjectList.add(new Subject(id, subject_name, subject_code, subject_credit));
                    }   while (cursor.moveToNext());

                    return takensubjectList;
                }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();

    }

    public long updateTakensubjectInfo(List<Long> subject_id, long student_id){

        long rowCount = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        try {
            if (subject_id.size() > 0) {
                rowCount = sqLiteDatabase.delete(Config.TABLE_TAKEN_SUBJECT,
                        Config.COLUMN_STUDENT_ID_TAKEN + " = ?", new String[]{String.valueOf(student_id)});
                for (int i = 0; i < subject_id.size(); i++){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(Config.COLUMN_STUDENT_ID_TAKEN, student_id);
                    contentValues.put(Config.COLUMN_SUBJECT_ID_TAKEN, subject_id.get(i));
                    rowCount = sqLiteDatabase.insertOrThrow(Config.TABLE_TAKEN_SUBJECT, null, contentValues);
                }
            }else {
                rowCount = sqLiteDatabase.delete(Config.TABLE_TAKEN_SUBJECT,
                        Config.COLUMN_STUDENT_ID_TAKEN + " = ?", new String[]{String.valueOf(student_id)});
            }
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
        return rowCount;
    }

    public long deleteSubjectByFid(long student_id, long subject_id) {
        long deletedRowCount = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            deletedRowCount = sqLiteDatabase.delete(Config.TABLE_TAKEN_SUBJECT,
                    Config.COLUMN_STUDENT_ID_TAKEN + " = ?" + " AND " + Config.COLUMN_SUBJECT_ID_TAKEN + " = ?", new String[]{String.valueOf(student_id), String.valueOf(subject_id)});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deletedRowCount;
    }


    public int studentCount(){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM student", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        return count;
    }

    public int subjectCount(){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM subject", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        return count;
    }

    public int takensubjectCount(){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM taken_subject", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        return count;
    }

}
