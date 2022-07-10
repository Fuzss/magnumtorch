package fuzs.magnumtorch;

import fuzs.magnumtorch.api.event.player.LivingCheckSpawnCallback;
import fuzs.magnumtorch.handler.MobSpawningHandler;
import net.fabricmc.api.ModInitializer;

public class MagnumTorchFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        MagnumTorch.onConstructMod();
        registerHandlers();
    }

    private static void registerHandlers() {
        MobSpawningHandler mobSpawningHandler = new MobSpawningHandler();
        LivingCheckSpawnCallback.EVENT.register(mobSpawningHandler::onLivingSpawn);
    }
}
