package fuzs.magnumtorch;

import fuzs.magnumtorch.handler.MobSpawningHandler;
import fuzs.magnumtorch.world.entity.SpawnDataMob;
import fuzs.puzzleslib.core.CommonFactories;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public class MagnumTorchFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CommonFactories.INSTANCE.modConstructor(MagnumTorch.MOD_ID).accept(new MagnumTorch());
        registerHandlers();
    }

    private static void registerHandlers() {
        ServerEntityEvents.ENTITY_LOAD.register((Entity entity, ServerLevel world) -> {
            if (!MobSpawningHandler.onLivingSpawn(entity, world, entity instanceof SpawnDataMob mob ? mob.puzzleslib$getSpawnType() : null)) {
                entity.setRemoved(Entity.RemovalReason.DISCARDED);
            }
        });
    }
}
