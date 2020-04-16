package com.github.switefaster.nederlod.util.physics;

import com.github.switefaster.nederlod.util.Util;
import com.github.switefaster.nederlod.util.math.Quaternion;
import com.github.switefaster.nederlod.util.math.matrix.Matrix3f;
import com.github.switefaster.nederlod.util.math.vector.Vec3f;
import com.github.switefaster.nederlod.util.physics.collision.Collision;

public class RigidBody {
    private final Collision shape;
    private final Quaternion pose;
    private final Matrix3f rotation;
    private final Vec3f velocity;
    private final Vec3f angularVelocity;
    private final Vec3f appliedForce;
    private final Vec3f appliedTorque;
    private float mass;
    private float invMass;
    private Vec3f centroid;
    private Vec3f invInertiaLocal;
    private Matrix3f invInertiaWorld;

    public RigidBody(float mass, Vec3f centroid, Collision shape) {
        this.mass = mass;
        this.invMass = Util.zeroOrInv(mass);
        this.centroid = centroid.copy();
        this.shape = shape;
        this.pose = Quaternion.ONE.copy();
        this.rotation = new Matrix3f(pose);
        updateInvInertiaLocal();
        this.velocity = Vec3f.ZERO.copy();
        this.angularVelocity = Vec3f.ZERO.copy();
        this.appliedForce = Vec3f.ZERO.copy();
        this.appliedTorque = Vec3f.ZERO.copy();
    }

    private void updateInvInertiaLocal() {
        Vec3f inertia = shape.calculateLocalInertia(mass);
        this.invInertiaLocal = new Vec3f(Util.zeroOrInv(inertia.getX()), Util.zeroOrInv(inertia.getY()), Util.zeroOrInv(inertia.getZ()));
        updateInvInertiaWorld();
    }

    private void updateInvInertiaWorld() {
        this.invInertiaWorld = rotation.copy().scale(invInertiaLocal).mul(rotation.copy().transpose());
    }

    private void integrateVelocity(float step) {
        velocity.add(appliedForce.copy().mul(step).mul(invMass));
        angularVelocity.add(appliedTorque.copy().mul(invInertiaWorld).mul(step));
    }

    private void integrateTransform(float step) {
        centroid.add(velocity.copy().mul(step));
        float angularSpeed = angularVelocity.length();
        pose.mul(new Quaternion(angularVelocity, angularSpeed * step)).normalize();
        rotation.fromQuaternion(pose);
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
        this.invMass = Util.zeroOrInv(mass);
        updateInvInertiaLocal();
    }

    public Vec3f getCentroid() {
        return centroid.copy();
    }

    public void setCentroid(Vec3f centroid) {
        this.centroid = centroid.copy();
    }

    public Quaternion getPose() {
        return pose.copy();
    }

    public Vec3f getVelocity() {
        return velocity.copy();
    }

    public void tick(float dt) {
        for (int i = 0; i < 10; i++) {
            integrateTransform(dt * 0.1f);
            updateInvInertiaWorld();
            integrateVelocity(dt * 0.1f);
        }
        this.appliedForce.mul(0);
        this.appliedTorque.mul(0);
    }

    public void force(Vec3f force, Vec3f pos) {
        this.appliedForce.add(force);
        this.appliedTorque.add(pos.copy().subtract(centroid).cross(force));
    }

    public void force(Vec3f force) {
        this.appliedForce.add(force);
    }

    public void torque(Vec3f torque) {
        this.appliedTorque.add(torque);
    }
}
