package com.cdcompany.firebook;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

import java.util.jar.Attributes;

public class MyEditText extends androidx.appcompat.widget.AppCompatEditText {
    private BackKeyListener backKeyListener;
    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    public interface BackKeyListener {
        void onBackPressed();
    }

    public void setBackKeyListener(BackKeyListener backKeyListener) {
        this.backKeyListener = backKeyListener;
    }

//    public MyEditText(Context context, AttributeSet attributes, defStyleAttr) {
//        super(context);
//    }

//    EditText(context, attrs, defStyleAttr) {

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK &&
                event.getAction() == KeyEvent.ACTION_UP) {
            if (backKeyListener != null) {
                backKeyListener.onBackPressed();
            }
            // do your stuff
            return false;
        }
        return super.dispatchKeyEvent(event);
    }
}
