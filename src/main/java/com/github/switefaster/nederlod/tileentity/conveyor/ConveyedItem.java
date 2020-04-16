package com.github.switefaster.nederlod.tileentity.conveyor;

import com.github.switefaster.nederlod.util.math.vector.Vec3f;
import com.github.switefaster.nederlod.util.physics.RigidBody;
import com.github.switefaster.nederlod.util.physics.collision.BoxCollision;
import net.minecraft.item.ItemStack;

public class ConveyedItem extends RigidBody {
    /**
     * Mass for ItemStack with size 1, kg as unit.
     */
    public static final float MASS_PER_ITEM = 0.05f;

    private final ItemStack itemStack;

    public ConveyedItem(ItemStack itemStack, Vec3f centroid) {
        super(MASS_PER_ITEM * itemStack.getCount(), centroid, new BoxCollision(0.6f, 0.6f, 0.6f));
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public void tick(float dt) {
        this.setMass(MASS_PER_ITEM * itemStack.getCount());
        super.tick(dt);
    }
}
