package com.example.hackathon_test.Adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hackathon_test.Activities.CourseDetailActivity;
import com.example.hackathon_test.Models.Assignments;
import com.example.hackathon_test.Models.Course;
import com.example.hackathon_test.R;
import com.parse.ParseFile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Locale;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private Context context;
    private List<Course> courses;
    private List<Assignments> assignments;

    public ToDoAdapter(Context context, List<Course> courses, List<Assignments> assignments) {
        this.context = context;
        this.courses = courses;
        this.assignments = assignments;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Assignments assignment = assignments.get(position);
        try {
            holder.bind(assignment);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvAssignmentName;
        private TextView tvStatus;
        private TextView tvCourseName;
        private TextView tvAssignmentDescription;
        private TextView tvMonth;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAssignmentName = itemView.findViewById(R.id.tvAssignmentName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvAssignmentDescription = itemView.findViewById(R.id.tvAssignmentDescription);
            tvMonth = itemView.findViewById(R.id.tvMonth);

        }

        public void bind(Assignments assignment) throws ParseException {

            Course correctCourse = new Course();

            for(Course course: courses) {
                if (course.getObjectId().equals(assignment.getCourseId()))
                    correctCourse = course;
            }

            tvAssignmentName.setText(assignment.getName());
            tvStatus.setText(assignment.geStatus());
            tvCourseName.setText(correctCourse.getName());
            tvAssignmentDescription.setText(assignment.getDescription());

            DateFormat dateFormat = new SimpleDateFormat(
                    "MMM dd", Locale.US);
            String date_string = dateFormat.format(assignment.getDate());

            tvMonth.setText(date_string);

        }
    }
}
