package science.com.smartbrowse;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by hasmik on 5/9/15.
 */
public class BubbleService extends Service {
    private WindowManager windowManager;
    private TextView bubble;

    @Override
    public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        bubble = new TextView(this);
        bubble.setBackgroundResource(R.drawable.img);
        bubble.setTypeface(null, Typeface.BOLD);
        bubble.setTextColor(0xff000000);
        bubble.setText("1");

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;
        bubble.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        System.out.println("Action was UP");
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(bubble, params);
                        return true;
                }
                return false;
            }
        });


        windowManager.addView(bubble, params);

      /*  RotateAnimation ra = new RotateAnimation(
                0,
                -80,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(210);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);
        // Start the animation
        chatHead.startAnimation(ra);*/

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Bundle extras = intent.getExtras();
        if (extras == null)
            Log.d("Service", "null");
        else {
            Log.d("Service", "not null");
            String from = (String) extras.get("From");
            if (from.equalsIgnoreCase("2")) {
                bubble.setText("2");
            }
        }
    }

    public void updateBubble(){
        System.out.println("windowManager " + windowManager);
       /* ImageView newImage = new ImageView();
        newImage.setImageResource(R.drawable.ic_launcher);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;
        windowManager.addView(chatHead, params);*/

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bubble != null) windowManager.removeView(bubble);
    }


}
