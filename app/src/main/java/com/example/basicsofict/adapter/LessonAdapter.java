package com.example.basicsofict.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.basicsofict.R;
import com.example.basicsofict.models.Lesson;
import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {

    private List<Lesson> lessons;
    private OnLessonClickListener listener;

    public LessonAdapter(List<Lesson> lessons, OnLessonClickListener listener) {
        this.lessons = lessons;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lesson, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        Lesson lesson = lessons.get(position);
        holder.bind(lesson, listener);
    }

    @Override
    public int getItemCount() {
        return lessons != null ? lessons.size() : 0;
    }

    static class LessonViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLessonTitle;
        private TextView tvLessonType;
        private TextView ivLessonStatus;

        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);

            // Use the correct IDs from your item_lesson.xml
            tvLessonTitle = itemView.findViewById(R.id.tvLessonTitle);
            tvLessonType = itemView.findViewById(R.id.tvLessonType);
            ivLessonStatus = itemView.findViewById(R.id.ivLessonStatus);
        }

        public void bind(Lesson lesson, OnLessonClickListener listener) {
            tvLessonTitle.setText(lesson.getTitle());

            // Set lesson type and duration
            String typeText = lesson.getActivityType() + " • 10 min";
            tvLessonType.setText(typeText);

            // Set status icon based on activity type
            String statusIcon = getStatusIcon(lesson.getActivityType());
            ivLessonStatus.setText(statusIcon);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onLessonClick(lesson);
                }
            });
        }

        private String getStatusIcon(String activityType) {
            switch (activityType) {
                case "theory":
                    return "📖";
                case "practice":
                    return "✏️";
                case "interactive":
                    return "🎮";
                default:
                    return "📚";
            }
        }
    }

    public interface OnLessonClickListener {
        void onLessonClick(Lesson lesson);
    }
}