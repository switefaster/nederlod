package com.github.switefaster.nederlod.capability;

import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Map;

public interface SyncProgressive {

    int getMaxProgress();

    void progressAll(int delta);

    List<ItemStack> getProgressComplete();

    List<ItemStack> getProgressZero();

    Map<Integer, List<ItemStack>> getAll();

    void removeProgressComplete();

    void reverse();

    void put(List<ItemStack> itemStacks);

    void put(int progress, List<ItemStack> itemStacks);

    void clear();

}
