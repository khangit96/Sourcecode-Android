package com.demooutlinetext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Administrator on 6/18/2017.
 */

public class MyTextView extends android.support.v7.widget.AppCompatTextView {
    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setText("Nguyen Ho Duy Khang");
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
     /*   super.onDraw(canvas);
        final int x = this.getLeft();
        final int y = this.getBottom();
        Paint p=new Paint();
       String mText = this.getText().toString();

        p.setStrokeWidth(30);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeJoin(Paint.Join.ROUND);
        p.setColor(0xffffffff);

        canvas.drawText(mText, x, y, p);

        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.GRAY);

        canvas.drawText(mText, x, y, p);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(60);                // text size
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextAlign(Paint.Align.CENTER);

        canvas.drawText("My text", 50, 50, paint);

        // draw again in white, slightly smaller
        paint.setColor(getResources().getColor(android.R.color.white));
        paint.setTextSize(18);                // text size

        canvas.drawText("My text", 50, 50, paint);

        canvas.drawColor(Color.BLACK);
*/
    /*    final int x = this.getLeft();
        final int y = this.getBottom();

        String mText = this.getText().toString();
        Paint p = new Paint();
        p.setStrokeWidth(50);
        p.setStyle(Style.STROKE);
        p.setStrokeJoin(Paint.Join.ROUND);
        p.setColor(0xffffffff);
        p.setTextAlign(Paint.Align.CENTER);

        canvas.drawText(mText, x, y, p);

        p.setStyle(Style.FILL);
        p.setColor(Color.RED);
        p.setTextAlign(Paint.Align.CENTER);

        canvas.drawText(mText, x, y, p);*/
     /*   Bitmap kangoo = BitmapFactory.decodeResource(getResources(),
                R.drawable.icon);*/
        canvas.drawColor(Color.BLACK);
        // canvas.drawBitmap(kangoo, 130, 10, null);

        Paint t_paint = new Paint();

        t_paint.setColor(Color.RED); //Color.CYAN);

        canvas.drawRect(0, 0, 320, 480, t_paint);

        //Typeface tf = Typeface.create("Helvetica",Typeface.DEFAULT_BOLD);
        Paint strokePaint = new Paint();
     //   strokePaint.setARGB(255, 255, 0, 0);
        strokePaint.setTextAlign(Paint.Align.CENTER);
        strokePaint.setTextSize(24);
        strokePaint.setTypeface(Typeface.DEFAULT_BOLD);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(Color.RED);
        strokePaint.setStrokeWidth(2);

        Paint textPaint = new Paint();
       // textPaint.setARGB(255, 255, 255, 255);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(24);
        textPaint.setColor(Color.RED);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);

        canvas.drawText("Nguyen Ho Duy Khang", 100, 100, strokePaint);
        canvas.drawText("Nguyen Ho Duy Khang", 100, 100, textPaint);

        //super.draw(canvas);//, mapView, shadow);
    }

}
