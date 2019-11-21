package com.example.jsketch;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;

public class Rectangle extends Shape implements Parcelable {
    float left;
    float top;
    float right;
    float bottom;
    private int color;

    public Rectangle(float x, float y) {
        super(x, y);
    }

    private void drawHelper(Canvas canvas, Paint paint) {
        canvas.drawRect(left, top, right, bottom, paint);

        paint.setStyle(Paint.Style.FILL);
    }

    public void update(float x, float y) {
        x1 += x;
        x2 += x;
        y1 += y;
        y2 += y;
        left += x;
        right += x;
        top += y;
        bottom += y;
    }
    public int getColor() {return color;}

    // Draw using the current matrix
    public void draw(Canvas canvas, Paint paint, int c) {
        color = c;

        if (x1 < x2) {
            left = x1;
            right = x2;
        } else {
            left = x2;
            right = x1;
        }
        if (y1 < y2) {
            top = y1;
            bottom = y2;
        } else {
            top = y2;
            bottom = y1;
        }
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
        if ((x >= left) && (x <= right) && (y >= top) && (y <= bottom)) {
            return 3;
        }
        return 0;
    }

    public void dotBoundary(Canvas canvas, Paint paint) {
        canvas.drawRect(left, top, right, bottom, paint);
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(color);
        out.writeFloat(left);
        out.writeFloat(top);
        out.writeFloat(right);
        out.writeFloat(bottom);
    }

    public final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Rectangle createFromParcel(Parcel in) {

            return new Rectangle(in);
        }

        public Rectangle[] newArray(int size) {
            return new Rectangle[size];
        }
    };

    public Rectangle(Parcel in) {
        super(in);
        this.color = in.readInt();
        this.left = in.readFloat();
        this.right = in.readFloat();
        this.top = in.readFloat();
        this.bottom = in.readFloat();
    }
}
