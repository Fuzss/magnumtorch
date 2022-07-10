package fuzs.magnumtorch.mixin;

import fuzs.magnumtorch.api.event.player.LivingCheckSpawnCallback;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.village.VillageSiege;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(VillageSiege.class)
public abstract class VillageSiegeMixin {

    @Inject(method = "trySpawn", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/entity/ai/village/VillageSiege;findRandomSpawnPos(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/phys/Vec3;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void trySpawn$invokeFindRandomSpawnPos(ServerLevel serverLevel, CallbackInfo callbackInfo, Vec3 vec3) {
        if (vec3 == null) return;
        // handles zombies spawned from village sieges
        if (!LivingCheckSpawnCallback.EVENT.invoker().onLivingCheckSpawn(EntityType.ZOMBIE, serverLevel, vec3.x(), vec3.y(), vec3.z(), MobSpawnType.EVENT)) {
            callbackInfo.cancel();
        }
    }
}
