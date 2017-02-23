package com.zyzsoft.homebrew.data;

import android.util.Pair;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class SrmColours {

    public static HashMap<Integer, Integer> ColourThresholds;

    static {
        ColourThresholds = new LinkedHashMap<Integer, Integer>();

        ColourThresholds.put(2, 0xFFF8F753);
        ColourThresholds.put(3, 0xFFF6F513);
        ColourThresholds.put(4, 0xFFECE61A);
        ColourThresholds.put(6, 0xFFD5BC26);
        ColourThresholds.put(8, 0xFFBF923B);
        ColourThresholds.put(10, 0xFFBF813A);
        ColourThresholds.put(13, 0xFFBC6733);
        ColourThresholds.put(17, 0xFF8D4C32);
        ColourThresholds.put(20, 0xFF5D341A);
        ColourThresholds.put(24, 0xFF261716);
        ColourThresholds.put(29, 0xFF0F0B0A);
        ColourThresholds.put(35, 0xFF080707);
        ColourThresholds.put(40, 0xFF030403);
    }
}
