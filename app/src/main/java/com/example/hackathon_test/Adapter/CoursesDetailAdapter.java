package com.example.hackathon_test.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hackathon_test.Activities.CourseDetailActivity;
import com.example.hackathon_test.Models.Assignments;
import com.example.hackathon_test.Models.Course;
import com.example.hackathon_test.R;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class CoursesDetailAdapter extends RecyclerView.Adapter<com.example.hackathon_test.Adapter.CoursesDetailAdapter.ViewHolder> {

    private Context context;
    private List<Assignments> assignments;

    public CoursesDetailAdapter(Context context, List<Assignments> assignments) {
        this.context = context;
        this.assignments = assignments;
    }

    @NonNull
        @Override
        public com.example.hackathon_test.Adapter.CoursesDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_course_details, parent, false);
            return new com.example.hackathon_test.Adapter.CoursesDetailAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull com.example.hackathon_test.Adapter.CoursesDetailAdapter.ViewHolder holder, int position) {
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
            private TextView tvAssignmentDescription;
            private TextView tvMonth;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                tvAssignmentName = itemView.findViewById(R.id.tvAssignmentName);
                tvStatus = itemView.findViewById(R.id.tvStatus);
                tvAssignmentDescription = itemView.findViewById(R.id.tvAssignmentDescription);
                tvMonth = itemView.findViewById(R.id.tvMonth);

            }

            public void bind(Assignments assignment) throws ParseException {

                Log.i("BIND", assignment.getName());


                tvAssignmentName.setText(assignment.getName());

                String stat = assignment.geStatus();
                if (stat.equals("DUE")) {
                    tvStatus.setText(stat);
                } else if (stat.equals("DONE")){
                    tvStatus.setTextColor(Color.parseColor("#4CAF50"));
                    tvStatus.setText(stat);

                } else {
                    tvStatus.setTextColor(Color.parseColor("#F57C00"));
                    tvStatus.setText(stat);
                }




                tvAssignmentDescription.setText(assignment.getDescription());

                DateFormat dateFormat = new SimpleDateFormat(
                        "MMM dd", Locale.US);
                String date_string = dateFormat.format(assignment.getDate());

                tvMonth.setText(date_string);

            }
        }
    }
