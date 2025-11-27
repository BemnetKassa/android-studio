package com.example.basicsofict.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.basicsofict.R;
import com.example.basicsofict.models.Chapter;
import com.example.basicsofict.utils.ProgressManager;
import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {

    private List<Chapter> chapters;
    private ProgressManager progressManager;
    private OnChapterClickListener listener;

    public interface OnChapterClickListener {
        void onChapterClick(Chapter chapter);
    }

    public ChapterAdapter(List<Chapter> chapters, ProgressManager progressManager, OnChapterClickListener listener) {
        this.chapters = chapters;
        this.progressManager = progressManager;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter, parent, false);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        Chapter chapter = chapters.get(position);
        holder.bind(chapter, progressManager);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onChapterClick(chapter);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    static class ChapterViewHolder extends RecyclerView.ViewHolder {
        private TextView tvChapterNumber, tvChapterTitle, tvChapterDescription;
        private TextView tvProgressText, tvLessonsCount;
        private androidx.core.widget.ContentLoadingProgressBar progressBar;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChapterNumber = itemView.findViewById(R.id.tv_chapter_number);
            tvChapterTitle = itemView.findViewById(R.id.tv_chapter_title);
            tvChapterDescription = itemView.findViewById(R.id.tv_chapter_description);
            tvProgressText = itemView.findViewById(R.id.tv_progress_text);
            tvLessonsCount = itemView.findViewById(R.id.tv_lessons_count);
            progressBar = itemView.findViewById(R.id.progress_bar);
        }

        public void bind(Chapter chapter, ProgressManager progressManager) {
            tvChapterNumber.setText(String.valueOf(chapter.getId()));
            tvChapterTitle.setText(chapter.getTitle());
            tvChapterDescription.setText(chapter.getDescription());

            // Calculate progress
            int totalLessons = chapter.getLessons() != null ? chapter.getLessons().size() : 0;
            int completedLessons = 0;

            if (chapter.getLessons() != null) {
                for (int i = 0; i < totalLessons; i++) {
                    if (progressManager.isLessonCompleted(chapter.getId(), i)) {
                        completedLessons++;
                    }
                }
            }

            int progress = totalLessons > 0 ? (completedLessons * 100) / totalLessons : 0;

            tvProgressText.setText(progress + "% completed");
            tvLessonsCount.setText(completedLessons + "/" + totalLessons + " lessons");
            progressBar.setProgress(progress);
        }
    }
}