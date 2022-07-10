package fuzs.magnumtorch.mixin;

import fuzs.magnumtorch.api.event.player.LivingCheckSpawnCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.SpawnUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(SpawnUtil.class)
public abstract class SpawnUtilMixin {

    @Inject(method = "trySpawnMob", at = @At("HEAD"), cancellable = true)
    private static <T extends Mob> void trySpawnMob(EntityType<T> entityType, MobSpawnType mobSpawnType, ServerLevel serverLevel, BlockPos blockPos, int i, int j, int k, SpawnUtil.Strategy strategy, CallbackInfoReturnable<Optional<T>> callback) {
        // handles usually just iron golems summoned by villagers, and wardens from sculk shriekers
        if (!LivingCheckSpawnCallback.EVENT.invoker().onLivingCheckSpawn(entityType, serverLevel, blockPos.getX(), blockPos.getY(), blockPos.getZ(), mobSpawnType)) {
            callback.setReturnValue(Optional.empty());
        }
    }
}
