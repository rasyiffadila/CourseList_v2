package com.example.courselist.Features.CreateTakensubject;

public class Takensubject {
    private long id;
    private long student_id;
    private long subject_id;

    public Takensubject(long id, long student_id, long subject_id) {
        this.id = id;
        this.student_id = student_id;
        this.subject_id = subject_id;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(long student_id) {
        this.student_id = student_id;
    }

    public long getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(long subject_id) {
        this.subject_id = subject_id;
    }
}
