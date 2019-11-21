package com.example.jsketch;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.File;
import java.util.UUID;
import android.net.Uri;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    DrawingView dv;
    // all buttons
    ImageButton currColor, colorBtn1, colorBtn2, colorBtn3, colorBtn4,
            selectBtn, circleBtn, eraseBtn, recBtn, saveBtn, lineBtn;

    // all information needs to be saved
    boolean selectPressed = false;
    boolean erasePressed = false;
    boolean linePressed = false;
    boolean recPressed = false;
    boolean circlePressed = false;
    int colorPressed = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setup process
        initialize();
    }

    private void initialize() {
        // set the main content
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        dv = new DrawingView(this.getBaseContext(), this);
        ViewGroup view_group = (ViewGroup) findViewById(R.id.drawing);
        view_group.addView(dv);

        // get all the buttons ready
        colorBtn1 = findViewById(R.id.color_btn1);
        colorBtn2 = findViewById(R.id.color_btn2);
        colorBtn3 = findViewById(R.id.color_btn3);
        colorBtn4 = findViewById(R.id.color_btn4);
        colorBtn1.setOnClickListener(this);
        colorBtn2.setOnClickListener(this);
        colorBtn3.setOnClickListener(this);
        colorBtn4.setOnClickListener(this);
        currColor = colorBtn1;
        currColor.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.color_pressed));

        selectBtn = findViewById(R.id.select_btn);
        selectBtn.setOnClickListener(this);

        circleBtn = findViewById(R.id.circle_btn);
        circleBtn.setOnClickListener(this);

        eraseBtn = findViewById(R.id.erase_btn);
        eraseBtn.setOnClickListener(this);

        recBtn = findViewById(R.id.rec_btn);
        recBtn.setOnClickListener(this);

        saveBtn = findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(this);

        lineBtn = findViewById(R.id.line_btn);
        lineBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // check which button got clicked
        if (!(view.getId() == R.id.color_btn1 || view.getId() == R.id.color_btn2
            || view.getId() == R.id.color_btn3 || view.getId() == R.id.color_btn4)) {
            selectBtn.setBackgroundColor(0xFFFFFFFF);
            eraseBtn.setBackgroundColor(0xFFFFFFFF);
            lineBtn.setBackgroundColor(0xFFFFFFFF);
            circleBtn.setBackgroundColor(0xFFFFFFFF);
            recBtn.setBackgroundColor(0xFFFFFFFF);
            saveBtn.setBackgroundColor(0xFFFFFFFF);

            selectPressed = false;
            erasePressed = false;
            linePressed = false;
            recPressed = false;
            circlePressed = false;
        }

        // to visualize the clicked button
        if(view.getId() == R.id.select_btn){
            selectBtn.setBackgroundColor(0xFFEDEDED);
            dv.setCurrent(1);
            selectPressed = true;
        } else if (view.getId() == R.id.erase_btn){
            eraseBtn.setBackgroundColor(0xFFEDEDED);
            dv.setCurrent(2);
            erasePressed = true;
        } else if (view.getId() == R.id.line_btn){
            lineBtn.setBackgroundColor(0xFFEDEDED);
            dv.setCurrent(3);
            linePressed = true;
        } else if (view.getId() == R.id.circle_btn){
            circleBtn.setBackgroundColor(0xFFEDEDED);
            dv.setCurrent(4);
            circlePressed = true;
        } else if (view.getId() == R.id.rec_btn){
            recBtn.setBackgroundColor(0xFFEDEDED);
            dv.setCurrent(5);
            recPressed = true;
        } else if (view.getId() == R.id.save_btn){
            saveBtn.setBackgroundColor(0xFFEDEDED);
            saveCanvas();
        } else if (view.getId() == R.id.color_btn1){
            currColor.setImageResource(android.R.color.transparent);
            currColor = colorBtn1;
            currColor.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.color_pressed));
            String color = currColor.getTag().toString();
            dv.setColor(color);
            colorPressed = 1;
        } else if (view.getId() == R.id.color_btn2){
            currColor.setImageResource(android.R.color.transparent);
            currColor = colorBtn2;
            currColor.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.color_pressed));
            String color = currColor.getTag().toString();
            dv.setColor(color);
            colorPressed = 2;
        } else if (view.getId() == R.id.color_btn3){
            currColor.setImageResource(android.R.color.transparent);
            currColor = colorBtn3;
            currColor.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.color_pressed));
            String color = currColor.getTag().toString();
            dv.setColor(color);
            colorPressed = 3;
        } else if (view.getId() == R.id.color_btn4){
            currColor.setImageResource(android.R.color.transparent);
            currColor = colorBtn4;
            currColor.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.color_pressed));
            String color = currColor.getTag().toString();
            dv.setColor(color);
            colorPressed = 4;
        }
    }

    public void setUnpressed() {
        colorBtn1.setImageResource(android.R.color.transparent);
        colorBtn2.setImageResource(android.R.color.transparent);
        colorBtn3.setImageResource(android.R.color.transparent);
        colorBtn4.setImageResource(android.R.color.transparent);
        currColor.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.color_pressed));
    }

    public void setPressed(int color_ind) {
        colorBtn1.setImageResource(android.R.color.transparent);
        colorBtn2.setImageResource(android.R.color.transparent);
        colorBtn3.setImageResource(android.R.color.transparent);
        colorBtn4.setImageResource(android.R.color.transparent);
        if (color_ind == 1) {
            colorBtn1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.color_pressed));
        } else if (color_ind == 2) {
            colorBtn2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.color_pressed));
        } else if (color_ind == 3) {
            colorBtn3.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.color_pressed));
        } else if (color_ind == 4) {
            colorBtn4.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.color_pressed));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save all the needed information
        outState.putParcelableArrayList("Shapes", dv.getShapes());
        outState.putInt("color", dv.getColor());
        outState.putInt("selected", dv.getSelected());
        outState.putParcelable("selectedShape", dv.getSelectedShape());
        outState.putFloat("selectX", dv.getSelectX());
        outState.putFloat("selectY", dv.getSelectY());

        outState.putBoolean("selectPressed", selectPressed);
        outState.putBoolean("erasePressed", erasePressed);
        outState.putBoolean("linePressed", linePressed);
        outState.putBoolean("recPressed", recPressed);
        outState.putBoolean("circlePressed", circlePressed);
        outState.putInt("colorPressed", colorPressed);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // restore all the information
        dv.setShapes(savedInstanceState.<Shape>getParcelableArrayList("Shapes"));
        dv.restoreColor(savedInstanceState.getInt("color"));
        dv.setSelected(savedInstanceState.getInt("selected"));
        dv.setSelectedShape(savedInstanceState.<Shape>getParcelable("SelectedShape"));
        dv.setSelectX(savedInstanceState.getFloat("selectX"));
        dv.setSelectY(savedInstanceState.getFloat("selectY"));

        selectPressed = savedInstanceState.getBoolean("selectPressed");
        erasePressed = savedInstanceState.getBoolean("erasePressed");
        linePressed = savedInstanceState.getBoolean("linePressed");
        recPressed = savedInstanceState.getBoolean("recPressed");
        circlePressed = savedInstanceState.getBoolean("circlePressed");
        colorPressed = savedInstanceState.getInt("colorPressed");

        // restore button stats before rotation
        if (selectPressed) {
            selectBtn.setBackgroundColor(0xFFEDEDED);
            dv.setCurrent(1);
        } else if (erasePressed) {
            eraseBtn.setBackgroundColor(0xFFEDEDED);
            dv.setCurrent(2);
        } else if (linePressed) {
            lineBtn.setBackgroundColor(0xFFEDEDED);
            dv.setCurrent(3);
        } else if (recPressed) {
            recBtn.setBackgroundColor(0xFFEDEDED);
            dv.setCurrent(5);
        } else if (circlePressed) {
            circleBtn.setBackgroundColor(0xFFEDEDED);
            dv.setCurrent(4);
        } else if (colorPressed == 1) {
            colorBtn1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.color_pressed));
        } else if (colorPressed == 2) {
            colorBtn2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.color_pressed));
        } else if (colorPressed == 3) {
            colorBtn3.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.color_pressed));
        } else if (colorPressed == 4) {
            colorBtn4.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.color_pressed));
        }
    }

    private void saveCanvas() {
        AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
        saveDialog.setTitle("Save and share");
        saveDialog.setMessage("Want to save the drawing to your computer?");
        saveDialog.setPositiveButton("Save and share", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dv.setDrawingCacheEnabled(true);
                String root = getApplicationContext().getExternalFilesDir(null).getAbsolutePath();

                String randomName = UUID.randomUUID().toString()+".jpg";
                File file = new File(root, randomName);
                try {
                    file.createNewFile();
                    FileOutputStream os = new FileOutputStream(file);
                    dv.getDrawingCache().compress(Bitmap.CompressFormat.JPEG, 100, os);

                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                    Uri imageUri = Uri.fromFile(file);

                    // start sharing
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                    shareIntent.setType("image/*");
                    startActivity(Intent.createChooser(shareIntent, "Share your image to "));
                } catch (Exception e) {
                    System.out.println(e);
                }

                dv.destroyDrawingCache();
            }
        });
        saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();
            }
        });
        saveDialog.show();
    }
}
