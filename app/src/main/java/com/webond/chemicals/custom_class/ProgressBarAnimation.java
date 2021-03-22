package com.webond.chemicals.custom_class;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

import androidx.appcompat.widget.AppCompatTextView;

public class ProgressBarAnimation extends Animation {
    private ProgressBar progressBar;
    private float from;
    private float to;
    private AppCompatTextView appCompatTextView;

    public ProgressBarAnimation(ProgressBar progressBar, float from, float to, AppCompatTextView appCompatTextView) {
        super();
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
        this.appCompatTextView = appCompatTextView;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
        appCompatTextView.setText((int) value + "%");
    }

}
