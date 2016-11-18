package com.example.logwall.str;

import com.example.logwall.str.SimpleGesture.SimpleGestureListener;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements SimpleGestureListener{
    private SimpleGesture detector;
    DrawView drawView;
    Canvas c;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        drawView=new DrawView(this);

        setContentView(drawView);
        drawView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                 switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        if (x > DrawView.width/2+DrawView.xinc/2 && x <DrawView.width/2+DrawView.xinc && y > DrawView.yinc && y <DrawView.yinc+DrawView.yinc/2)
                            drawView.undo = 1;
                        if (x > DrawView.width/2+DrawView.xinc*3/2 && x <DrawView.width/2+2*DrawView.xinc && y > DrawView.yinc && y <DrawView.yinc+DrawView.yinc/2)
                            drawView.reset = 1;
                        drawView.invalidate();
                        break;
                    default:
                }
                return true;
            }
        });




        // Detect touched area
        detector = new SimpleGesture(this,this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }
    @Override
    public void onSwipe(int direction) {
        String str = "";

        try{

            switch (direction) {

                case SimpleGesture.SWIPE_RIGHT :

                    drawView.rig=1;
                    drawView.invalidate();
                    str = "Swipe Right";
                    break;
                case SimpleGesture.SWIPE_LEFT :

                    drawView.lef=1;
                    drawView.invalidate();
                    str = "Swipe Left";
                    break;
                case SimpleGesture.SWIPE_DOWN :  str = "Swipe Down";
                    drawView.dwn=1;
                    drawView.invalidate();
                    break;
                case SimpleGesture.SWIPE_UP :
                    str = "Swipe Up";
                    drawView.u=1;
                    drawView.invalidate();
                    break;

            }}catch(Exception e){
            Toast.makeText(getBaseContext(),"error is"+e,Toast.LENGTH_LONG).show();
        }
        //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDoubleTap() {
        // Toast.makeText(this, DrawView.xinc+"<-w,h->"+DrawView.yinc, Toast.LENGTH_SHORT).show();
    }

}