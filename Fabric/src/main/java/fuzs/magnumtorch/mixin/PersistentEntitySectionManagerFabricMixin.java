package fuzs.magnumtorch.mixin;

import fuzs.magnumtorch.handler.MobSpawningHandler;
import fuzs.magnumtorch.world.entity.SpawnDataMob;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PersistentEntitySectionManager.class)
abstract class PersistentEntitySectionManagerFabricMixin<T extends EntityAccess> {

    @Inject(method = "addEntity", at = @At("HEAD"), cancellable = true)
    private void addEntity(T entityAccess, boolean worldGenSpawned, CallbackInfoReturnable<Boolean> callback) {
        if (entityAccess instanceof Entity entity) {
            MobSpawnType spawnType = !worldGenSpawned && entityAccess instanceof SpawnDataMob mob ? mob.puzzleslib$getSpawnType() : null;
            if (!MobSpawningHandler.onLivingSpawn(entity, (ServerLevel) entity.level, spawnType)) {
                callback.setReturnValue(false);
            }
        }
    }
}
