package com.github.switefaster.nederlod.util.math.matrix;

public class Matrix4f {
    private float[][] m;

    public Matrix4f(float[][] element) {
        if (element.length != 4 || element[0].length != 4) {
            throw new IllegalArgumentException("Given array must be 4x4!");
        }
        m = element.clone();
    }
}
