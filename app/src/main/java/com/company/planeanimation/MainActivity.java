package com.company.planeanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    PlaneAnim planeAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        planeAnim = new PlaneAnim(this);
        setContentView(planeAnim);

    }
}