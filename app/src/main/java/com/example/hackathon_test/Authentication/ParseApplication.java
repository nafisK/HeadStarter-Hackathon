package com.example.hackathon_test.Authentication;

import com.example.hackathon_test.Models.Assignments;
import com.example.hackathon_test.Models.Course;
import com.example.hackathon_test.Models.User;
import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Application;

public class ParseApplication extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Course.class);
        ParseObject.registerSubclass(Assignments.class);
        ParseObject.registerSubclass(User.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("s6r1RitavBrOjJQyqGbg8kT0jAUDcZgZbuK4GoxA")
                .clientKey("4sEfW9mnGrzOae9gRHNiQg6YXOW4oHP3t7ZgE1mG")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}