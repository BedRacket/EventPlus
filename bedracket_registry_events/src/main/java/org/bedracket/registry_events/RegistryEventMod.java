package org.bedracket.registry_events;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;

public class RegistryEventMod implements ModInitializer {

    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Registry Events Module...");
    }
}
