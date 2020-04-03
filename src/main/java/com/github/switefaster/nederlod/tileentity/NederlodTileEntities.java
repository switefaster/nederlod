package com.github.switefaster.nederlod.tileentity;

import com.github.switefaster.nederlod.Nederlod;
import com.github.switefaster.nederlod.block.NederlodBlocks;
import com.github.switefaster.nederlod.client.renderer.tileentity.ItemConveyorTESR;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import static com.github.switefaster.nederlod.util.Util.nonnull;

@ObjectHolder(Nederlod.MOD_ID)
public class NederlodTileEntities {

    public static final TileEntityType<ItemConveyorTileEntity> item_conveyor = nonnull();

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderers() {
        ClientRegistry.bindTileEntityRenderer(item_conveyor, ItemConveyorTESR::new);
    }

    @Mod.EventBusSubscriber(modid = Nederlod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryListener {
        @SubscribeEvent
        public static void registerEntity(final RegistryEvent.Register<TileEntityType<?>> event) {
            event.getRegistry().register(TileEntityType.Builder.create(ItemConveyorTileEntity::new, NederlodBlocks.item_conveyor).build(nonnull()).setRegistryName(Nederlod.MOD_ID, "item_conveyor"));
        }
    }

}
