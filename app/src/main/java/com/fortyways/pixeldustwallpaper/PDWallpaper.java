package com.fortyways.pixeldustwallpaper;


import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;



public class PDWallpaper extends WallpaperService
{
    @Override
    public Engine onCreateEngine() {
        return new MyEngine();
    }
    public class MyEngine extends WallpaperService.Engine{
        private final Handler handler=new Handler();
        private final Runnable Runner=new Runnable() {
            @Override
            public void run() {
                draw();
            }
        };
        private List<Pixel> pixels;
        private Paint paint=new Paint();
        private int width,height;
        private boolean visible=true;
        private int maxNumber;
        public MyEngine(){
            SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(PDWallpaper.this);
            maxNumber=Integer.valueOf(prefs.getString("amount","20"));
            pixels=new ArrayList<Pixel>();
            paint.setAntiAlias(true);
            paint.setColor(Color.CYAN);
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(10f);

            handler.post(Runner);

        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible=visible;
            if(visible){
                handler.post(Runner);
            }
            else{
                handler.removeCallbacks(Runner);
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            this.visible=false;
            handler.removeCallbacks(Runner);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            this.width=width;
            this.height=height;
            super.onSurfaceChanged(holder, format, width, height);
        }


        public void draw(){
            SurfaceHolder holder=getSurfaceHolder();
            Canvas canvas =null;
            try{
                canvas=holder.lockCanvas();
                if(canvas!=null){

                        int x = (int) (width * Math.random());
                        int y = (int) (height * Math.random());
                    if(pixels.size()<maxNumber) {
                        Pixel p=new Pixel(x,y);
                        p.setRandomColor();
                        pixels.add(p); //}
                    }

                    for (Pixel p:pixels) {
                        if(p.width>=20){
                            p.expand=false;
                        }
                        if(p.expand){
                        p.width+=0.8;
                        p.height+=0.8;}
                        else{
                            p.width-=0.8;
                            p.height-=0.8;

                        }

                        if(p.width<=0){
                            pixels.remove(pixels.indexOf(p));break;
                        }
                    }drawPixels(canvas,pixels);

                }
            }finally {
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
            }
            handler.removeCallbacks(Runner);
            if (visible) {
                handler.postDelayed(Runner, 20);
            }

        }
        public void drawPixels(Canvas canvas,List<Pixel>pixels){
            canvas.drawColor(Color.BLACK);

            for (Pixel p:
                 pixels) {
                paint.setColor(p.color);
                canvas.drawRect(p.x,p.y,p.x+p.width,p.y+p.height,paint);

            }
        }


    }
    }

