package com.github.switefaster.nederlod.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import static com.github.switefaster.nederlod.util.Util.nonnull;

public class NederlodCapabilities {

    @CapabilityInject(SyncProgressive.class)
    public static final Capability<SyncProgressive> SYNC_PROGRESSIVE = nonnull();

    public static void registerCapabilities() {
        CapabilityManager.INSTANCE.register(SyncProgressive.class, new CapabilitySyncProgressive.Storage(), CapabilitySyncProgressive.Implementation::new);
    }

}
