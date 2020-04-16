package com.github.switefaster.nederlod.util.math.matrix;

import com.github.switefaster.nederlod.util.math.Quaternion;
import com.github.switefaster.nederlod.util.math.vector.Vec3f;

public class Matrix3f {
    public static final Matrix3f IDENTITY = new Matrix3f(new float[][]{
            {1.0f, 0.0f, 0.0f},
            {0.0f, 1.0f, 0.0f},
            {0.0f, 0.0f, 1.0f}
    });

    public static final Matrix3f ZERO = new Matrix3f(new float[][]{
            {0.0f, 0.0f, 0.0f},
            {0.0f, 0.0f, 0.0f},
            {0.0f, 0.0f, 0.0f}
    });

    /**
     * Line, column
     * {
     * {00, 01, 02},
     * {10, 11, 12},
     * {20, 21, 22}
     * }
     */
    private float[][] m;

    public Matrix3f() {
        this(IDENTITY);
    }

    public Matrix3f(float[][] element) {
        if (element.length != 3 || element[0].length != 3) {
            throw new IllegalArgumentException("Given array must be 3x3!");
        }
        m = element.clone();
    }

    public Matrix3f(Matrix3f another) {
        this.m = another.m.clone();
    }

    public Matrix3f(Quaternion quaternion) {
        fromQuaternion(quaternion);
    }

    public Matrix3f fromQuaternion(Quaternion quaternion) {
        float q1 = quaternion.getX();
        float q2 = quaternion.getY();
        float q3 = quaternion.getZ();
        float q4 = quaternion.getW();

        this.m = new float[][]{
                {2 * (q1 * q1 + q4 * q4) - 1, 2 * (q1 * q2 + q3 * q4), 2 * (q1 * q3 - q2 * q4)},
                {2 * (q1 * q2 - q3 * q4), 2 * (q2 * q2 + q4 * q4) - 1, 2 * (q2 * q3 + q1 * q4)},
                {2 * (q1 * q3 + q2 * q4), 2 * (q2 * q3 - q1 * q4), 2 * (q3 * q3 + q4 * q4) - 1}
        };
        return this;
    }

    public Matrix3f mulAssign(Matrix3f another) {
        Matrix3f temp = new Matrix3f(ZERO);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    temp.m[i][j] += this.m[i][k] * another.m[k][j];
                }
            }
        }
        return temp;
    }

    public Matrix3f mul(Matrix3f another) {
        this.m = mulAssign(another).m.clone();
        return this;
    }

    public Matrix3f mul(float scalar) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.m[i][j] *= scalar;
            }
        }
        return this;
    }

    public Matrix3f scale(Vec3f vector) {
        this.m[0][0] *= vector.getX();
        this.m[1][0] *= vector.getY();
        this.m[2][0] *= vector.getZ();
        this.m[0][1] *= vector.getX();
        this.m[1][1] *= vector.getY();
        this.m[2][1] *= vector.getZ();
        this.m[0][2] *= vector.getX();
        this.m[1][2] *= vector.getY();
        this.m[2][2] *= vector.getZ();
        return this;
    }

    public Matrix3f add(Matrix3f adder) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.m[i][j] += adder.m[i][j];
            }
        }
        return this;
    }

    public Matrix3f transpose() {
        Matrix3f temp = new Matrix3f(this);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.m[i][j] = temp.m[j][i];
            }
        }
        return this;
    }

    public float trace() {
        float trace = 0;
        for (int i = 0; i < 3; i++) {
            trace += this.m[i][i];
        }
        return trace;
    }

    public Vec3f getColumn(int column) {
        return new Vec3f(this.m[0][column], this.m[1][column], this.m[2][column]);
    }

    public Vec3f getLine(int line) {
        return new Vec3f(this.m[line][0], this.m[line][1], this.m[line][2]);
    }

    public float get(int line, int column) {
        return m[line][column];
    }

    public Matrix3f copy() {
        return new Matrix3f(this);
    }
}
