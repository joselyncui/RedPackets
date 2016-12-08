/*
 * Copyright (C) 2015, 程序亦非猿
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vicky.red.redpackets.view.bezier;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

public class BezierEvaluator implements TypeEvaluator<PointF> {


    private PointF mPointF1;
    private PointF mPointF2;
    public BezierEvaluator(PointF pointF1,PointF pointF2){
        this.mPointF1 = pointF1;
        this.mPointF2 = pointF2;
    }
    @Override
    public PointF evaluate(float time, PointF startValue,
                           PointF endValue) {

        float timeLeft = 1.0f - time;
        PointF point = new PointF();//结果

        point.x = timeLeft * timeLeft * timeLeft * (startValue.x)
                + 3 * timeLeft * timeLeft * time * (mPointF1.x)
                + 3 * timeLeft * time * time * (mPointF2.x)
                + time * time * time * (endValue.x);

        point.y = timeLeft * timeLeft * timeLeft * (startValue.y)
                + 3 * timeLeft * timeLeft * time * (mPointF1.y)
                + 3 * timeLeft * time * time * (mPointF2.y)
                + time * time * time * (endValue.y);

        return point;
    }
}
