package com.fortyways.pixeldustwallpaper;

import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;

public class Pixel extends ShapeDrawable {
    float x;
    float y;
    float width=0;
    float height=0;
    boolean expand=true;
    int color;
    public Pixel(float x,float y){
        this.x=x;
        this.y=y;

    }
    public Pixel(float x,float y,float width,float height){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
    }
    public void setRandomColor(){
        double j=Math.random();
        if(j<0.1){
            color=Color.CYAN;
        }
        else if(j<0.3){
            color=Color.RED;
        }
        else if(j<0.5){
            color=Color.BLUE;
        }
        else if(j<0.7){
            color=Color.GREEN;
        }
        else if(j<0.8){
            color=Color.MAGENTA;
        }
        else if(j<1){
            color=Color.YELLOW;
        }



    }
}
