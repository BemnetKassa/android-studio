package com.example.basicsofict.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.basicsofict.R;
import com.example.basicsofict.models.Chapter;
import com.example.basicsofict.utils.ProgressManager;
import java.util.List;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ProgressViewHolder> {
    private List<Chapter> chapters;
    private ProgressManager progressManager;

    public ProgressAdapter(List<Chapter> chapters, ProgressManager progressManager) {
        this.chapters = chapters;
        this.progressManager = progressManager;
    }

    @NonNull
    @Override
    public ProgressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chapter_progress, parent, false);
        return new ProgressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressViewHolder holder, int position) {
        Chapter chapter = chapters.get(position);
        holder.bind(chapter, progressManager);
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    public void updateChapters(List<Chapter> chapters) {
        this.chapters = chapters;
        notifyDataSetChanged();
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        private TextView chapterTitle;
        private ProgressBar chapterProgressBar;
        private TextView progressText;
        private TextView lessonsCompletedText;

        public ProgressViewHolder(@NonNull View itemView) {
            super(itemView);
            chapterTitle = itemView.findViewById(R.id.chapterTitle);
            chapterProgressBar = itemView.findViewById(R.id.chapterProgressBar);
            progressText = itemView.findViewById(R.id.progressText);
            lessonsCompletedText = itemView.findViewById(R.id.lessonsCompletedText);
        }

        public void bind(Chapter chapter, ProgressManager progressManager) {
            chapterTitle.setText(chapter.getTitle());

            int progress = progressManager.calculateChapterProgress(chapter);
            int completedLessons = getCompletedLessonsCount(chapter, progressManager);
            int totalLessons = chapter.getLessons().size();

            chapterProgressBar.setProgress(progress);
            progressText.setText(String.format("%d%%", progress));
            lessonsCompletedText.setText(String.format("%d/%d lessons", completedLessons, totalLessons));
        }

        private int getCompletedLessonsCount(Chapter chapter, ProgressManager progressManager) {
            int count = 0;
            // Use loop index instead of lesson.getId()
            for (int i = 0; i < chapter.getLessons().size(); i++) {
                if (progressManager.isLessonCompleted(chapter.getId(), i + 1)) { // Use index + 1 as lesson ID
                    count++;
                }
            }
            return count;
        }
    }
}