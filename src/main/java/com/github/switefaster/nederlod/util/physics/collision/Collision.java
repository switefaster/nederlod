package com.github.switefaster.nederlod.util.physics.collision;

import com.github.switefaster.nederlod.util.math.vector.Vec3f;

public interface Collision {
    Vec3f calculateLocalInertia(float mass);
}
