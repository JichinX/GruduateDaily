package me.djc.common.util;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.widget.ImageView;
import androidx.palette.graphics.Palette;

import java.util.Random;

/**
 * Des:GruduateDaily - me.djc.common.util
 *
 * @author xujichang
 * @date 2019-04-19 - 06:39
 * <p>
 * modify:
 */
public class ColorUtils {
    private static Random mRandom = new Random();
    private static final ColorUtils ourInstance = new ColorUtils();

    static ColorUtils getInstance() {
        return ourInstance;
    }

    private ColorUtils() {

    }

    /**
     * 生成随机颜色
     *
     * @return
     */
    public static int createRandomColor() {

        int r = mRandom.nextInt(256);
        int g = mRandom.nextInt(256);
        int b = mRandom.nextInt(256);
        int a = 200 + mRandom.nextInt(56);


        return Color.argb(a, r, g, b);
    }

    public static int paletteIvColor(int eColor) {
//        Palette.from(BitmapFactory.)
        return 0;
    }
}
