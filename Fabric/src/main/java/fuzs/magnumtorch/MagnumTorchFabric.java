package fuzs.magnumtorch;

import fuzs.magnumtorch.api.event.player.LivingCheckSpawnCallback;
import fuzs.magnumtorch.handler.MobSpawningHandler;
import fuzs.puzzleslib.core.CoreServices;
import net.fabricmc.api.ModInitializer;

public class MagnumTorchFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CoreServices.FACTORIES.modConstructor(MagnumTorch.MOD_ID).accept(new MagnumTorch());
        registerHandlers();
    }

    private static void registerHandlers() {
        MobSpawningHandler mobSpawningHandler = new MobSpawningHandler();
        LivingCheckSpawnCallback.EVENT.register(mobSpawningHandler::onLivingSpawn);
    }
}
