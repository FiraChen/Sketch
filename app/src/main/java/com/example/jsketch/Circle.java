package com.example.jsketch;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;

public class Circle extends Shape implements Parcelable {
    float radius;
    private int color = 0;

    public Circle(float x, float y) {
        super(x,y);
    }

    private void drawHelper(Canvas canvas, Paint paint) {
        canvas.drawCircle(x1, y1, radius, paint);
    }

    // Draw using the current matrix
    public void draw(Canvas canvas, Paint paint, int c) {
        color = c;

        float x_diff;
        float y_diff;

        if(y2 < y1) {
            y_diff = (y1-y2);
        } else {
            y_diff = (y2-y1);
        }

        if(x2 < x1) {
            x_diff = (y1-y2);
        } else {
            x_diff = (y2-y1);
        }

        radius = (float) Math.sqrt((double) x_diff * x_diff + y_diff * y_diff);
        drawHelper(canvas, paint);
    }

    public void update(float x, float y) {
        x1 += x;
        x2 += x;
        y1 += y;
        y2 += y;
    }

    public int getColor() {return color;}

    public void redraw(Canvas canvas, Paint paint) {
        int temp = paint.getColor();
        paint.setColor(color);

        drawHelper(canvas, paint);

        paint.setColor(temp);
    }

    public void clear(Canvas canvas, Paint paint) {
        radius += 1;
        drawHelper(canvas, paint);
        radius -= 1;
    }

    public int checkBoundary(float x, float y) {
        if ((x >= x1 - radius) && (x <= x1 + radius) && (y >= y1 - radius) && (y <= y1 + radius)) {
            return 2;
        }
        return 0;
    }

    public void dotBoundary(Canvas canvas, Paint paint) {
        canvas.drawCircle(x1, y1, radius - 8, paint);
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(color);
        out.writeFloat(radius);
    }

    public final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Circle createFromParcel(Parcel in) {

            return new Circle(in);
        }

        public Circle[] newArray(int size) {
            return new Circle[size];
        }
    };

    public Circle(Parcel in) {
        super(in);
        this.color = in.readInt();
        this.radius = in.readFloat();
    }
}