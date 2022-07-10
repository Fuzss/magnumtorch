package fuzs.magnumtorch.mixin;

import fuzs.magnumtorch.api.event.player.LivingCheckSpawnCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(SpawnPlacements.class)
public abstract class SpawnPlacementsMixin {

    @Inject(method = "checkSpawnRules", at = @At("TAIL"), cancellable = true)
    private static <T extends Entity> void checkSpawnRules$tail(EntityType<T> entityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource random, CallbackInfoReturnable<Boolean> callbackInfo) {
        if (!callbackInfo.getReturnValue()) return;
        // handles a bunch of cases: natural spawns for mobs, natural spawns on world gen, zombie reinforcements, mob spawner spawn attempts
        if (!LivingCheckSpawnCallback.EVENT.invoker().onLivingCheckSpawn(entityType, serverLevelAccessor, blockPos.getX(), blockPos.getY(), blockPos.getZ(), mobSpawnType)) {
            callbackInfo.setReturnValue(false);
        }
    }
}
