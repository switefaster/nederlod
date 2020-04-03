package com.github.switefaster.nederlod.entity;

import com.github.switefaster.nederlod.Nederlod;
import com.github.switefaster.nederlod.client.renderer.entity.GirlfriendRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import static com.github.switefaster.nederlod.util.Util.nonnull;

@ObjectHolder(Nederlod.MOD_ID)
public class NederlodEntities {

    public static final EntityType<GirlfriendEntity> girlfriend = nonnull();

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(NederlodEntities.girlfriend, GirlfriendRenderer::new);
    }

    @Mod.EventBusSubscriber(modid = Nederlod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryListener {
        @SubscribeEvent
        public static void registerEntity(final RegistryEvent.Register<EntityType<?>> event) {
            event.getRegistry().register(EntityType.Builder.create(GirlfriendEntity::new, EntityClassification.MISC).size(1.0f, 1.0f).build(Nederlod.MOD_ID + ":girlfriend").setRegistryName(Nederlod.MOD_ID, "girlfriend"));
        }
    }

}
