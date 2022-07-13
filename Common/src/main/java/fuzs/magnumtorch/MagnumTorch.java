package fuzs.magnumtorch;

import fuzs.magnumtorch.config.ServerConfig;
import fuzs.magnumtorch.registry.ModRegistry;
import fuzs.puzzleslib.config.AbstractConfig;
import fuzs.puzzleslib.config.ConfigHolder;
import fuzs.puzzleslib.core.CoreServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MagnumTorch {
    public static final String MOD_ID = "magnumtorch";
    public static final String MOD_NAME = "Magnum Torch";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @SuppressWarnings("Convert2MethodRef")
    public static final ConfigHolder<AbstractConfig, ServerConfig> CONFIG = CoreServices.FACTORIES.serverConfig(() -> new ServerConfig());

    public static void onConstructMod() {
        CONFIG.loadConfigs(MOD_ID);
        ModRegistry.touch();
    }
}
