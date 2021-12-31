package com.example.courselist.Util;

public class Config {
    public static final String DATABASE_NAME = "student-db";

    //column names of student table
    public static final String TABLE_STUDENT = "student";
    public static final String TABLE_SUBJECT = "subject";
    public static final String TABLE_TAKEN_SUBJECT = "taken_subject";

    public static final String COLUMN_STUDENT_ID = "_id";
    public static final String COLUMN_STUDENT_NAME = "name";
    public static final String COLUMN_STUDENT_REGISTRATION = "registration_no";
    public static final String COLUMN_STUDENT_PHONE = "phone";
    public static final String COLUMN_STUDENT_EMAIL = "email";


    public static final String COLUMN_SUBJECT_ID = "_id";
    public static final String COLUMN_SUBJECT_NAME = "subject_name";
    public static final String COLUMN_SUBJECT_CODE = "subject_code";
    public static final String COLUMN_SUBJECT_CREDIT = "subject_credit";

    public static final String COLUMN_TAKEN_SUBJECT_ID = "_id";
    public static final String COLUMN_STUDENT_ID_TAKEN = "student_id";
    public static final String COLUMN_SUBJECT_ID_TAKEN = "subject_id";

    //others for general purpose key-value pair data
    public static final String TITLE = "title";
    public static final String CREATE_STUDENT = "create_student";
    public static final String UPDATE_STUDENT = "update_student";

    public static final String CREATE_SUBJECT = "create_subject";
    public static final String UPDATE_SUBJECT = "update_subject";

    public static final String CREATE_TAKEN_SUBJECT = "create_taken_subject";
    public static final String UPDATE_TAKEN_SUBJECT = "update_taken_subject";
    //column names of student table


    //others for general purpose key-value pair data

}