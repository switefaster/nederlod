package com.github.switefaster.nederlod.util.math.vector;

import com.github.switefaster.nederlod.util.math.matrix.Matrix3f;

import java.util.Objects;

public class Vec3f extends AbstractVectorF<Vec3f> {
    public static final Vec3f ZERO = new Vec3f(0.0f, 0.0f, 0.0f);

    private float x;
    private float y;
    private float z;

    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3f(Vec3f another) {
        this.x = another.x;
        this.y = another.y;
        this.z = another.z;
    }

    @Override
    public Vec3f add(Vec3f another) {
        this.x += another.x;
        this.y += another.y;
        this.z += another.z;
        return this;
    }

    @Override
    public float dot(Vec3f another) {
        return this.x * another.x + this.y * another.y + this.z * another.z;
    }

    @Override
    public Vec3f mul(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        return this;
    }

    @Override
    public Vec3f copy() {
        return new Vec3f(this);
    }

    @Override
    protected Vec3f getThis() {
        return this;
    }

    public Vec3f cross(Vec3f another) {
        Vec3f temp = this.copy();
        this.x = temp.y * another.z - another.y * temp.z;
        this.y = temp.z * another.x - another.z * temp.x;
        this.z = temp.x * another.y - another.x * temp.y;
        return this;
    }

    public Vec3f mul(Matrix3f matrix) {
        Vec3f temp = this.copy();
        this.x = temp.dot(matrix.getColumn(0));
        this.y = temp.dot(matrix.getColumn(1));
        this.z = temp.dot(matrix.getColumn(2));
        return this;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec3f vec3f = (Vec3f) o;
        return Float.compare(vec3f.x, x) == 0 &&
                Float.compare(vec3f.y, y) == 0 &&
                Float.compare(vec3f.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
