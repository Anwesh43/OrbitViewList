package com.anwesome.ui.orbitviewlist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 09/05/17.
 */
public class OrbitView extends View {
    private int w,h,time = 0;
    private float totalScale = 0;
    private OnFillChangeLister onFillChangeLister;
    public void setOnFillChangeLister(OnFillChangeLister onFillChangeLister) {
        this.onFillChangeLister = onFillChangeLister;
    }
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private AnimationController animationController = new AnimationController();
    private ConcurrentLinkedQueue<OrbitElement> orbitElements = new ConcurrentLinkedQueue<>();
    private ArcProgressTracker arcProgressTracker;
    public OrbitView(Context context) {
        super(context);
    }
    public void onDraw(Canvas canvas) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            for(int i=0;i<6;i++) {
                OrbitElement orbitElement = new OrbitElement(i*60);
                orbitElements.add(orbitElement);
            }
            arcProgressTracker = new ArcProgressTracker();
        }
        totalScale = 0;
        for(OrbitElement orbitElement:orbitElements) {
            totalScale+= orbitElement.scale;
        }
        paint.setColor(Color.parseColor("#311B92"));
        for(OrbitElement orbitElement:orbitElements) {
            orbitElement.draw(canvas,paint);
        }
        arcProgressTracker.draw(canvas,paint);
        time++;
        animationController.animate();
    }
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            animationController.startAnimating(event.getX(),event.getY());
        }
        return true;
    }
    private class OrbitElement {
        private float x,y,r,scale=0,dir = 0;
        public OrbitElement(int deg) {
            r = w/16;
            x = w/2 + (float)(w/3*Math.cos(deg*(Math.PI/180)));
            y = h/2 +(float)(w/3*Math.sin(deg*(Math.PI/180)));
        }
        public void draw(Canvas canvas,Paint paint) {
            paint.setStrokeWidth(r/5);
            canvas.save();
            canvas.translate(x,y);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(0,0,r,paint);
            canvas.save();
            canvas.scale(scale,scale);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(0,0,r,paint);
            canvas.restore();
            canvas.restore();
        }
        public void update() {
            scale += 0.2f*dir;
            if(scale> 1) {
                dir = 0;
                scale = 1;
                if(totalScale >= orbitElements.size() && onFillChangeLister!=null) {
                    onFillChangeLister.onFill();
                }
            }
            if(scale<0) {
                scale = 0;
                dir = 0;
                Log.d("totalFilled",""+totalScale);
                if(Math.floor(totalScale) <= 0 && onFillChangeLister!=null) {
                    onFillChangeLister.onUnFill();
                }
            }

        }
        public boolean stopped() {
            return dir == 0;
        }
        public boolean handleTap(float x,float y) {
            boolean condition =  x>=this.x-3*r/2 && x<=this.x+3*r/2 && y>=this.y - 3*r/2 && y <= this.y+3*r/2;
            if(condition) {
                dir = scale == 1?-1:1;
            }
            return condition;
        }
    }
    private class AnimationController {
        private boolean isAnimated = false;
        private OrbitElement currElement = null;
        public void animate() {
            if(isAnimated && currElement != null) {
                currElement.update();
                if(currElement.stopped()) {
                    isAnimated = false;
                    currElement = null;
                }
                try {
                    Thread.sleep(100);
                    invalidate();
                }
                catch(Exception ex) {

                }
            }
        }
        public void startAnimating(float x,float y) {
            if(!isAnimated && currElement == null) {
                for(OrbitElement orbitElement:orbitElements) {
                    if(orbitElement.handleTap(x,y)) {
                        currElement = orbitElement;
                        isAnimated = true;
                        postInvalidate();
                    }
                }
            }
        }
    }
    private class ArcProgressTracker {
        public void draw(Canvas canvas,Paint paint) {
            canvas.save();
            canvas.translate(w/2,h/2);
            canvas.drawArc(new RectF(-w/10,-w/10,w/10,w/10),0,360*((totalScale)/orbitElements.size()),true,paint);
            canvas.restore();
        }
    }
}
