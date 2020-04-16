package com.github.switefaster.nederlod.util.math.vector;

import java.util.Objects;

public class Vec2f extends AbstractVectorF<Vec2f> {
    private float x;
    private float y;

    public Vec2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vec2f(Vec2f another) {
        this.x = another.x;
        this.y = another.y;
    }

    @Override
    public Vec2f add(Vec2f another) {
        this.x += another.x;
        this.y += another.y;
        return this;
    }

    @Override
    public float dot(Vec2f another) {
        return this.x * another.x + this.y * another.y;
    }

    @Override
    public Vec2f mul(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    @Override
    public Vec2f copy() {
        return new Vec2f(this);
    }

    @Override
    protected Vec2f getThis() {
        return this;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec2f vec2f = (Vec2f) o;
        return Float.compare(vec2f.x, x) == 0 &&
                Float.compare(vec2f.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
