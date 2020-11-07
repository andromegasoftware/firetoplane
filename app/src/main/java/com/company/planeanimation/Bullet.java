package com.company.planeanimation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bullet {

    int bulletX;
    int bulletY;
    int bVelocity;
    static Bitmap bullet;

    public Bullet(Context context){
        bullet = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);
        bulletX = PlaneAnim.canvasWidth/2 - bullet.getWidth()/2;
        bulletY = PlaneAnim.canvasHeight - PlaneAnim.tankHeight;
        bVelocity = 40;
    }

    public static int getBulletHeight(){
        return bullet.getHeight();
    }
    public static int getBulletWidth(){
        return bullet.getWidth();
    }

}
