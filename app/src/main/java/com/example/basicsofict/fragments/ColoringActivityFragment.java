package com.example.basicsofict.fragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.basicsofict.R;

public class ColoringActivityFragment extends Fragment {

    private ImageView ivColoringOverlay;
    private LinearLayout colorPalette;
    private SeekBar seekBarBrush;
    private TextView tvBrushSize;
    private Button btnClear;

    private Bitmap drawingBitmap;
    private Canvas drawingCanvas;
    private Paint drawingPaint;
    private int currentColor = Color.RED;
    private float brushSize = 20f;
    private float lastX, lastY;

    public ColoringActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_coloring, container, false);

        initializeViews(view);
        setupDrawing();
        setupColorPalette();

        return view;
    }

    private void initializeViews(View view) {
        ivColoringOverlay = view.findViewById(R.id.iv_coloring_overlay);
        colorPalette = view.findViewById(R.id.color_palette);
        seekBarBrush = view.findViewById(R.id.seekbar_brush);
        tvBrushSize = view.findViewById(R.id.tv_brush_size);
        btnClear = view.findViewById(R.id.btn_clear);

        seekBarBrush.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brushSize = progress;
                tvBrushSize.setText(String.valueOf(progress));
                drawingPaint.setStrokeWidth(brushSize);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnClear.setOnClickListener(v -> clearDrawing());
    }

    private void setupDrawing() {
        // Initialize paint
        drawingPaint = new Paint();
        drawingPaint.setColor(currentColor);
        drawingPaint.setStrokeWidth(brushSize);
        drawingPaint.setStyle(Paint.Style.STROKE);
        drawingPaint.setStrokeCap(Paint.Cap.ROUND);
        drawingPaint.setStrokeJoin(Paint.Join.ROUND);
        drawingPaint.setAntiAlias(true);

        // Set up touch listener for drawing
        ivColoringOverlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startDrawing(x, y);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        continueDrawing(x, y);
                        return true;
                    case MotionEvent.ACTION_UP:
                        stopDrawing();
                        return true;
                }
                return false;
            }
        });
    }

    private void startDrawing(float x, float y) {
        if (drawingBitmap == null) {
            createDrawingBitmap();
        }
        lastX = x;
        lastY = y;
        drawingCanvas.drawCircle(x, y, brushSize / 2, drawingPaint);
        ivColoringOverlay.setImageBitmap(drawingBitmap);
    }

    private void continueDrawing(float x, float y) {
        if (drawingBitmap != null) {
            drawingCanvas.drawLine(lastX, lastY, x, y, drawingPaint);
            ivColoringOverlay.setImageBitmap(drawingBitmap);
            lastX = x;
            lastY = y;
        }
    }

    private void stopDrawing() {
        // Nothing special needed for now
    }

    private void createDrawingBitmap() {
        int width = ivColoringOverlay.getWidth();
        int height = ivColoringOverlay.getHeight();

        if (width > 0 && height > 0) {
            drawingBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            drawingCanvas = new Canvas(drawingBitmap);
            drawingCanvas.drawColor(Color.TRANSPARENT);
        }
    }

    private void setupColorPalette() {
        colorPalette.removeAllViews();

        int[] colors = {
                Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,
                Color.MAGENTA, Color.CYAN, Color.BLACK, Color.WHITE,
                Color.rgb(255, 165, 0), // Orange
                Color.rgb(128, 0, 128)  // Purple
        };

        for (int color : colors) {
            View colorView = createColorView(color);
            colorPalette.addView(colorView);
        }
    }

    private View createColorView(int color) {
        View colorView = new View(requireContext());
        colorView.setBackgroundColor(color);
        colorView.setTag(color);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.color_circle_size),
                getResources().getDimensionPixelSize(R.dimen.color_circle_size)
        );
        params.setMargins(8, 0, 8, 0);
        colorView.setLayoutParams(params);

        // Add border for white color
        if (color == Color.WHITE) {
            colorView.setBackgroundResource(R.drawable.color_white_bg);
        }

        colorView.setOnClickListener(v -> {
            currentColor = (int) v.getTag();
            drawingPaint.setColor(currentColor);
            highlightSelectedColor(v);
        });

        return colorView;
    }

    private void highlightSelectedColor(View selectedView) {
        for (int i = 0; i < colorPalette.getChildCount(); i++) {
            View child = colorPalette.getChildAt(i);
            child.setBackgroundColor((int) child.getTag());

            // Re-add border for white
            if ((int) child.getTag() == Color.WHITE) {
                child.setBackgroundResource(R.drawable.color_white_bg);
            }
        }

        // Highlight selected color
        selectedView.setBackgroundResource(R.drawable.color_selected_bg);
    }

    private void clearDrawing() {
        if (drawingCanvas != null) {
            drawingCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            ivColoringOverlay.setImageBitmap(drawingBitmap);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (drawingBitmap != null && !drawingBitmap.isRecycled()) {
            drawingBitmap.recycle();
        }
    }
}