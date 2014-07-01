package android.szdb.mbaz;

import android.graphics.Path;
import android.graphics.Region;

/**
 * Klasa Kawalka wykresu
 * @version 1.0
 */
public class PieSlice {
    private final Path mPath = new Path();
    private final Region mRegion = new Region();
    private int mColor = 0xFF33B5E5;
    private int mSelectedColor = -1;
    private float mValue;
    private String mTitle;

    /**
     * Getter
     * @return nazwa kawalka
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Setter
     * @param title nazwa kawalka
     */
    public void setTitle(String title) {
        mTitle = title;
    }

    /**
     * Getter
     * @return kolor kawalka
     */
    public int getColor() {
        return mColor;
    }

    /**
     * Setter
     * @param color kolor kawalka
     */
    public void setColor(int color) {
        mColor = color;
    }

    /**
     * Getter
     * @return kolor kawalka kliknietego
     */
    public int getSelectedColor() {
        if (-1 == mSelectedColor) mSelectedColor = Utils.darkenColor(mColor);
        return mSelectedColor;
    }

    /**
     * Setter
     * @param selectedColor kolor kawalka kliknietego
     */
    public void setSelectedColor(int selectedColor) {
        mSelectedColor = selectedColor;
    }

    /**
     * Getter
     * @return wartosc kawalka
     */
    public float getValue() {
        return mValue;
    }

    /**
     * Setter
     * @param value wartosc kawalka
     */
    public void setValue(float value) {
        mValue = value;
    }

    /**
     * Getter
     * @return Path
     */
    public Path getPath() {
        return mPath;
    }

    /**
     * Setter
     * @return Path
     */
    public Region getRegion() {
        return mRegion;
    }
}
