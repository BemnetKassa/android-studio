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

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {

    private List<Chapter> chapters;
    private ProgressManager progressManager;
    private OnChapterClickListener listener;

    public ChapterAdapter(List<Chapter> chapters, ProgressManager progressManager, OnChapterClickListener listener) {
        this.chapters = chapters;
        this.progressManager = progressManager;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chapter, parent, false);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        Chapter chapter = chapters.get(position);
        holder.bind(chapter, progressManager, listener);
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    static class ChapterViewHolder extends RecyclerView.ViewHolder {
        private TextView tvChapterNumber;
        private TextView tvChapterTitle;
        private TextView tvChapterDesc;
        private ProgressBar progressBar;
        private TextView tvProgressText;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);

            // Use the correct IDs from your item_chapter.xml
            tvChapterNumber = itemView.findViewById(R.id.tvChapterNumber);
            tvChapterTitle = itemView.findViewById(R.id.tvChapterTitle);
            tvChapterDesc = itemView.findViewById(R.id.tvChapterDesc);
            progressBar = itemView.findViewById(R.id.progressBar);
            tvProgressText = itemView.findViewById(R.id.tvProgressText);
        }

        public void bind(Chapter chapter, ProgressManager progressManager, OnChapterClickListener listener) {
            // Set chapter number (position + 1 since chapters start from 1)
            tvChapterNumber.setText(String.valueOf(chapter.getId()));

            tvChapterTitle.setText(chapter.getTitle());
            tvChapterDesc.setText(chapter.getDescription());

            // Calculate and display progress
            int progress = progressManager.calculateChapterProgress(chapter);
            progressBar.setProgress(progress);
            tvProgressText.setText(progress + "% Complete");

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onChapterClick(chapter);
                }
            });
        }
    }

    public interface OnChapterClickListener {
        void onChapterClick(Chapter chapter);
    }
}