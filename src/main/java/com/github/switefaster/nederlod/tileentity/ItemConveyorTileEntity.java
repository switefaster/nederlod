package com.github.switefaster.nederlod.tileentity;

import com.github.switefaster.nederlod.capability.CapabilitySyncProgressive;
import com.github.switefaster.nederlod.capability.NederlodCapabilities;
import com.github.switefaster.nederlod.capability.SyncProgressive;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemConveyorTileEntity extends NBTSyncTileEntity implements ITickableTileEntity {
    private SyncProgressive conveyedItems = new CapabilitySyncProgressive.Implementation();

    public ItemConveyorTileEntity() {
        super(NederlodTileEntities.item_conveyor);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (NederlodCapabilities.SYNC_PROGRESSIVE.equals(cap)) {
            return LazyOptional.of(() -> conveyedItems).cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void read(CompoundNBT compound) {
        conveyedItems.clear();
        NederlodCapabilities.SYNC_PROGRESSIVE.getStorage().readNBT(NederlodCapabilities.SYNC_PROGRESSIVE, conveyedItems, null, compound.getList("conveyed", Constants.NBT.TAG_COMPOUND));
        super.read(compound);
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        INBT list = NederlodCapabilities.SYNC_PROGRESSIVE.getStorage().writeNBT(NederlodCapabilities.SYNC_PROGRESSIVE, conveyedItems, null);
        if (list != null) {
            compound.put("conveyed", list);
        }
        return super.write(compound);
    }

    @Override
    public void tick() {
        if (world != null && !world.isRemote) {
            conveyedItems.progressAll(1);
            notifyClient();
        }
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }
}
