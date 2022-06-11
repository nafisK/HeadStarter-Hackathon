package com.example.hackathon_test.Models;


import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.parceler.Parcel;

import java.util.Date;

@ParseClassName("Assignments")
@Parcel(analyze = Assignments.class)
public class Assignments extends ParseObject {

    public Assignments() {

    }

    public static final String KEY_NAME = "name";
    public static final String KEY_DUE = "due";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_STATUS = "status";
    public static final String KEY_COURSE_ID = "courseId";

    public String getName() { return getString(KEY_NAME); }
    public Date getDate() { return getDate(KEY_DUE); }
    public String getDescription() { return getString(KEY_DESCRIPTION); }
    public String geStatus() { return getString(KEY_STATUS); }
    public String getCourseId() { return getString(KEY_COURSE_ID); }


}
