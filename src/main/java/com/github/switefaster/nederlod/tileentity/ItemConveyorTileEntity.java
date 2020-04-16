package com.github.switefaster.nederlod.tileentity;

import com.github.switefaster.nederlod.tileentity.conveyor.ConveyorBelt;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemConveyorTileEntity extends NBTSyncTileEntity implements ITickableTileEntity {
    private LazyOptional<IItemHandler> itemHandler = LazyOptional.empty();
    private LazyOptional<BlockPos> commander = LazyOptional.empty();
    private ConveyorBelt belt;
    private boolean dirty;

    public ItemConveyorTileEntity() {
        super(NederlodTileEntities.item_conveyor);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(cap) && side != Direction.DOWN) {
            return itemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void read(@Nonnull CompoundNBT compound) {
        super.read(compound);
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        return super.write(compound);
    }

    @Override
    public void tick() {
        if (dirty) {
            belt.tick();
            this.notifyClient();
            dirty = false;
        }
    }

    public boolean isCommander() {
        return this.pos.equals(commander.orElse(BlockPos.ZERO.down(1)));
    }

    @Override
    public boolean hasFastRenderer() {
        return isCommander();
    }

    public void needUpdate() {
        this.dirty = isCommander();
    }
}
