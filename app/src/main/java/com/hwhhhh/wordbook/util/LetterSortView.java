package com.hwhhhh.wordbook.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.hwhhhh.wordbook.R;

public class LetterSortView extends View {
    private static final String TAG = "LetterSortView";
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    private static String[] letters = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };
    private int choose = -1;
    private Paint paint = new Paint();  //新建画笔
    private TextView textView_title;

    public void setTextView_title(TextView textView_title) {
        this.textView_title = textView_title;
    }

    public LetterSortView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    public LetterSortView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public LetterSortView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();   //获取画板高度
        int width = getWidth();    //获取画板宽度
        int singleLetterHeight = height / letters.length;

        for (int i = 0; i < letters.length; i++) {
            paint.setColor(Color.parseColor("#9da0a4"));
            paint.setAntiAlias(true);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setTextSize(40);
            if (i == choose) {  //处于选中状态
                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }
            float xPos = width / 2 - paint.measureText(letters[i]) / 2; // 起点： x坐标等于宽度的一半减去字母的一半
            float yPos = singleLetterHeight * i + singleLetterHeight;       //y坐标
            canvas.drawText(letters[i], xPos, yPos, paint);
            paint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * letters.length);// 点击字母时， y坐标/总高度*字母数组的长度等于字母数组中的第c个元素
        setBackgroundResource(R.color.letterBackground);
        switch (action) {
            case MotionEvent.ACTION_UP:
                choose = -1;
                invalidate();
                if (textView_title != null) {
                    textView_title.setVisibility(INVISIBLE);
                }
                break;
            default:
                if (oldChoose != c) {
                    if (c >= 0 && c < letters.length) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(letters[c]);
                        }
                        if (textView_title != null) {
                            textView_title.setText(letters[c]);
                            textView_title.setVisibility(VISIBLE);
                        }
                        choose = c;
                        invalidate();
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }


    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {
        public void onTouchingLetterChanged(String s);
    }
}
