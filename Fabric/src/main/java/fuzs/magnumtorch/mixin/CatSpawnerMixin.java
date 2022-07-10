package fuzs.magnumtorch.mixin;

import fuzs.magnumtorch.api.event.player.LivingCheckSpawnCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.npc.CatSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CatSpawner.class)
public abstract class CatSpawnerMixin {

    @Inject(method = "spawnCat", at = @At("HEAD"), cancellable = true)
    private void spawnCat$head(BlockPos blockPos, ServerLevel serverLevel, CallbackInfoReturnable<Integer> callbackInfo) {
        // handles cats spawned in villages
        if (!LivingCheckSpawnCallback.EVENT.invoker().onLivingCheckSpawn(EntityType.CAT, serverLevel, blockPos.getX(), blockPos.getY(), blockPos.getZ(), MobSpawnType.NATURAL)) {
            callbackInfo.setReturnValue(0);
        }
    }
}
