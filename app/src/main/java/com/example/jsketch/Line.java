package com.example.jsketch;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;

class Line extends Shape implements Parcelable {
    private int color;

    public Line(float x, float y) {
        super(x,y);
    }

    private Boolean distance(float x, float y) {
        double distance1 = Math.sqrt((double) (x1 - x)*(x1 - x) + (y1 - y)*(y1 - y));
        double distance2 = Math.sqrt((double) (x2 - x)*(x2 - x) + (y2 - y)*(y2 - y));
        double distance3 = Math.sqrt((double) (x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1));
        if ((distance1 + distance2 >= distance3 - 1) && (distance1 + distance2 <= distance3 + 1)) {
            return true;
        }
        return false;
    }

    private void drawHelper(Canvas canvas, Paint paint) {
        canvas.drawLine(x1, y1, x2, y2, paint);

        paint.setStyle(Paint.Style.FILL);
    }

    public void update(float x, float y) {
        x1 += x;
        x2 += x;
        y1 += y;
        y2 += y;
    }

    public int getColor() {return color;}

    // Draw using the current matrix
    public void draw(Canvas canvas, Paint paint, int c) {
        color = c;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        drawHelper(canvas, paint);
    }

    public void redraw(Canvas canvas, Paint paint) {
        int temp = paint.getColor();
        paint.setColor(color);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        drawHelper(canvas, paint);

        paint.setColor(temp);
    }

    public void clear(Canvas canvas, Paint paint) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(12);
        drawHelper(canvas, paint);
    }

    public int checkBoundary(float x, float y) {
        if (distance(x, y)) {
            return 1;
        }
        return 0;
    }

    public void dotBoundary(Canvas canvas, Paint paint) {
        canvas.drawLine(x1, y1, x2, y2, paint);
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(color);
    }

    public final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Line createFromParcel(Parcel in) {

            return new Line(in);
        }

        public Line[] newArray(int size) {
            return new Line[size];
        }
    };

    public Line(Parcel in) {
        super(in);
        this.color = in.readInt();
    }
}