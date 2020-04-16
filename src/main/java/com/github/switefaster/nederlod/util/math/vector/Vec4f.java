package com.github.switefaster.nederlod.util.math.vector;

import com.github.switefaster.nederlod.util.math.matrix.Matrix4f;

import java.util.Objects;

public class Vec4f extends AbstractVectorF<Vec4f> {
    private float x;
    private float y;
    private float z;
    private float w;

    public Vec4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vec4f(Vec4f another) {
        this.x = another.x;
        this.y = another.y;
        this.z = another.z;
        this.w = another.w;
    }

    @Override
    public Vec4f add(Vec4f another) {
        this.x += another.x;
        this.y += another.y;
        this.z += another.z;
        this.w += another.w;
        return this;
    }

    @Override
    public float dot(Vec4f another) {
        return this.x * another.x + this.y * another.y + this.z * another.z + this.w * another.w;
    }

    @Override
    public Vec4f mul(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        this.w *= scalar;
        return this;
    }

    @Override
    public Vec4f copy() {
        return new Vec4f(this);
    }

    @Override
    protected Vec4f getThis() {
        return this;
    }

    public Vec4f mul(Matrix4f matrix) {
        //TODO: matrix mul
        return null;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getW() {
        return w;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec4f vec4f = (Vec4f) o;
        return Float.compare(vec4f.x, x) == 0 &&
                Float.compare(vec4f.y, y) == 0 &&
                Float.compare(vec4f.z, z) == 0 &&
                Float.compare(vec4f.w, w) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }
}
