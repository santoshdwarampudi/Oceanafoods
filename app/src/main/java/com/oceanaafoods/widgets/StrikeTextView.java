package com.oceanaafoods.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.oceanaafoods.R;

public class StrikeTextView extends AppCompatTextView {
    private int mColor;
    private Paint paint;

    public StrikeTextView (Context context) {
        super(context);
        init(context);
    }

    public StrikeTextView (Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StrikeTextView (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        Resources resources = context.getResources();
        //Color
        mColor = resources.getColor(R.color.red);

        paint = new Paint();
        paint.setColor(mColor);
        //Width
        paint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
      //  canvas.drawLine(0, 25, getWidth(), 25, paint);
        Paint paint = new Paint();
        paint.setColor(getContext().getResources().getColor(R.color.red));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrikeThruText(true);
        paint.setStrokeWidth(getContext().getResources().getDimension(R.dimen._1sdp));
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        super.onDraw(canvas);
        float width = getWidth();
        float heigh = getHeight();
        canvas.drawLine(width/10, heigh/10, (width-width/10),(heigh-heigh/10), paint);
    }

}
