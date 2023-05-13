package fuzs.magnumtorch;

import fuzs.magnumtorch.data.ModBlockTagsProvider;
import fuzs.magnumtorch.data.ModLootTableProvider;
import fuzs.magnumtorch.data.ModRecipeProvider;
import fuzs.magnumtorch.handler.MobSpawningHandler;
import fuzs.magnumtorch.world.entity.SpawnDataMob;
import fuzs.puzzleslib.core.CommonFactories;
import net.minecraft.data.DataGenerator;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod(MagnumTorch.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MagnumTorchForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        CommonFactories.INSTANCE.modConstructor(MagnumTorch.MOD_ID).accept(new MagnumTorch());
        registerHandlers();
    }

    private static void registerHandlers() {
        MinecraftForge.EVENT_BUS.addListener((final EntityJoinLevelEvent evt) -> {
            if (evt.getLevel().isClientSide) return;
            if (!MobSpawningHandler.onLivingSpawn(evt.getEntity(), (ServerLevel) evt.getLevel(), !evt.loadedFromDisk() && evt.getEntity() instanceof SpawnDataMob mob ? mob.puzzleslib$getSpawnType() : null)) {
                evt.setCanceled(true);
            }
        });
    }

    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent evt) {
        DataGenerator generator = evt.getGenerator();
        final ExistingFileHelper existingFileHelper = evt.getExistingFileHelper();
        generator.addProvider(true, new ModBlockTagsProvider(generator, MagnumTorch.MOD_ID, existingFileHelper));
        generator.addProvider(true, new ModLootTableProvider(generator, MagnumTorch.MOD_ID));
        generator.addProvider(true, new ModRecipeProvider(generator));
    }
}
