package com.example.hackathon_test.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hackathon_test.Activities.CourseDetailActivity;
import com.example.hackathon_test.Models.Course;
import com.example.hackathon_test.R;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {

    private Context context;
    private List<Course> courses;

    public CoursesAdapter(Context context, List<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.bind(course);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCourseName;
        private TextView tvProfessorName;
        private ImageView ivBanner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvProfessorName = itemView.findViewById(R.id.tvProfessorName);
            ivBanner = itemView.findViewById(R.id.ivBanner);
        }

        public void bind(Course course) {
            tvCourseName.setText(course.getName());
            tvProfessorName.setText(course.getProfessor());

            ParseFile image = course.getBanner();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivBanner);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CourseDetailActivity.class);
                    intent.putExtra("course", Parcels.wrap(course));
                    context.startActivity(intent);
                }
            });

        }
    }
}
