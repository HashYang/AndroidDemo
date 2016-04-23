package demo.chen.com.androiddemo.chapter3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import demo.chen.com.androiddemo.R;

/**
 * Created by Chen on 15/12/23.
 */
public class ArcView extends View {
    private Paint arcPaint;
    private float mCenter;
    private float mRadius;
    private RectF mArcRect;
    private SweepGradient mSweepGradient;
    public ArcView(Context context) {
        super(context);
        init();
    }

    private void init() {
        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setDither(true);                    // set the dither to true
        arcPaint.setStrokeJoin(Paint.Join.ROUND);    // set the join to round you want
        arcPaint.setStrokeCap(Paint.Cap.ROUND);      // set the paint cap to round too
        arcPaint.setPathEffect(new CornerPathEffect(20));   // set the path effect when they join.

        mCenter = 300;
        mRadius = 208;

        mArcRect = new RectF(mCenter-mRadius,mCenter-mRadius,mCenter+mRadius,mCenter+mRadius);
        int[] colors = {0xFFE5BD7D,0xFFFAAA64,

                0xFFFFFFFF, 0xFF6AE2FD,
                0xFF8CD0E5, 0xFFA3CBCB,
                0xFFBDC7B3, 0xFFD1C299, 0xFFE5BD7D, };
        float[] positions = {0,1f/8,2f/8,3f/8,4f/8,5f/8,6f/8,7f/8,1};
        mSweepGradient = new SweepGradient(mCenter, mCenter, colors , positions);
    }

    public ArcView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        arcPaint.setStrokeWidth(20);
        arcPaint.setShader(mSweepGradient);
        canvas.drawArc(mArcRect, -240, 300,false, arcPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = Integer.MAX_VALUE;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }
        int size = width;
        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == View.MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(size, heightSize);
        } else {
            //Be whatever you want
            height = size;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

}
