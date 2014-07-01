package android.szdb.mbaz;

import android.graphics.Color;

/**
 * Klasa poocnicza do zarzadania kolorami
 * @version 1.0
 */
public class Utils {
    /**
     * Statyczna meotda przyciemnajaca kolor
     * @param color
     * @return przyciemniony kolor
     */
    public static int darkenColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f;
        return Color.HSVToColor(hsv);
    }
}
