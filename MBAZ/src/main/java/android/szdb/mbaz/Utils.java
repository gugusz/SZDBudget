package android.szdb.mbaz;

import android.graphics.Color;

/**
 * Created by Adrian Zyzda on 2014-07-01 Adrian.
 */
public class Utils {
    public static int darkenColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f;
        return Color.HSVToColor(hsv);
    }
}
