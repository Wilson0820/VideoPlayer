package com.fxc.myvideoplayer.Player;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;

public class MyMediaController extends MediaController {

    public MyMediaController(Context context, boolean useFastForward) {
        super(context, useFastForward);

    }
    public void setAnchorView(View view){
        super.setAnchorView(view);

    }

   /* @Override
    public void hide() {
        super.show();
    }
    */
   @Override
   public boolean onInterceptTouchEvent(MotionEvent event) {
       switch (event.getAction()) {
           case MotionEvent.ACTION_UP:
               //showTopMenu();
               Log.i("showTopMenu();", "onInterceptTouchEvent:showTopMenu();555 ");
               break;
           default:
               break;
       }

       Log.i("showTopMenu();", "onInterceptTouchEvent:showTopMenu()7777; ");

       return super.onInterceptTouchEvent(event);
   }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                show(0); // show until hide is called
               // MovieActivity.this.showTopMenu();
                break;
            case MotionEvent.ACTION_UP:
                show(3000); // start timeout
                break;
            case MotionEvent.ACTION_CANCEL:
                hide();
                break;
            default:
                break;
        }
        return true;
    }

}
