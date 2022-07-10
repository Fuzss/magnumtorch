package fuzs.magnumtorch.mixin;

import fuzs.magnumtorch.api.event.player.LivingCheckSpawnCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.levelgen.PatrolSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(PatrolSpawner.class)
public abstract class PatrolSpawnerMixin {

    @Inject(method = "spawnPatrolMember", at = @At("HEAD"), cancellable = true)
    private void spawnPatrolMember$head(ServerLevel serverLevel, BlockPos blockPos, RandomSource random, boolean bl, CallbackInfoReturnable<Boolean> callbackInfo) {
        // handles pillagers spawned as a member of patrols
        if (!LivingCheckSpawnCallback.EVENT.invoker().onLivingCheckSpawn(EntityType.PILLAGER, serverLevel, blockPos.getX(), blockPos.getY(), blockPos.getZ(), MobSpawnType.PATROL)) {
            callbackInfo.setReturnValue(false);
        }
    }
}
