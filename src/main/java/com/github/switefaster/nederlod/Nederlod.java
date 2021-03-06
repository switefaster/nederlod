package com.github.switefaster.nederlod;

import com.github.switefaster.nederlod.entity.NederlodEntities;
import com.github.switefaster.nederlod.tileentity.NederlodTileEntities;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Nederlod.MOD_ID)
public class Nederlod {
    public static final String MOD_ID = "nederlod";

    public Nederlod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    }

    private void setupClient(final FMLClientSetupEvent event) {
        NederlodTileEntities.registerRenderers();
        NederlodEntities.registerRenderers();
    }
}
