package com.example.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.R;

public class BesselView extends View {

    private float mCircleRadius;

    private float currentRadius;

    private int mMaxLength;

    private int mColor;

    private Context mContext;

    private int mNumber = 0;

    private int mNumberColor;

    private int mNumberSize;

    private int mCenter;

    private Paint mPaint;

    private Rect textRect;

    private float minPercent = 0.2f;

    private float percent = 1;

    private float mCurrentX = 0;

    private float mCurrentY = 0;

    private float mMoveCircleX;

    private float mMoveCircleY;

    private boolean isMax = true;

    private boolean isInside = false;

    private boolean isDisPlay = true;

    private Path mPath;

    private float sin;

    private float tan;

    private double angle;

    private float cos;

    private int quadrant = 0;

    public BesselView(Context context) {
        this(context, null);
    }

    public BesselView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BesselView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        TypedArray ta = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.BesselView, defStyleAttr, 0);

        mColor = ta.getColor(R.styleable.BesselView_circleColor, Color.parseColor("#F74C31"));
        mMaxLength = ta.getDimensionPixelSize(R.styleable.BesselView_maxLength, dip2px(mContext, 80));
        mCircleRadius = ta.getDimensionPixelSize(R.styleable.BesselView_circleRadius, dip2px(mContext, 10));
        mNumber = ta.getInt(R.styleable.BesselView_number, 0);
        mNumberColor = ta.getColor(R.styleable.BesselView_numberColor, Color.WHITE);
        mNumberSize = ta.getDimensionPixelSize(R.styleable.BesselView_numberSize, sp2px(mContext, 14));
        minPercent = ta.getFloat(R.styleable.BesselView_minPercent, 0.2f);

        ta.recycle();

        mPaint = new Paint();
        mPath = new Path();
        textRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCenter = getWidth() / 2;

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(mColor);

        if (isDisPlay) {

            // canvas.drawCircle(mCircleWidth, mCircleWidth, percent *
            // mCircleRadius, mPaint);
            //
            // mPath = new Path();
            // mPath.moveTo(mCircleWidth - percent * mCircleRadius,
            // mCircleWidth);
            // mPath.quadTo(mCircleWidth, mCircleWidth + mMoveCircleY / 2,
            // mCircleWidth - mCircleRadius, mCircleWidth + mMoveCircleY);
            //
            // mPath.lineTo(mCircleWidth + mCircleRadius, mCircleWidth +
            // mMoveCircleY);
            // mPath.quadTo(mCircleWidth, mCircleWidth + mMoveCircleY / 2,
            // mCircleWidth + percent * mCircleRadius, mCircleWidth);
            // mPath.close();
            // canvas.drawPath(mPath, mPaint);

            currentRadius = percent * mCircleRadius;
            // 绘制移动圆
            canvas.drawCircle(mCenter + mMoveCircleX, mCenter + mMoveCircleY, mCircleRadius, mPaint);
            if (!isInside) {
                if (isMax) {
                    // 绘制定圆
                    canvas.drawCircle(mCenter, mCenter, percent * mCircleRadius, mPaint);
                    switch (quadrant) {
                        case 1: {
                            mPath = new Path();
                            mPath.moveTo(mCenter + currentRadius * sin, mCenter + currentRadius * cos);
                            mPath.quadTo(mCenter + mMoveCircleX / 2, mCenter + mMoveCircleY / 2,
                                    mCurrentX + mCircleRadius * sin, mCurrentY + mCircleRadius * cos);
                            mPath.lineTo(mCurrentX - mCircleRadius * sin, mCurrentY - mCircleRadius * cos);
                            mPath.quadTo(mCenter + mMoveCircleX / 2, mCenter + mMoveCircleY / 2,
                                    mCenter - currentRadius * sin, mCenter - currentRadius * cos);
                            mPath.close();
                            canvas.drawPath(mPath, mPaint);
                            break;
                        }
                        case 2: {
                            mPath = new Path();
                            mPath.moveTo(mCenter + currentRadius * sin, mCenter - currentRadius * cos);
                            mPath.quadTo(mCenter + mMoveCircleX / 2, mCenter + mMoveCircleY / 2,
                                    mCurrentX + mCircleRadius * sin, mCurrentY - mCircleRadius * cos);
                            mPath.lineTo(mCurrentX - mCircleRadius * sin, mCurrentY + mCircleRadius * cos);
                            mPath.quadTo(mCenter + mMoveCircleX / 2, mCenter + mMoveCircleY / 2,
                                    mCenter - currentRadius * sin, mCenter + currentRadius * cos);
                            mPath.close();
                            canvas.drawPath(mPath, mPaint);
                            break;
                        }
                        case 3: {
                            mPath = new Path();
                            mPath.moveTo(mCenter - currentRadius * sin, mCenter - currentRadius * cos);
                            mPath.quadTo(mCenter + mMoveCircleX / 2, mCenter + mMoveCircleY / 2,
                                    mCurrentX - mCircleRadius * sin, mCurrentY - mCircleRadius * cos);
                            mPath.lineTo(mCurrentX + mCircleRadius * sin, mCurrentY + mCircleRadius * cos);
                            mPath.quadTo(mCenter + mMoveCircleX / 2, mCenter + mMoveCircleY / 2,
                                    mCenter + currentRadius * sin, mCenter + currentRadius * cos);
                            mPath.close();
                            canvas.drawPath(mPath, mPaint);
                            break;
                        }
                        case 4: {
                            mPath = new Path();
                            mPath.moveTo(mCenter - currentRadius * sin, mCenter + currentRadius * cos);
                            mPath.quadTo(mCenter + mMoveCircleX / 2, mCenter + mMoveCircleY / 2,
                                    mCurrentX - mCircleRadius * sin, mCurrentY + mCircleRadius * cos);
                            mPath.lineTo(mCurrentX + mCircleRadius * sin, mCurrentY - mCircleRadius * cos);
                            mPath.quadTo(mCenter + mMoveCircleX / 2, mCenter + mMoveCircleY / 2,
                                    mCenter + currentRadius * sin, mCenter - currentRadius * cos);
                            mPath.close();
                            canvas.drawPath(mPath, mPaint);
                            break;
                        }
                        default:
                            break;
                    }
                }
            }
            mPaint.setColor(Color.WHITE);
            if (mNumber > 0) {
                textRect.left = (int) (mCenter + mMoveCircleX - mCircleRadius / (float) Math.sqrt(2));
                textRect.top = (int) (mCenter + mMoveCircleY - mCircleRadius / (float) Math.sqrt(2));
                textRect.right = (int) (mCenter + mMoveCircleX + mCircleRadius / (float) Math.sqrt(2));
                textRect.bottom = (int) (mCenter + mMoveCircleY + mCircleRadius / (float) Math.sqrt(2));
                mPaint.setColor(Color.TRANSPARENT);
                canvas.drawRect(textRect, mPaint);

                mPaint.setColor(mNumberColor);
                mPaint.setTextSize(mNumberSize);
                // 该方法即为设置基线上那个点究竟是left,center,还是right 这里设置为center
                mPaint.setTextAlign(Paint.Align.CENTER);

                Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
                float top = fontMetrics.top;// 为基线到字体上边框的距离,即上图中的top
                float bottom = fontMetrics.bottom;// 为基线到字体下边框的距离,即上图中的bottom

                int baseLineY = (int) (textRect.centerY() - top / 2 - bottom / 2);// 基线中间点的y轴计算公式

                canvas.drawText(mNumber + "", textRect.centerX(), baseLineY, mPaint);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                mCurrentX = event.getX();
                mCurrentY = event.getY();
                mMoveCircleX = event.getX() - mCenter;
                mMoveCircleY = event.getY() - mCenter;

                if (mMoveCircleX > 0 && mMoveCircleY < 0) {
                    quadrant = 1;
                } else if (mMoveCircleX < 0 && mMoveCircleY < 0) {
                    quadrant = 2;
                } else if (mMoveCircleX < 0 && mMoveCircleY > 0) {
                    quadrant = 3;
                } else if (mMoveCircleX > 0 && mMoveCircleY > 0) {
                    quadrant = 4;
                }

                tan = Math.abs(mMoveCircleY / mMoveCircleX);
                angle = Math.atan(tan) * 180 / Math.PI;
                sin = (float) Math.sin(angle * Math.PI / 180);
                cos = (float) Math.cos(angle * Math.PI / 180);

                percent = (float) (1 - Math.sqrt(mMoveCircleX * mMoveCircleX + mMoveCircleY * mMoveCircleY) / mMaxLength);
                if (percent < minPercent)
                    isMax = false;
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (percent < minPercent)
                    isInside = true;
                if (isMax) {
                    initViewData();
                } else {
                    if (isInside) {
                        isDisPlay = false;
                    } else {
                        initViewData();
                    }
                }
                postInvalidate();
                break;
        }

        return true;
    }

    public void setReDisplay() {
        this.isDisPlay = true;
        this.isInside = false;
        initViewData();
    }

    public void setCircleColor(int color) {
        this.mColor = color;
    }

    public void setNumberColor(int color) {
        this.mNumberColor = color;
    }

    public void setNumber(int number) {
        this.mNumber = number;
    }

    public void setMinPercent(int percent) {
        this.minPercent = percent;
    }

    private void initViewData() {
        this.percent = 1;
        this.mMoveCircleY = 0;
        this.mMoveCircleX = 0;
        this.quadrant = 0;
        this.isMax = true;
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
