package com.spud.sgsclasscountdownapp;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stephen Ogden on 10/26/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
public enum BlockNames {

    A("A block"),
    B("B block"),
    C("C block"),
    D("D block"),
    E("E block"),
    F("F block"),
    G("G block"),
    H("H block"),
    Lunch("Lunch"),
    NoBlock("No class right now");

    private String value;

    BlockNames(String value) {
        this.value = value;
    }

    public String getName() {
        Log.i("BlockNames", value);
        return value;
    }

    // https://stackoverflow.com/questions/31271061/not-a-constant-in-enum
    private static final Map<String, BlockNames> map = new HashMap<>();
    static {
        for (BlockNames en : values()) {
            map.put(en.value, en);
        }
    }

    public static BlockNames valueFor(String name) {
        return map.get(name);
    }
}
