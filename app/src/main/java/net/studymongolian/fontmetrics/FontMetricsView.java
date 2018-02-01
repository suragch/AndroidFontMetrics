package net.studymongolian.fontmetrics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;


public class FontMetricsView extends View {

    public final static int DEFAULT_FONT_SIZE_PX = 200;
    //private static final int PURPLE = Color.parseColor("#9315db");
    //private static final int ORANGE = Color.parseColor("#ff8a00");
    private static final float STROKE_WIDTH = 5.0f;

    private String mText;
    private int mTextSize;
    private Paint mAscentPaint;
    private Paint mTopPaint;
    private Paint mBaselinePaint;
    private Paint mDescentPaint;
    private Paint mBottomPaint;
    private Paint mMeasuredWidthPaint;
    private Paint mTextBoundsPaint;
    private TextPaint mTextPaint;
    private Paint mLinePaint;
    private Paint mRectPaint;
    private Rect mBounds;
    private boolean mIsTopVisible;
    private boolean mIsAscentVisible;
    private boolean mIsBaselineVisible;
    private boolean mIsDescentVisible;
    private boolean mIsBottomVisible;
    private boolean mIsBoundsVisible;
    private boolean mIsWidthVisible;


    public FontMetricsView(Context context) {
        super(context);
        init();
    }


    public FontMetricsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mText = "My text line";
        mTextSize = DEFAULT_FONT_SIZE_PX;
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(Color.BLACK);

        mLinePaint = new Paint();
        mLinePaint.setColor(Color.RED);
        mLinePaint.setStrokeWidth(STROKE_WIDTH);

        mAscentPaint = new Paint();
        mAscentPaint.setColor(getResources().getColor(R.color.ascent));
        mAscentPaint.setStrokeWidth(STROKE_WIDTH);

        mTopPaint = new Paint();
        mTopPaint.setColor(getResources().getColor(R.color.top));
        mTopPaint.setStrokeWidth(STROKE_WIDTH);

        mBaselinePaint = new Paint();
        mBaselinePaint.setColor(getResources().getColor(R.color.baseline));
        mBaselinePaint.setStrokeWidth(STROKE_WIDTH);

        mBottomPaint = new Paint();
        mBottomPaint.setColor(getResources().getColor(R.color.bottom));
        mBottomPaint.setStrokeWidth(STROKE_WIDTH);

        mDescentPaint = new Paint();
        mDescentPaint.setColor(getResources().getColor(R.color.descent));
        mDescentPaint.setStrokeWidth(STROKE_WIDTH);

        mMeasuredWidthPaint = new Paint();
        mMeasuredWidthPaint.setColor(getResources().getColor(R.color.measured_width));
        mMeasuredWidthPaint.setStrokeWidth(STROKE_WIDTH);

        mTextBoundsPaint = new Paint();
        mTextBoundsPaint.setColor(getResources().getColor(R.color.text_bounds));
        mTextBoundsPaint.setStrokeWidth(STROKE_WIDTH);
        mTextBoundsPaint.setStyle(Paint.Style.STROKE);

        mRectPaint = new Paint();
        mRectPaint.setColor(Color.BLACK);
        mRectPaint.setStrokeWidth(STROKE_WIDTH);
        mRectPaint.setStyle(Paint.Style.STROKE);


        mBounds = new Rect();

        mIsTopVisible = true;
        mIsAscentVisible = true;
        mIsBaselineVisible = true;
        mIsDescentVisible = true;
        mIsBottomVisible = true;
        mIsBoundsVisible = true;
        mIsWidthVisible = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // center the text baseline vertically
        int verticalAdjustment = this.getHeight() / 2;
        canvas.translate(0, verticalAdjustment);

        float startX = getPaddingLeft();
        float startY = 0;
        float stopX = this.getMeasuredWidth();
        float stopY = 0;

        // draw text
        canvas.drawText(mText, startX, startY, mTextPaint); // x=0, y=0

        // draw lines
        startX = 0;

        if (mIsTopVisible) {
            startY = mTextPaint.getFontMetrics().top;
            stopY = startY;
            canvas.drawLine(startX, startY, stopX, stopY, mTopPaint);
        }

