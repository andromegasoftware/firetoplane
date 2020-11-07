package com.company.planeanimation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class PlaneAnim extends View {

    Bitmap background, tank;
    Bitmap plane[] = new Bitmap[15];
    int planeX, planeY;
    Rect dest;
    int dWidth;
    int dHeight;
    int tankWidth;
    static int tankHeight;
    static int canvasHeight;
    static int canvasWidth;

    int planeFrame = 0;
    Random random;
    int planeVelocity = 20;
    int planeWidth, planeHeight;

    Handler handler;
    Runnable runnable;
    final int UPDATE_MILIS = 30;

    ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    Context context;
    int count = 0;
    SoundPool soundPool;
    int fire = 0;
    int blast = 0;

    boolean explotionState = false;
    int explotionFrame = 0;
    Expolosion expolosion;
    int explotionX, explotionY;

    public PlaneAnim(Context context) {
        super(context);
        this.context = context;
        handler = new Handler();
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        plane[0] = BitmapFactory.decodeResource(getResources(), R.mipmap.framea);
        plane[1] = BitmapFactory.decodeResource(getResources(), R.mipmap.frameb);
        plane[2] = BitmapFactory.decodeResource(getResources(), R.mipmap.framec);
        plane[3] = BitmapFactory.decodeResource(getResources(), R.mipmap.framed);
        plane[4] = BitmapFactory.decodeResource(getResources(), R.mipmap.framee);
        plane[5] = BitmapFactory.decodeResource(getResources(), R.mipmap.framef);
        plane[6] = BitmapFactory.decodeResource(getResources(), R.mipmap.frameg);
        plane[7] = BitmapFactory.decodeResource(getResources(), R.mipmap.frameh);
        plane[8] = BitmapFactory.decodeResource(getResources(), R.mipmap.framei);
        plane[9] = BitmapFactory.decodeResource(getResources(), R.mipmap.framej);
        plane[10] = BitmapFactory.decodeResource(getResources(), R.mipmap.framek);
        plane[11] = BitmapFactory.decodeResource(getResources(), R.mipmap.framel);
        plane[12] = BitmapFactory.decodeResource(getResources(), R.mipmap.framem);
        plane[13] = BitmapFactory.decodeResource(getResources(), R.mipmap.framen);
        plane[14] = BitmapFactory.decodeResource(getResources(), R.mipmap.frameo);

        tank = BitmapFactory.decodeResource(getResources(), R.drawable.tank);

        random = new Random();

        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;

        planeX = dWidth + random.nextInt(200);
        planeY = random.nextInt(100);

        dest = new Rect(0, 0, dWidth, dHeight);
        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        fire = soundPool.load(context, R.raw.fire, 1);
        blast = soundPool.load(context, R.raw.bomb, 1);
        expolosion = new Expolosion(context);
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasHeight = getHeight();
        canvasWidth = getWidth();
        //canvas.drawBitmap(background, 0, 0, null);
        canvas.drawBitmap(background, null, dest, null);

        planeFrame++;
        if(planeFrame == 15){
            planeFrame = 0;
        }
        planeX = planeX - planeVelocity;
        planeWidth = plane[0].getWidth();
        planeHeight = plane[0].getHeight();

        tankWidth = tank.getWidth();
        tankHeight = tank.getHeight();

        if(planeX < -planeWidth){
            planeX = dWidth + random.nextInt(200);
            planeY = random.nextInt(500);
            planeVelocity = 5 + random.nextInt(16);
        }

        for(int i = 0; i < bullets.size(); i++){
            if(bullets.get(i).bulletY > -Bullet.getBulletHeight()){
                bullets.get(i).bulletY -= bullets.get(i).bVelocity;
                canvas.drawBitmap(bullets.get(i).bullet, bullets.get(i).bulletX,
                        bullets.get(i).bulletY, null);

                if((bullets.get(i).bulletX >= planeX) && (bullets.get(i).bulletX <= planeX+planeWidth)
                && (bullets.get(i).bulletY >= planeY) && (bullets.get(i).bulletY < planeY + planeHeight)){

                    count++;
                    explotionState = true;
                    explotionX = planeX;
                    explotionY = planeY;

                    planeX = dWidth + random.nextInt(200);
                    planeY = random.nextInt(100);
                    planeVelocity = 5 + random.nextInt(16);
                    bullets.remove(i);
                    soundPool.play(blast, 1,1,0,0, 1);
                }
            }
            else {
                bullets.remove(i);
            }
        }

        if(explotionState){
            if(explotionFrame < 13){
                canvas.drawBitmap(expolosion.getExplotion(explotionFrame), explotionX,
                        explotionY, null);
                explotionFrame++;
            }
            else{
                explotionFrame = 0;
                explotionState = false;
            }
        }
        canvas.drawBitmap(plane[planeFrame], planeX, planeY, null);
        canvas.drawBitmap(tank, (dWidth/2 - tankWidth/2), (canvasHeight - tankHeight), null);

        handler.postDelayed(runnable, UPDATE_MILIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX = event.getX();
        float touchY = event.getY();
        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN){
            //we will check if we are touching on tank
            if(touchX >= (dWidth/2 - tankWidth/2) && touchX <= (dWidth/2 + tankWidth/2)
                    && touchY >= (canvasHeight - tankHeight)){
                //fire bullet
                if(bullets.size() < 3){
                    Bullet b = new Bullet(context);
                    bullets.add(b);
                    if(fire != 0){
                        soundPool.play(fire, 1,1,0,0, 1);
                    }
                }
            }
        }

        return true;
    }
}
