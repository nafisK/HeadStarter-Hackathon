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

import com.example.hackathon_test.Adapter.ToDoAdapter;
import com.example.hackathon_test.Models.Assignments;
import com.example.hackathon_test.Models.Course;
import com.example.hackathon_test.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ToDoFragment extends Fragment {

    public static final String TAG = "ToDoFragment";
    private List<Assignments> dueAssignments;
    private List<Course> allCourses;
    private RecyclerView rvToDo;
    private ToDoAdapter adapter;


    public ToDoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_do, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvToDo = view.findViewById(R.id.rvToDo);

        dueAssignments = new ArrayList<>();
        allCourses = new ArrayList<Course>();

        adapter = new ToDoAdapter(getContext(), allCourses, dueAssignments);
        rvToDo.setAdapter(adapter);
        rvToDo.setLayoutManager(new LinearLayoutManager(getContext()));

        queryDueAssignments();
        queryCourses();


    }



    // gets all the assignments
    private void queryDueAssignments() {
        // filtering with date
        Date date = new Date();
        ParseQuery<Assignments> query = ParseQuery.getQuery(Assignments.class);
        query.orderByAscending(Assignments.KEY_DUE);
        query.findInBackground(new FindCallback<Assignments>() {
            @Override
            public void done(List<Assignments> assignments, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting courses", e);
                    return;
                }
                // filtering with date
                Date date = new Date();
                long currTime = date.getTime();
                for (Assignments assignment: assignments) {
                    Log.i(TAG, "assignment: " + assignment.getName());

                    long dueTime = assignment.getDate().getTime();
                    if (currTime < dueTime){
                        dueAssignments.add(assignment);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

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
            for (Course course : courses) {
                Log.i(TAG, "Courses: " + course.getName());
            }
                allCourses.addAll(courses);
            }
        });

    }





}