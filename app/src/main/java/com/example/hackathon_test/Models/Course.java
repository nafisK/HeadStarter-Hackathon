package com.example.hackathon_test.Models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import org.parceler.Parcel;

@ParseClassName("Course")
@Parcel(analyze = Course.class)
public class Course extends ParseObject{

    public Course() {
    }

    public static final String KEY_COURSE_ID = "courseId";
    public static final String KEY_COURSE_DESCRIPTION = "courseDescription";
    public static final String KEY_COURSE_NAME = "courseName";
    public static final String KEY_COURSE_PROFESSOR = "courseProfessor";
    public static final String KEY_BANNER = "courseBanner";

    // getters
    public String getId() { return getString(KEY_COURSE_ID); }
    public String getDescription() { return getString(KEY_COURSE_DESCRIPTION); }
    public String getName() { return getString(KEY_COURSE_NAME); }
    public String getProfessor() { return getString(KEY_COURSE_PROFESSOR); }
    public ParseFile getBanner() { return getParseFile(KEY_BANNER); }


}
