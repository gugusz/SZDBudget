package android.szdb.mbaz;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Metoda tworzaca wykres kolowy
 * @version 1.0
 */
public class PieGraph extends View {

    private int mPadding;
    private int mInnerCircleRatio;
    private ArrayList<PieSlice> mSlices = new ArrayList<PieSlice>();
    private Paint mPaint = new Paint();
    private int mSelectedIndex = -1;
    private OnSliceClickedListener mListener;
    private boolean mDrawCompleted = false;
    private RectF mRectF = new RectF();

    /**
     * Konstruktor
     * @param context kontekst
     */
    public PieGraph(Context context) {
        super(context);
    }

    /**
     * Konstruktor
     * @param context kontekst
     * @param attrs zestaw atrybutow
     */
    public PieGraph(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Konstruktor
     * @param context kontekst
     * @param attrs zestaw atrybutow
     * @param defStyle style
     */
    public PieGraph(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PieGraph, 0, 0);
        mInnerCircleRatio = a.getInt(R.styleable.PieGraph_pieInnerCircleRatio, 0);
        mPadding = a.getDimensionPixelSize(R.styleable.PieGraph_pieSlicePadding, 0);
    }

    /**
     * Metoda rysujaca wykres
     * @param canvas kontrolka plotno na ktorej bedzie rysowany wykres
     */
    public void onDraw(Canvas canvas) {
        float midX, midY, radius, innerRadius;

        canvas.drawColor(Color.TRANSPARENT);
        mPaint.reset();
        mPaint.setAntiAlias(true);

        float currentAngle = 270;
        float currentSweep = 0;
        int totalValue = 0;

        midX = getWidth() / 2;
        midY = getHeight() / 2;
        if (midX < midY) {
            radius = midX;
        } else {
            radius = midY;
        }
        radius -= mPadding;
        innerRadius = radius * mInnerCircleRatio / 255;

        for (PieSlice slice : mSlices) {
            totalValue += slice.getValue();
        }

        int count = 0;
        for (PieSlice slice : mSlices) {
            Path p = slice.getPath();
            p.reset();

            if (mSelectedIndex == count && mListener != null) {
                mPaint.setColor(slice.getSelectedColor());
            } else {
                mPaint.setColor(slice.getColor());
            }
            currentSweep = (slice.getValue() / totalValue) * (360);
            mRectF.set(midX - radius, midY - radius, midX + radius, midY + radius);
            p.arcTo(mRectF,
                    currentAngle + mPadding, currentSweep - mPadding);
            mRectF.set(midX - innerRadius, midY - innerRadius,
                    midX + innerRadius, midY + innerRadius);
            p.arcTo(mRectF,
                    (currentAngle + mPadding) + (currentSweep - mPadding),
                    -(currentSweep - mPadding));
            p.close();

            // Create selection region
            Region r = slice.getRegion();
            r.set((int) (midX - radius),
                    (int) (midY - radius),
                    (int) (midX + radius),
                    (int) (midY + radius));
            canvas.drawPath(p, mPaint);
            currentAngle = currentAngle + currentSweep;

            count++;
        }
        mDrawCompleted = true;
    }

    /**
     * Nadpisana event dotkniecia wykresu
     * @param event event
     * @return wartosc boolowska
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDrawCompleted) {
            Point point = new Point();
            point.x = (int) event.getX();
            point.y = (int) event.getY();

            int count = 0;
            Region r = new Region();
            for (PieSlice slice : mSlices) {
                r.setPath(slice.getPath(), slice.getRegion());
                switch (event.getAction()) {
                    default:
                        break;
                    case MotionEvent.ACTION_DOWN:
                        if (r.contains(point.x, point.y)) {
                            mSelectedIndex = count;
                            postInvalidate();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (count == mSelectedIndex
                                && mListener != null
                                && r.contains(point.x, point.y)) {
                            mListener.onClick(mSelectedIndex);
                        }
                        break;
                }
                count++;
            }
        }
        // Reset selection
        if (MotionEvent.ACTION_UP == event.getAction()
                || MotionEvent.ACTION_CANCEL == event.getAction()) {
            mSelectedIndex = -1;
            postInvalidate();
        }
        return true;
    }

    /**
     * Setter odstepu pomiedzy kawalkami wykresu
     * @param padding warosc odstepu
     */
    public void setPadding(int padding) {
        mPadding = padding;
        postInvalidate();
    }

    /**
     * Setter wielkosci dzury w wykresie
     * @param innerCircleRatio promien dziury
     */
    public void setInnerCircleRatio(int innerCircleRatio) {
        mInnerCircleRatio = innerCircleRatio;
        postInvalidate();
    }

    /**
     * getter
     * @return lista kawalkow wykresu
     */
    public ArrayList<PieSlice> getSlices() {
        return mSlices;
    }

    /**
     * Setter kawalkow wykresu
     * @param slices kawalek wykresu
     */
    public void setSlices(ArrayList<PieSlice> slices) {
        mSlices = slices;
        postInvalidate();
    }

    /**
     * Getter kawalka wykresu
     * @param index numer kawalka
     * @return kawalek wykresu
     */
    public PieSlice getSlice(int index) {
        return mSlices.get(index);
    }

    /**
     * Metoda dodajaca kawalek wykreu do wykresu
     * @param slice
     */
    public void addSlice(PieSlice slice) {
        mSlices.add(slice);
        postInvalidate();
    }

    /**
     * Metoda ustawiajaca OnSliceClickedListener
     * @param listener listerner
     */
    public void setOnSliceClickedListener(OnSliceClickedListener listener) {
        mListener = listener;
    }

    /**
     * Metoda usuwajaca wszystkie kawalki wykresu z wykresu
     */
    public void removeSlices() {
        mSlices.clear();
        postInvalidate();
    }

    /**
     * Interfejs OnSliceClickedListenera
     */
    public interface OnSliceClickedListener {
        public abstract void onClick(int index);
    }
}