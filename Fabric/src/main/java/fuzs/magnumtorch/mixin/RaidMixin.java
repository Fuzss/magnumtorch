package fuzs.magnumtorch.mixin;

import fuzs.magnumtorch.api.event.player.LivingCheckSpawnCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Raid.class)
public abstract class RaidMixin {
    @Shadow
    @Final
    private ServerLevel level;

    @Inject(method = "joinRaid", at = @At("HEAD"), cancellable = true)
    public void joinRaid$head(int waveCount, Raider raider, @Nullable BlockPos blockPos, boolean hasBeenAdded, CallbackInfo callbackInfo) {
        if (hasBeenAdded || blockPos == null) return;
        // handles new raiders being spawned
        // one problem: removing the leader will not allow a new one to spawn
        if (!LivingCheckSpawnCallback.EVENT.invoker().onLivingCheckSpawn(raider.getType(), this.level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), MobSpawnType.EVENT)) {
            if (raider.isPatrolLeader()) {
                this.removeLeader(waveCount);
            }
            raider.discard();
            callbackInfo.cancel();
        }
    }

    @Shadow
    public abstract void removeLeader(int i);
}
