package com.github.switefaster.nederlod.util;

import javax.annotation.Nonnull;

public class Util {

    @SuppressWarnings("ConstantConditions")
    @Nonnull
    public static <T> T nonnull() {
        return null;
    }

    public static float zeroOrInv(float number) {
        return number == 0 ? 0 : 1 / number;
    }

}
