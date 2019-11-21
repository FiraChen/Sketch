package com.example.jsketch;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Parcelable;
import android.os.Parcel;

public class Shape implements Parcelable {
    float x1, y1, x2, y2;
    Matrix matrix, invertMatrix;

    // basic constructor
    Shape(float x, float y) {
        matrix = new Matrix();
        invertMatrix = new Matrix();
        x1 = x;
        x2 = x;
        y1 = y;
        y2 = y;
    }

    // set the end point of shape
    public void setEnd(float x, float y) {
        x2 = x;
        y2 = y;
    }

    public int getColor() { return 0;}
    public void update(float x, float y) {}
    public void draw(Canvas canvas, Paint paint, int color) {} // draw the shape
    public void redraw(Canvas canvas, Paint paint) {} // redraw shape using changed color
    public void clear(Canvas canvas, Paint paint) {} // clear the current shape
    public void dotBoundary(Canvas canvas, Paint paint) {} // dot boundary to show selected
    public int checkBoundary(float x, float y) { return 0; } // check if the click point is inside the current shape

    // the following functions are used for saving and restoring shapes
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeFloat(x1);
        out.writeFloat(x2);
        out.writeFloat(y1);
        out.writeFloat(y1);
    }

    public static final Parcelable.Creator<Shape> CREATOR
            = new Parcelable.Creator<Shape>() {
        public Shape createFromParcel(Parcel in) {
            return new Shape(in);
        }

        public Shape[] newArray(int size) {
            return new Shape[size];
        }
    };

    public Shape(Parcel in){
        this.x1 = in.readFloat();
        this.x2 = in.readFloat();
        this.y1 = in.readFloat();
        this.y2 = in.readFloat();
    }
}
