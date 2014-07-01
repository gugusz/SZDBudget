package android.szdb.mbaz;

import android.graphics.Path;
import android.graphics.Region;

/**
 * Created by Adrian Zyzda on 2014-07-01 Adrian.
 */
public class PieSlice {
    private final Path mPath = new Path();
    private final Region mRegion = new Region();
    private int mColor = 0xFF33B5E5;
    private int mSelectedColor = -1;
    private float mValue;
    private String mTitle;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public int getSelectedColor() {
        if (-1 == mSelectedColor) mSelectedColor = Utils.darkenColor(mColor);
        return mSelectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        mSelectedColor = selectedColor;
    }

    public float getValue() {
        return mValue;
    }

    public void setValue(float value) {
        mValue = value;
    }

    public Path getPath() {
        return mPath;
    }

    public Region getRegion() {
        return mRegion;
    }
}
