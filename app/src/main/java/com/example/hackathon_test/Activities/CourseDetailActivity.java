package com.example.hackathon_test.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hackathon_test.Adapter.CoursesDetailAdapter;
import com.example.hackathon_test.Adapter.ToDoAdapter;
import com.example.hackathon_test.Models.Assignments;
import com.example.hackathon_test.Models.Course;
import com.example.hackathon_test.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseDetailActivity extends AppCompatActivity {

    // member variables
    public static final String TAG = "CourseDetailActivity";
    Toolbar toolbar;
    private List<Assignments> allAssignments;
    private RecyclerView rvCoursesDetail;
    private CoursesDetailAdapter adapter;
    Course course;

    ImageView ivBanner;
    TextView tvCourseName, tvCourseId, tvProfessorName, tvCourseDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        rvCoursesDetail = findViewById(R.id.rvCoursesDetail);
        toolbar = findViewById(R.id.toolbar);
        tvCourseName = findViewById(R.id.tvCourseName);
        tvCourseId = findViewById(R.id.tvCourseId);
        tvProfessorName = findViewById(R.id.tvProfessorName);
        tvCourseDescription = findViewById(R.id.tvCourseDescription);
        ivBanner = findViewById(R.id.ivBanner);

        setSupportActionBar(toolbar);

        course = (Course) Parcels.unwrap(getIntent().getParcelableExtra("course"));
        tvCourseName.setText(course.getName());
        tvCourseId.setText(course.getId());
        tvProfessorName.setText(course.getProfessor());
        tvCourseDescription.setText(course.getDescription());
        ParseFile image = course.getBanner();
        if (image != null) {
            Glide.with(this).load(image.getUrl()).into(ivBanner);
        }

        allAssignments = new ArrayList<>();
        adapter = new CoursesDetailAdapter(this, allAssignments);
        rvCoursesDetail.setAdapter(adapter);
        rvCoursesDetail.setLayoutManager(new LinearLayoutManager(this));

        //queryAssignments();
        queryAssignments(course);

    }


    // gets all the assignments
    private void queryAssignments() {
        // filtering with date
        Date date = new Date();
        ParseQuery<Assignments> query = ParseQuery.getQuery(Assignments.class);
        query.orderByDescending(Assignments.KEY_DUE);
        query.findInBackground(new FindCallback<Assignments>() {
            @Override
            public void done(List<Assignments> assignments, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting courses", e);
                    return;
                }



                allAssignments.addAll(assignments);
                adapter.notifyDataSetChanged();
            }
        });

    }


    private void queryAssignments(Course course) {
        // filtering with date
        Date date = new Date();
        ParseQuery<Assignments> query = ParseQuery.getQuery(Assignments.class);
        query.orderByDescending(Assignments.KEY_DUE);
        query.include(Assignments.KEY_COURSE);
        query.whereEqualTo(Assignments.KEY_COURSE, course);
        query.findInBackground(new FindCallback<Assignments>() {
            @Override
            public void done(List<Assignments> assignments, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting courses", e);
                    return;
                }


                allAssignments.addAll(assignments);
                adapter.notifyDataSetChanged();
            }
        });

    }



}