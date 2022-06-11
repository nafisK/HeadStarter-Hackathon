package com.example.hackathon_test.Models;

import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcel;

@Parcel(analyze = User.class)
public class User extends ParseUser {

    public static final String TAG = "User";
    public User() {
    }

    public static final String KEY_EMAIL = "email";
    public static final String KEY_UNIVERSITY = "university";
    public static final String KEY_IMAGE = "image";



    public String getEmail() {
        return getString(KEY_EMAIL);
    }
    public String getUniversity() {
        return getString(KEY_UNIVERSITY);
    }
    public ParseFile getImage() { return getParseFile(KEY_IMAGE); }

}
