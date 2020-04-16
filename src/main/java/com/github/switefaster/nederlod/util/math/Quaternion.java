package com.github.switefaster.nederlod.util.math;

import com.github.switefaster.nederlod.util.math.vector.Vec3f;
import net.minecraft.util.math.MathHelper;

import java.util.Objects;

public class Quaternion {
    public static final Quaternion ONE = new Quaternion(0.0f, 0.0f, 0.0f, 1.0f);

    private float x;
    private float y;
    private float z;
    private float w;

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaternion(Vec3f vector) {
        this.x = vector.getX();
        this.y = vector.getY();
        this.z = vector.getZ();
        this.w = 0.0f;
    }

    public Quaternion(Vec3f axis, float angle) {
        Vec3f rot = axis.copy().normalize().mul(MathHelper.sin(angle * 0.5f));
        this.x = rot.getX();
        this.y = rot.getY();
        this.z = rot.getZ();
        this.w = MathHelper.cos(angle * 0.5f);
    }

    public Quaternion(Quaternion another) {
        this.x = another.x;
        this.y = another.y;
        this.z = another.z;
        this.w = another.w;
    }

    public Quaternion mul(Quaternion another) {
        this.x = this.x * another.w - this.y * another.z + this.z * another.y + this.w * another.x;
        this.y = this.x * another.z + this.y * another.w - this.z * another.x + this.w * another.y;
        this.z = -this.x * another.y + this.y * another.x + this.z * another.w + this.w * another.z;
        this.w = -this.x * another.x - this.y * another.y - this.z * another.z + this.w * another.w;
        return this;
    }

    public Quaternion mul(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        this.w *= scalar;
        return this;
    }

    public Quaternion add(Quaternion adder) {
        this.x += adder.x;
        this.y += adder.y;
        this.z += adder.z;
        this.w += adder.w;
        return this;
    }

    public Quaternion conjugate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    public Quaternion normalize() {
        float invLength = MathHelper.fastInvSqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
        this.x *= invLength;
        this.y *= invLength;
        this.z *= invLength;
        this.w *= invLength;
        return this;
    }

    public Quaternion copy() {
        return new Quaternion(this);
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
        Quaternion that = (Quaternion) o;
        return Float.compare(that.x, x) == 0 &&
                Float.compare(that.y, y) == 0 &&
                Float.compare(that.z, z) == 0 &&
                Float.compare(that.w, w) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }
}
