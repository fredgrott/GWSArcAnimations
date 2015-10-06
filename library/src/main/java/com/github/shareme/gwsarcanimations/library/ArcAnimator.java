/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Asylbek Isakov
 * Modifications Copyright (C) 2015 Fred Grott(GrottWorkShop)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package com.github.shareme.gwsarcanimations.library;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.View;

import java.lang.ref.WeakReference;


/**
 * ArcAnimator class
 * Created by fgrott on 9/16/2015.
 */
@SuppressWarnings("unused")
public class ArcAnimator extends Animator {

    public static ArcAnimator createArcAnimator(View clipView, View nestView,
                                                float degree, Side side){

        return createArcAnimator(clipView, Utils.centerX(nestView), Utils.centerY(nestView),
                degree, side);
    }

    public static ArcAnimator createArcAnimator(View clipView, float endX, float endY,
                                                float degree, Side side){

        ArcMetric arcMetric = ArcMetric.evaluate(Utils.centerX(clipView), Utils.centerY(clipView),
                endX, endY, degree, side);
        return new ArcAnimator(arcMetric, clipView);
    }

    ArcMetric mArcMetric;
    WeakReference<View> mTarget;
    WeakReference<ObjectAnimator> mAnimator;
    float mValue;


    private ArcAnimator(ArcMetric arcmetric, View target) {
        mArcMetric = arcmetric;
        mTarget = new WeakReference<>(target);

        mAnimator = new WeakReference<>(
                ObjectAnimator.ofFloat(
                        ArcAnimator.this, // target
                        "degree", // property
                        arcmetric.getStartDegree(),
                        arcmetric.getEndDegree())
        );
    }

    void setDegree(float degree){
        mValue = degree;
        View clipView = mTarget.get();
        float x = mArcMetric.getAxisPoint().x + mArcMetric.mRadius
                * Utils.cos(degree);
        float y = mArcMetric.getAxisPoint().y - mArcMetric.mRadius
                * Utils.sin(degree);
        clipView.setX(x - clipView.getWidth() / 2);
        clipView.setY(y - clipView.getHeight() / 2);
    }

    float getDegree(){
        return mValue;
    }

    @Override
    public long getStartDelay() {
        Animator a = mAnimator.get();
        return a == null ? 0 : a.getDuration();
    }

    @Override
    public void setStartDelay(long startDelay) {
        Animator a = mAnimator.get();
        if(a != null) a.setStartDelay(startDelay);
    }

    @Override
    public ArcAnimator setDuration(long duration) {
        Animator a = mAnimator.get();
        if(a != null) a.setDuration(duration);
        return this;
    }

    @Override
    public long getDuration() {
        Animator a = mAnimator.get();
        return a == null ? 0 : a.getDuration();
    }

    @Override
    public void setInterpolator(TimeInterpolator value) {
        Animator a = mAnimator.get();
        if(a != null) a.setInterpolator(value);
    }

    @Override
    public void start() {
        super.start();
        Animator a = mAnimator.get();
        if(a != null) a.start();
    }

    @Override
    public void end() {
        super.end();
        Animator a = mAnimator.get();
        if(a != null) a.end();
    }

    @Override
    public void cancel() {
        super.cancel();
        Animator a = mAnimator.get();
        if(a != null) a.cancel();
    }

    @Override
    public void addListener(AnimatorListener listener) {
        Animator a = mAnimator.get();
        if(a != null) a.addListener(listener);
    }

    @Override
    public void setupEndValues() {
        super.setupEndValues();
        Animator a = mAnimator.get();
        if(a != null) a.setupEndValues();
    }

    @Override
    public void setupStartValues() {
        super.setupStartValues();
        Animator a = mAnimator.get();
        if(a != null) a.setupStartValues();
    }

    @Override
    public boolean isRunning() {
        Animator a = mAnimator.get();
        return a != null && a.isRunning();
    }

    @Override
    public String toString() {
        return mArcMetric.toString();
    }
}