package com.github.switefaster.nederlod.block;

import com.github.switefaster.nederlod.Nederlod;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import static com.github.switefaster.nederlod.util.Util.nonnull;

@ObjectHolder(Nederlod.MOD_ID)
public class NederlodBlocks {

    public static final Block item_conveyor = nonnull();

    @Mod.EventBusSubscriber(modid = Nederlod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryListener {
        @SubscribeEvent
        public static void registerBlock(final RegistryEvent.Register<Block> event) {
            event.getRegistry().register(new ItemConveyorBlock().setRegistryName(Nederlod.MOD_ID, "item_conveyor"));
        }

        @SuppressWarnings("ConstantConditions")
        @SubscribeEvent
        public static void registerItem(final RegistryEvent.Register<Item> event) {
            event.getRegistry().register(new BlockItem(item_conveyor, new Item.Properties()).setRegistryName(item_conveyor.getRegistryName()));
        }
    }

}