        if (mIsAscentVisible) {
            startY = mTextPaint.getFontMetrics().ascent;
            stopY = startY;
            //mLinePaint.setColor(Color.GREEN);
            canvas.drawLine(startX, startY, stopX, stopY, mAscentPaint);
        }

        if (mIsBaselineVisible) {
            startY = 0;
            stopY = startY;
            canvas.drawLine(startX, startY, stopX, stopY, mBaselinePaint);
        }

        if (mIsDescentVisible) {
            startY = mTextPaint.getFontMetrics().descent;
            stopY = startY;
            //mLinePaint.setColor(Color.BLUE);
            canvas.drawLine(startX, startY, stopX, stopY, mDescentPaint);
        }

        if (mIsBottomVisible) {
            startY = mTextPaint.getFontMetrics().bottom;
            stopY = startY;
            // mLinePaint.setColor(ORANGE);
            mLinePaint.setColor(Color.RED);
            canvas.drawLine(startX, startY, stopX, stopY, mBaselinePaint);
        }

        if (mIsBoundsVisible) {

            mTextPaint.getTextBounds(mText, 0, mText.length(), mBounds);
            float dx = getPaddingLeft();
            canvas.drawRect(mBounds.left + dx, mBounds.top, mBounds.right + dx, mBounds.bottom, mTextBoundsPaint);
        }

        if (mIsWidthVisible) {


            // get measured width
            float width = mTextPaint.measureText(mText);

            // get bounding width so that we can compare them
            mTextPaint.getTextBounds(mText, 0, mText.length(), mBounds);

            // draw vertical line just before the left bounds
            startX = getPaddingLeft() + mBounds.left - (width - mBounds.width()) / 2;
            stopX = startX;
            startY = -verticalAdjustment;
            stopY = startY + this.getHeight();
            canvas.drawLine(startX, startY, stopX, stopY, mMeasuredWidthPaint);

            // draw vertical line just after the right bounds
            startX = startX + width;
            stopX = startX;
            canvas.drawLine(startX, startY, stopX, stopY, mMeasuredWidthPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = 200;
        int height = 200;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthRequirement = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthRequirement;
        } else if (widthMode == MeasureSpec.AT_MOST && width > widthRequirement) {
            width = widthRequirement;
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightRequirement = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightRequirement;
        } else if (heightMode == MeasureSpec.AT_MOST && width > heightRequirement) {
            height = heightRequirement;
        }

        setMeasuredDimension(width, height);
    }

    // getters
    public Paint.FontMetrics getFontMetrics() {
        return mTextPaint.getFontMetrics();
    }

    public Rect getTextBounds() {
        mTextPaint.getTextBounds(mText, 0, mText.length(), mBounds);
        return mBounds;
    }

    public float getMeasuredTextWidth() {
        return mTextPaint.measureText(mText);
    }

    // setters
    public void setText(String text) {
        mText = text;
        invalidate();
        requestLayout();
    }

    public void setTextSizeInPixels(int pixels) {
        mTextSize = pixels;
        mTextPaint.setTextSize(mTextSize);
        invalidate();
        requestLayout();
    }

    public void setTopVisible(boolean isVisible) {
        mIsTopVisible = isVisible;
        invalidate();
    }

    public void setAscentVisible(boolean isVisible) {
        mIsAscentVisible = isVisible;
        invalidate();
    }

    public void setBaselineVisible(boolean isVisible) {
        mIsBaselineVisible = isVisible;
        invalidate();
    }

    public void setDescentVisible(boolean isVisible) {
        mIsDescentVisible = isVisible;
        invalidate();
    }

    public void setBottomVisible(boolean isVisible) {
        mIsBottomVisible = isVisible;
        invalidate();
    }

    public void setBoundsVisible(boolean isVisible) {
        mIsBoundsVisible = isVisible;
        invalidate();
    }

    public void setWidthVisible(boolean isVisible) {
        mIsWidthVisible = isVisible;
        invalidate();
    }

}
