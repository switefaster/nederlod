package com.github.switefaster.nederlod.util.math.vector;

import net.minecraft.util.math.MathHelper;

public abstract class AbstractVectorF<E extends AbstractVectorF<E>> {
    public abstract E add(E another);

    public abstract float dot(E another);

    public abstract E mul(float scalar);

    public abstract E copy();

    protected abstract E getThis();

    public E negate() {
        return this.mul(-1);
    }

    public E getNegate() {
        return this.copy().negate();
    }

    public E subtract(E another) {
        return this.add(another.getNegate());
    }

    public float lengthSq() {
        return this.dot(getThis());
    }

    public float length() {
        return (float) Math.sqrt(lengthSq());
    }

    public E normalize() {
        float lengthSq = this.lengthSq();
        if (lengthSq == 0) {
            throw new IllegalStateException("Can't normalize zero-vector!");
        } else if (lengthSq == 1) {
            return getThis();
        }
        return this.mul(MathHelper.fastInvSqrt(lengthSq));
    }
}
