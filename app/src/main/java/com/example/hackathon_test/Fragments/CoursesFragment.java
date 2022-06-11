package com.example.hackathon_test.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hackathon_test.Adapter.CoursesAdapter;
import com.example.hackathon_test.Models.Course;
import com.example.hackathon_test.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class CoursesFragment extends Fragment {

    public static final String TAG = "CoursesFragment";
    private CoursesAdapter adapter;
    private RecyclerView rvCourses;
    private List<Course> allCourses;

    public CoursesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_courses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvCourses = view.findViewById(R.id.rvCourses);

        allCourses = new ArrayList<>();
        adapter = new CoursesAdapter(getContext(), allCourses);
        rvCourses.setAdapter(adapter);
        rvCourses.setLayoutManager(new LinearLayoutManager(getContext()));

        queryCourses();


    }

    private void queryCourses() {
        ParseQuery<Course> query = ParseQuery.getQuery(Course.class);

        query.findInBackground(new FindCallback<Course>() {
        @Override
        public void done(List<Course> courses, ParseException e) {
            // check for errors
            if (e != null) {
                Log.e(TAG, "Issue with getting courses", e);
                return;
            }

            // logs all the courses for debugging
//            for (Course course : courses) {
////                Log.i(TAG, "Courses: " + course.getBanner());
//            }
            allCourses.addAll(courses);
            adapter.notifyDataSetChanged();
        }
    });

    }





}