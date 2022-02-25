package fuzs.magnumtorch;

import fuzs.magnumtorch.config.ServerConfig;
import fuzs.magnumtorch.data.ModBlockTagsProvider;
import fuzs.magnumtorch.data.ModLootTableProvider;
import fuzs.magnumtorch.data.ModRecipeProvider;
import fuzs.magnumtorch.handler.MobSpawningHandler;
import fuzs.magnumtorch.registry.ModRegistry;
import fuzs.puzzleslib.config.AbstractConfig;
import fuzs.puzzleslib.config.ConfigHolder;
import fuzs.puzzleslib.config.ConfigHolderImpl;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MagnumTorch.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MagnumTorch {
    public static final String MOD_ID = "magnumtorch";
    public static final String MOD_NAME = "Magnum Torch";
    public static final Logger LOGGER = LogManager.getLogger(MagnumTorch.MOD_NAME);

    @SuppressWarnings("Convert2MethodRef")
    public static final ConfigHolder<AbstractConfig, ServerConfig> CONFIG = ConfigHolder.server(() -> new ServerConfig());

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ((ConfigHolderImpl<?, ?>) CONFIG).addConfigs(MOD_ID);
        ModRegistry.touch();
        registerHandlers();
    }

    private static void registerHandlers() {
        MobSpawningHandler mobSpawningHandler = new MobSpawningHandler();
        MinecraftForge.EVENT_BUS.addListener(mobSpawningHandler::onCheckSpawn);
    }

    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent evt) {
        DataGenerator generator = evt.getGenerator();
        final ExistingFileHelper existingFileHelper = evt.getExistingFileHelper();
        generator.addProvider(new ModBlockTagsProvider(generator, MOD_ID, existingFileHelper));
        generator.addProvider(new ModLootTableProvider(generator, MOD_ID));
        generator.addProvider(new ModRecipeProvider(generator));
    }
}
