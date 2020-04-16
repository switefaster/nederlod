package com.github.switefaster.nederlod.util.physics.collision;

import com.github.switefaster.nederlod.util.math.Quaternion;
import com.github.switefaster.nederlod.util.math.matrix.Matrix3f;
import com.github.switefaster.nederlod.util.math.vector.Vec3f;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BoxCollision implements Collision {
    private final Vec3f shape;

    public BoxCollision(float x, float y, float z) {
        this.shape = new Vec3f(x, y, z);
    }

    public BoxCollision(Vec3f shape) {
        this.shape = shape.copy();
    }

    @Override
    public Vec3f calculateLocalInertia(float mass) {
        float x = shape.getX();
        float y = shape.getY();
        float z = shape.getZ();
        float twelfth = mass / 12.0f;
        return new Vec3f(y * y + z * z, x * x + z * z, x * x + y * y).mul(twelfth);
    }

    public static class OrientedPositionedBox {
        public Vec3f centroid;
        public Vec3f shape;
        public Quaternion pose;

        public OrientedPositionedBox(Vec3f centroid, Vec3f shape, Quaternion pose) {
            this.centroid = centroid.copy();
            this.shape = shape.copy();
            this.pose = pose.copy();
        }

        /**
         * Detect whether two OBB is collided.
         *
         * @param another another OBB to detect
         * @return the list of vertices of <strong>this</strong> OBB which is inside another's
         */
        public List<Vec3f> collideWith(OrientedPositionedBox another) {
            Matrix3f transform = new Matrix3f(pose);
            Matrix3f anotherTransform = new Matrix3f(another.pose).transpose();
            Vec3f extent = shape.copy().mul(0.5f).mul(transform);
            Vec3f displacement = this.centroid.copy().subtract(another.centroid);
            //TODO: check
            return Stream.of(
                    extent.copy().mul(transform).add(displacement),
                    new Vec3f(-extent.getX(), extent.getY(), extent.getZ()).mul(transform).add(displacement).mul(anotherTransform),
                    new Vec3f(-extent.getX(), -extent.getY(), extent.getZ()).mul(transform).add(displacement).mul(anotherTransform),
                    new Vec3f(-extent.getX(), -extent.getY(), -extent.getZ()).mul(transform).add(displacement).mul(anotherTransform),
                    new Vec3f(extent.getX(), -extent.getY(), extent.getZ()).mul(transform).add(displacement).mul(anotherTransform),
                    new Vec3f(extent.getX(), -extent.getY(), -extent.getZ()).mul(transform).add(displacement).mul(anotherTransform),
                    new Vec3f(-extent.getX(), extent.getY(), -extent.getZ()).mul(transform).add(displacement).mul(anotherTransform),
                    new Vec3f(extent.getX(), extent.getY(), -extent.getZ()).mul(transform).add(displacement).mul(anotherTransform)
            ).filter(vertex -> true).collect(Collectors.toList());
        }
    }
}
