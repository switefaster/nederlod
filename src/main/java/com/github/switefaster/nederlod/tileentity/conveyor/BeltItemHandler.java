package com.github.switefaster.nederlod.tileentity.conveyor;

import javafx.util.Pair;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

public class BeltItemHandler implements IItemHandler {
    private final ConveyorBelt belt;
    private final int ordinal;

    public BeltItemHandler(ConveyorBelt belt, int ordinal) {
        this.belt = belt;
        this.ordinal = ordinal;
    }

    @Override
    public int getSlots() {
        return 1;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return belt.getFirstItemOnBelt(ordinal).getValue().getItemStack();
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        int available = belt.findIndexForPosition(ordinal + 0.5f);
        if (ConveyorBelt.getCorrespondingComponentForAxis(belt.getDirection(), belt.getItemAtIndex(available).getCentroid()) - belt.getMinEndPoint() == 0.5f
                || (available - 1 >= 0 && ConveyorBelt.getCorrespondingComponentForAxis(belt.getDirection(), belt.getItemAtIndex(available - 1).getCentroid()) - belt.getMinEndPoint() == 0.5f)) {
            return stack;
        }
        if (!simulate) {
            belt.insertItemAt(new ConveyedItem(stack, belt.getActualPosition(ordinal + 0.5f)), available);
        }
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0) {
            return ItemStack.EMPTY;
        }
        Pair<Integer, ConveyedItem> item = belt.getFirstItemOnBelt(ordinal);
        ItemStack toRemove = simulate ? item.getValue().getItemStack().copy() : item.getValue().getItemStack();
        if (amount >= toRemove.getCount() && !simulate) {
            belt.removeAtIndex(item.getKey());
        } else {
            toRemove.setCount(toRemove.getCount() - amount);
        }
        return toRemove;
    }

    @Override
    public int getSlotLimit(int slot) {
        return Math.min(getStackInSlot(slot).getMaxStackSize(), 64);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return true;
    }
}
