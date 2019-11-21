package com.example.jsketch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.DashPathEffect;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class DrawingView extends View {
    // tools for drawing purpose
    Paint drawp, canvasp, dottedp, whitep;
    Bitmap canvasbp;
    Canvas myCanvas;
    MainActivity mainAcitivity;

    int currColor = 0xFFA37C79;

    boolean is_select = false;
    boolean is_erase = false;
    boolean is_line = false;
    boolean is_rec = false;
    boolean is_circle = false;
    boolean drawing = false;
    boolean moving = false;

    int selected = 0;

    float selectX = 0;
    float selectY = 0;

    Shape selectedShape;
    ArrayList<Shape> shapes = new ArrayList<Shape>();

    public DrawingView(Context context, MainActivity activity) {
        super(context);
        mainAcitivity = activity;

        // general paint for painting
        drawp = new Paint(Paint.ANTI_ALIAS_FLAG);
        drawp.setColor(currColor);

        // dotted paint for selecting visualization
        dottedp = new Paint(Paint.ANTI_ALIAS_FLAG);
        dottedp.setARGB(255, 0, 0,0);
        dottedp.setStyle(Paint.Style.STROKE);
        dottedp.setStrokeWidth(10);
        dottedp.setPathEffect(new DashPathEffect(new float[] {30, 30}, 0));

        // white paint for clearing
        whitep = new Paint(Paint.ANTI_ALIAS_FLAG);
        whitep.setColor(Color.WHITE);

        canvasp = new Paint(Paint.DITHER_FLAG);
        canvasbp = Bitmap.createBitmap(1800, 1800, Bitmap.Config.ARGB_8888);
        myCanvas = new Canvas(canvasbp);
    }

    public void setCurrent(int curr) {
        // disable any button clicked previously
        is_select = false;
        is_erase = false;
        is_line = false;
        is_circle = false;
        is_rec = false;

        // select button got clicked
        if (curr == 1) {
            is_select = true;
        } else if (curr == 2) {
            // erase button got clicked
            is_erase = true;
            clearShape();
        } else if (curr == 3) {
            // line button got clicked
            is_line = true;
        } else if (curr == 4) {
            // circle button got clicked
            is_circle = true;
        } else if (curr == 5) {
            // rec button got clicked
            is_rec = true;
        }
        // also need to make the selected shape unselected
        if (curr == 3 || curr == 4 || curr == 5) {
            if (selected != 0) {
                removeDotted();
                selected = 0;
                selectedShape = null;
            }
        }
    }

    // function to set the current color
    public void setColor(String newColor){
        if (!drawing) {
            invalidate();
        }

        currColor = Color.parseColor(newColor);
        drawp.setColor(currColor);

        // change the selected drawing's color
        if (selected != 0) {
            selectedShape.draw(myCanvas, drawp, currColor);
            selectedShape.dotBoundary(myCanvas, dottedp);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // draw shape
        if (is_line || is_circle || is_rec) {
            onTouchEventShape(event);
        } else if (is_select) {
            // check if the position is inside a certain shape
            onTouchEventSelect(event);
        }
        return true;
    }

    private void onTouchEventShape(MotionEvent event) {
        int currEvent = event.getAction();
        // get the starting point here
        if (currEvent == MotionEvent.ACTION_DOWN) {
            // drawing process starts
            drawing = true;
            if (is_line) {
                // create the shape and add to the array
                Shape line = new Line(event.getX(), event.getY());
                shapes.add(line);
            } else if (is_circle) {
                Shape circle = new Circle(event.getX(), event.getY());
                shapes.add(circle);
            } else if(is_rec) {
                Shape rec = new Rectangle(event.getX(), event.getY());
                shapes.add(rec);
            }
            // expend the shape
        } else if (currEvent == MotionEvent.ACTION_MOVE) {
            Shape current = shapes.get(shapes.size() - 1);
            current.setEnd(event.getX(), event.getY());
        } else if (currEvent == MotionEvent.ACTION_UP) {
            Shape current = shapes.get(shapes.size() - 1);
            current.setEnd(event.getX(), event.getY());
            drawing = false;
        }
        invalidate();
    }

    private void onTouchEventSelect(MotionEvent event) {
        // if there is already a shape selected
        // remove the dotted effect previously
        int currEvent = event.getAction();
        if (currEvent == MotionEvent.ACTION_DOWN) {
            if (selected != 0) {
                removeDotted();
            }
            selectX = event.getX();
            selectY = event.getY();
            // find the shape
            findSelect(selectX, selectY);
        } else if (currEvent == MotionEvent.ACTION_MOVE && selected != 0) {
            // still need to find out how to clear the shape\
            if (!moving) {
                moving = true;
            }
            selectedShape.clear(myCanvas, whitep);
            selectedShape.update(event.getX() - selectX, event.getY() - selectY);
            selectX = event.getX();
            selectY = event.getY();
            drawAll();
            invalidate();
        } else if (currEvent == MotionEvent.ACTION_UP && selected != 0) {
            if (moving) {
                selectedShape.dotBoundary(myCanvas, dottedp);
                moving = false;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(canvasbp, 0, 0, canvasp);

        // if still in the drawing process
        if (drawing) {
            // draw on canvas to show each step
            if (is_line || is_circle || is_rec) {
                Shape current = shapes.get(shapes.size() - 1);
                current.draw(canvas, drawp, currColor);
            }
        } else if (!drawing){
            // draw the final shape
            if (is_line || is_circle || is_rec) {
                Shape current = shapes.get(shapes.size() - 1);
                current.draw(myCanvas, drawp, currColor);
            }
        }
    }

    // draw all the exsting shpes on the canvas
    private void drawAll() {
        for (Shape shape : shapes) {
            shape.redraw(myCanvas, drawp);
        }
    }

    // remove the dotted line of selected shape
    private void removeDotted() {
        selectedShape.clear(myCanvas, whitep);
        selectedShape.redraw(myCanvas, drawp);
        mainAcitivity.setUnpressed();
        invalidate();
    }

    private void clearShape() {
        // if no shape is selected, just disable the eraser
        if (selected == 0) {
            return;
        }
        // still need to find out how to clear the shape
        selectedShape.clear(myCanvas, whitep);

        // remove the shape from array
        shapes.remove(shapes.size() - 1);

        // redraw any shapes that overlaps
        drawAll();

        selected = 0;
        selectedShape = null;
        mainAcitivity.setUnpressed();
        invalidate();
    }

    private void findSelect(float x, float y) {
        // find the shape from newest to oldest to solve overlapping problem
        for (int i = shapes.size() - 1; i >= 0 ;i--) {
            Shape current = shapes.get(i);
            int inBoundary = current.checkBoundary(x, y);

            // if the current shape is in boundary
            if (inBoundary != 0) {
                selected = inBoundary;
                selectedShape = current;

                current.redraw(myCanvas, drawp);

                // move the shape to the front
                shapes.remove(i);
                shapes.add(selectedShape);

                // visualize what has been selected
                current.dotBoundary(myCanvas, dottedp);

                int shapeColor = current.getColor();
                colorPressShape(shapeColor);

                invalidate();
                return;
            }
        }
    }

    public void colorPressShape(int shapeColor) {
        if (shapeColor == 0xFFA37C79) {
            mainAcitivity.setPressed(1);
        } else if (shapeColor == 0xFFD4C592) {
            mainAcitivity.setPressed(2);
        } else if (shapeColor == 0xFFB0C999) {
            mainAcitivity.setPressed(3);
        } else if (shapeColor == 0xFF93C4C7) {
            mainAcitivity.setPressed(4);
        }
    }

    // functions for saving and restoring
    public ArrayList<Shape> getShapes() {
        return shapes;
    }
    public void setShapes(ArrayList<Shape> s) {
       shapes = s;
       drawAll();
    }
    public int getColor() {
        return currColor;
    }
    public void restoreColor(int c) {
        currColor = c;
    }
    public int getSelected() {
        return selected;
    }
    public void setSelected(int s) {
        selected = s;
    }
    public Shape getSelectedShape() {
        return selectedShape;
    }
    public void setSelectedShape(Shape s) { selectedShape = s; }
    public float getSelectX() { return selectX; }
    public void setSelectX(float x) { selectX = x; }
    public float getSelectY() { return selectY; }
    public void setSelectY(float y) { selectY = y; }
}
