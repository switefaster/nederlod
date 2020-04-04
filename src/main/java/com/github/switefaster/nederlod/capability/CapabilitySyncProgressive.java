package com.github.switefaster.nederlod.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.extensions.IForgeItemStack;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CapabilitySyncProgressive {

    public static class Storage implements Capability.IStorage<SyncProgressive> {

        @Nullable
        @Override
        public INBT writeNBT(Capability<SyncProgressive> capability, SyncProgressive instance, Direction side) {
            Map<Integer, List<ItemStack>> items = instance.getAll();
            ListNBT progress = new ListNBT();
            for (Map.Entry<Integer, List<ItemStack>> entry : items.entrySet()) {
                ListNBT list = new ListNBT();
                list.addAll(entry.getValue().stream().map(IForgeItemStack::serializeNBT).collect(Collectors.toList()));
                CompoundNBT compound = new CompoundNBT();
                compound.putInt("progress", entry.getKey());
                compound.put("items", list);
                progress.add(compound);
            }
            return progress;
        }

        @Override
        public void readNBT(Capability<SyncProgressive> capability, SyncProgressive instance, Direction side, INBT nbt) {
            ((ListNBT) nbt).forEach(comp ->
                    instance.put(
                            ((CompoundNBT) comp).getInt("progress"),
                            ((CompoundNBT) comp).getList("items", Constants.NBT.TAG_COMPOUND)
                                    .stream()
                                    .map(CompoundNBT.class::cast)
                                    .map(ItemStack::read)
                                    .collect(Collectors.toList())
                    )
            );
        }
    }

    public static class Implementation implements SyncProgressive {

        public static final int MAX_PROGRESS = 100;

        private HashMap<Integer, List<ItemStack>> items = new HashMap<>();

        @Override
        public int getMaxProgress() {
            return MAX_PROGRESS;
        }

        @Override
        public void progressAll(int delta) {
            HashMap<Integer, List<ItemStack>> newer = new HashMap<>();
            items.forEach((progress, list) -> newer.computeIfAbsent(Math.min(progress + delta, getMaxProgress()), $ -> new ArrayList<>()).addAll(list));
            items = newer;
        }

        @Nonnull
        @Override
        public List<ItemStack> getProgressComplete() {
            return items.getOrDefault(getMaxProgress(), new ArrayList<>());
        }

        @Nonnull
        @Override
        public List<ItemStack> getProgressZero() {
            return items.getOrDefault(0, new ArrayList<>());
        }

        @Nonnull
        @Override
        public Map<Integer, List<ItemStack>> getAll() {
            return items;
        }

        @Override
        public void removeProgressComplete() {
            items.remove(getMaxProgress());
        }

        @Override
        public void reverse() {
            HashMap<Integer, List<ItemStack>> newer = new HashMap<>();
            items.forEach((progress, list) -> newer.put(getMaxProgress() - progress, list));
            items = newer;
        }

        @Override
        public void put(List<ItemStack> itemStacks) {
            put(0, itemStacks);
        }

        @Override
        public void put(int progress, List<ItemStack> itemStacks) {
            items.computeIfAbsent(progress, $ -> new ArrayList<>()).addAll(itemStacks);
        }

        @Override
        public void clear() {
            items.clear();
        }
    }

}
