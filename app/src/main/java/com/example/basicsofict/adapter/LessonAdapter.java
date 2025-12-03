package com.example.basicsofict.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.basicsofict.R;
import com.example.basicsofict.fragments.LessonDetailFragment;
import com.example.basicsofict.models.Chapter;
import com.example.basicsofict.models.Lesson;
import com.example.basicsofict.utils.ProgressManager;
import java.util.Locale;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {

    // *** FIX: Removed the 'type' field and getType() method from this class ***
    private final Chapter chapter;
    private final Context context;
    private final ProgressManager progressManager;

    public LessonAdapter(Context context, Chapter chapter) {
        this.context = context;
        this.chapter = chapter;
        this.progressManager = new ProgressManager(context);
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lesson, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        Lesson lesson = chapter.getLessons().get(position);
        holder.bind(lesson, position);
    }

    @Override
    public int getItemCount() {
        return chapter.getLessons().size();
    }

    class LessonViewHolder extends RecyclerView.ViewHolder {
        TextView tvLessonTitle, tvLessonType, ivLessonStatus;

        LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLessonTitle = itemView.findViewById(R.id.tvLessonTitle);
            tvLessonType = itemView.findViewById(R.id.tvLessonType);
            ivLessonStatus = itemView.findViewById(R.id.ivLessonStatus);
        }

        void bind(Lesson lesson, int lessonIndex) {
            tvLessonTitle.setText(lesson.getTitle());
            // This line will now work correctly after we fix Lesson.java
            tvLessonType.setText(String.format(Locale.getDefault(), "%s • %s", lesson.getType(), lesson.getDuration()));

            boolean isCompleted = progressManager.isLessonCompleted(chapter.getId(), lessonIndex);
            if (isCompleted) {
                ivLessonStatus.setText("✓"); // Checkmark for completed
            } else {
                ivLessonStatus.setText("📖"); // Book for not completed
            }

            itemView.setOnClickListener(v -> {
                LessonDetailFragment fragment = LessonDetailFragment.newInstance(chapter, lesson, lessonIndex);
                ((FragmentActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .addToBackStack(null)
                        .commit();
            });
        }
    }
}
