package com.example.hackathon_test.Fragments;

import static com.parse.Parse.getApplicationContext;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.service.voice.VoiceInteractionSession;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackathon_test.Adapter.ToDoAdapter;
import com.example.hackathon_test.Models.Assignments;
import com.example.hackathon_test.Models.Course;
import com.example.hackathon_test.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ToDoFragment extends Fragment {

    public static final String TAG = "ToDoFragment";
    private List<Assignments> dueAssignments;
    private List<Course> allCourses;
    private RecyclerView rvToDo;
    private ToDoAdapter adapter;
    private TextView tvDate;


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
        tvDate = view.findViewById(R.id.tvDate);

        // sets date in top bar
        setDate();

        dueAssignments = new ArrayList<>();
        allCourses = new ArrayList<Course>();
//
//        ToDoAdapter.OnLongClickListener onLongClickListener = new ToDoAdapter.OnLongClickListener() {
//            @Override
//            public void onItemLongClicked(int position) {
//                markAssignmentComplete(position);
//                dueAssignments.remove(position);
//                adapter.notifyDataSetChanged();
//                Toast.makeText(getApplicationContext(),  "Task Marked as Complete", Toast.LENGTH_SHORT).show();
//            }
//        };




        ToDoAdapter.OnLongClickListener onLongClickListener = new ToDoAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                Assignments assignments = dueAssignments.get(position);
                ParseQuery<Assignments> query = ParseQuery.getQuery(Assignments.class);

                // Retrieve the object by id
                query.getInBackground(assignments.getObjectId(), new GetCallback<Assignments>() {
                    public void done(Assignments assignments, ParseException e) {
                        if (e == null) {
                            // Now let's update it with some new data. In this case, only status and course
                            // will get sent to your Parse Server. the coursename hasn't changed.

                            assignments.put("status", "DONE");
                            assignments.saveInBackground();
                        }
                    }
                });

                markAssignmentComplete(position);
                dueAssignments.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),  "Task Marked as Complete", Toast.LENGTH_SHORT).show();
            }
        };










        adapter = new ToDoAdapter(getContext(), allCourses, dueAssignments, onLongClickListener);
        rvToDo.setAdapter(adapter);
        rvToDo.setLayoutManager(new LinearLayoutManager(getContext()));

        queryDueAssignments();
        queryCourses();


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDate() {
        //Getting the current date value
        LocalDate currentdate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentdate = LocalDate.now();
        }
        int currentDay = currentdate.getDayOfMonth();
        System.out.println("Current day: "+currentDay);
        //Getting the current month
        Month currentMonth = currentdate.getMonth();
        //getting the current year
        int currentYear = currentdate.getYear();

        String date = currentDay + "th " + currentMonth + ", " + currentYear;
        tvDate.setText(date);
    }

    private void markAssignmentComplete(int position) {
        // Retrieve the object by id
        ParseQuery<Assignments> query = ParseQuery.getQuery(Assignments.class);
        Assignments assignment = dueAssignments.get(position);
        assignment.put(Assignments.KEY_STATUS, "DONE");

        query.findInBackground(new FindCallback<Assignments>() {
            @Override
            public void done(List<Assignments> objects, ParseException e) {
                if (e == null) {
                    Log.i(TAG, "Database Updated to DONE");
                }else{
                    // Something went wrong while saving
                    Log.i(TAG, "Error with updating database" + e.getMessage());
                }
            }
        });

    }

    // gets all the assignments
    private void queryDueAssignments() {
        // filtering with date
        Date date = new Date();
        ParseQuery<Assignments> query = ParseQuery.getQuery(Assignments.class);
        query.orderByAscending(Assignments.KEY_DUE);
        query.whereEqualTo(Assignments.KEY_STATUS, "DUE");
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
//                    Log.i(TAG, "assignment: " + assignment.getName());

//                    long dueTime = assignment.getDate().getTime();
//                    if (currTime < dueTime){
//                        dueAssignments.add(assignment);
//                    }
                }
                dueAssignments.addAll(assignments);
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
//                Log.i(TAG, "Courses: " + course.getName());
            }
                allCourses.addAll(courses);
            }
        });

    }





}