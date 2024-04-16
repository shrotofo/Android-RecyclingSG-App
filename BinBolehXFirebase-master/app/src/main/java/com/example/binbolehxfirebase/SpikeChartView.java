package com.example.binbolehxfirebase;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class SpikeChartView extends View {
    private List<MonthData> monthDataPoints;
    private Paint textPaint; // Paint for drawing text
    private Paint paint; // Paint for drawing lines
    private float maxYValue = 100; // Max value for y-axis, to scale the data
    private float padding = 30; // Padding from the edges of the view
    private float xAxisStart = padding;
    private float xAxisEnd;
    private float yAxisStart;
    private float yAxisEnd;
    private float xAxisSpacing; // Spacing between x-axis points

    public SpikeChartView(Context context) {
        super(context);
        init();
        paint.setStrokeWidth(5);
    }

    public SpikeChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpikeChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Initialize paint for the lines
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);

        // Initialize paint for the text
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30); // Adjust size as needed
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        xAxisEnd = getWidth() - padding;
        yAxisEnd = getHeight() - padding;
        yAxisStart = getHeight() - padding;
        if (monthDataPoints != null && monthDataPoints.size() > 1) {
            xAxisSpacing = (xAxisEnd - xAxisStart) / (monthDataPoints.size() - 1);
        }
    }

    public void setMonths(List<MonthData> monthDataPoints) {
        this.monthDataPoints = monthDataPoints;
        if (monthDataPoints != null && monthDataPoints.size() > 1) {
            xAxisSpacing = (xAxisEnd - xAxisStart) / (monthDataPoints.size() - 1);
        }
        maxYValue = findMaxPercentage(); // Find the maximum percentage value to scale the graph
        invalidate(); // Redraw the view
    }

    private float findMaxPercentage() {
        float max = 0;
        if (monthDataPoints != null) {
            for (MonthData data : monthDataPoints) {
                max = Math.max(max, data.getTotalPercentage());
            }
        }
        return max;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawAxes(canvas);
        if (monthDataPoints != null) {
            drawDataPoints(canvas);
            drawMonthNames(canvas);
        }
    }

    private void drawAxes(Canvas canvas) {
        // Draw x-axis
        canvas.drawLine(xAxisStart, yAxisStart, xAxisEnd, yAxisStart, paint);
        // Draw y-axis
        canvas.drawLine(xAxisStart, yAxisEnd, xAxisStart, padding, paint);
    }

    private void drawDataPoints(Canvas canvas) {
        if (!monthDataPoints.isEmpty()) {
            float lastX = xAxisStart;
            float lastY = yAxisStart - (monthDataPoints.get(0).getTotalPercentage() / maxYValue) * (yAxisEnd - padding);
            for (int i = 1; i < monthDataPoints.size(); i++) {
                float x = xAxisStart + i * xAxisSpacing;
                float y = yAxisStart - (monthDataPoints.get(i).getTotalPercentage() / maxYValue) * (yAxisEnd - padding);
                canvas.drawLine(lastX, lastY, x, y, paint);
                lastX = x;
                lastY = y;
            }
        }
    }

    private void drawMonthNames(Canvas canvas) {
        if (monthDataPoints == null) return;

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float textHeight = fontMetrics.descent - fontMetrics.ascent;
        // Increase the space below the axis for the text
        float textPaddingBelowAxis = padding;
        float textOffset = textHeight - fontMetrics.descent;
        // Move the text higher up to make sure it fits after rotation
        float textYPosition = yAxisStart + textPaddingBelowAxis - fontMetrics.descent;

        for (int i = 0; i < monthDataPoints.size(); i++) {
            float x = xAxisStart + i * xAxisSpacing;
            float y = yAxisStart - textOffset- textPaddingBelowAxis;
            // Save the current state of the canvas
            canvas.save();
            // Rotate the canvas around the point (x, textYPosition)
            //canvas.rotate(-45, x, textYPosition);
            // Draw the text
            canvas.drawText(monthDataPoints.get(i).getName(), x, textYPosition, textPaint);
            // Restore the previous state of the canvas
            canvas.restore();
        }
    }


}

