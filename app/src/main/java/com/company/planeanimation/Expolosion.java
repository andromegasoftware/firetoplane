package com.company.planeanimation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Expolosion {

    Bitmap explosion[] = new Bitmap[13];
    public Expolosion(Context context){
        explosion[0] = BitmapFactory.decodeResource(context.getResources(), R.mipmap.a1);
        explosion[1] = BitmapFactory.decodeResource(context.getResources(), R.mipmap.a2);
        explosion[2] = BitmapFactory.decodeResource(context.getResources(), R.mipmap.a3);
        explosion[3] = BitmapFactory.decodeResource(context.getResources(), R.mipmap.a4);
        explosion[4] = BitmapFactory.decodeResource(context.getResources(), R.mipmap.a5);
        explosion[5] = BitmapFactory.decodeResource(context.getResources(), R.mipmap.a6);
        explosion[6] = BitmapFactory.decodeResource(context.getResources(), R.mipmap.a7);
        explosion[7] = BitmapFactory.decodeResource(context.getResources(), R.mipmap.a8);
        explosion[8] = BitmapFactory.decodeResource(context.getResources(), R.mipmap.a9);
        explosion[9] = BitmapFactory.decodeResource(context.getResources(), R.mipmap.a10);
        explosion[10] = BitmapFactory.decodeResource(context.getResources(), R.mipmap.a11);
        explosion[11] = BitmapFactory.decodeResource(context.getResources(), R.mipmap.a12);
        explosion[12] = BitmapFactory.decodeResource(context.getResources(), R.mipmap.a13);
    }
    public Bitmap getExplotion(int explotionFrame){

        return explosion[explotionFrame];
    }
    public int getExplotionWidth(){

        return explosion[0].getWidth();
    }
    public int getExplotionHeigth(){

        return explosion[0].getHeight();
    }
}
