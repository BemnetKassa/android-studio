package com.example.basicsofict.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicsofict.R;
import com.example.basicsofict.models.Chapter;
import com.example.basicsofict.models.Lesson;
// Import the REAL ProgressManager from the utils package
import com.example.basicsofict.utils.ProgressManager;

import java.util.List;
import java.util.Locale;

// This class is for UI. It MUST extend RecyclerView.Adapter.
public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ProgressViewHolder> {

    private final List<Chapter> chapterList;
    private final ProgressManager progressManager;
    private Context context; // We will get this from the parent view

    public ProgressAdapter(List<Chapter> chapterList, ProgressManager progressManager) {
        this.chapterList = chapterList;
        this.progressManager = progressManager;
    }

    @NonNull
    @Override
    public ProgressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext(); // Get context when the view is created
        View view = LayoutInflater.from(context).inflate(R.layout.item_chapter_progress, parent, false);
        return new ProgressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressViewHolder holder, int position) {
        Chapter chapter = chapterList.get(position);
        holder.bind(chapter, context, progressManager);
    }

    @Override
    public int getItemCount() {
        // Return the actual number of chapters
        return chapterList != null ? chapterList.size() : 0;
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvChapterNumber, chapterTitle, progressText, lessonsCompletedText;
        private final ProgressBar chapterProgressBar;
        private final RecyclerView lessonsRecyclerView;

        public ProgressViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChapterNumber = itemView.findViewById(R.id.tvChapterNumber);
            chapterTitle = itemView.findViewById(R.id.chapterTitle);
            progressText = itemView.findViewById(R.id.progressText);
            lessonsCompletedText = itemView.findViewById(R.id.lessonsCompletedText);
            chapterProgressBar = itemView.findViewById(R.id.chapterProgressBar);
            lessonsRecyclerView = itemView.findViewById(R.id.lessonsRecyclerView);
        }

        void bind(Chapter chapter, Context context, ProgressManager progressManager) {
            int chapterNumber = getAdapterPosition() + 1;
            tvChapterNumber.setText(String.valueOf(chapterNumber));
            chapterTitle.setText(chapter.getTitle());

            // *** FIX STARTS HERE ***

            // 1. Calculate the progress percentage using the existing method in ProgressManager
            int progress = progressManager.calculateChapterProgress(chapter);

            // 2. To get the completed lessons count, we need a bit more logic.
            // Since we know the total lessons and the progress percentage, we can calculate it.
            List<Lesson> lessons = chapter.getLessons();
            int totalLessons = (lessons != null) ? lessons.size() : 0;
            int completedLessons = (totalLessons * progress) / 100;

            // *** FIX ENDS HERE ***

            chapterProgressBar.setProgress(progress);
            progressText.setText(String.format(Locale.getDefault(), "%d%%", progress));
            lessonsCompletedText.setText(String.format(Locale.getDefault(), "%d/%d lessons", completedLessons, totalLessons));

            setupLessonsRecyclerView(chapter, context);

            itemView.setOnClickListener(v -> {
                boolean isVisible = lessonsRecyclerView.getVisibility() == View.VISIBLE;
                lessonsRecyclerView.setVisibility(isVisible ? View.GONE : View.VISIBLE);
            });
        }

        private void setupLessonsRecyclerView(Chapter chapter, Context context) {
            // Make sure LessonAdapter exists and is correct
            LessonAdapter lessonAdapter = new LessonAdapter(context, chapter);
            lessonsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            lessonsRecyclerView.setAdapter(lessonAdapter);
            lessonsRecyclerView.setHasFixedSize(true);
        }
    }
}
