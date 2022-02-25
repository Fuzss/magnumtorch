package fuzs.amethystlights.handler;

import fuzs.amethystlights.config.ServerConfig;
import fuzs.amethystlights.registry.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Optional;
import java.util.Set;

public class MobSpawningHandler {
    @SubscribeEvent
    public void onCheckSpawn(final LivingSpawnEvent.CheckSpawn evt) {
        if (!evt.getWorld().isClientSide()) {
            ServerLevel level = (ServerLevel) evt.getWorld();
            Optional<BlockPos> optional = level.getPoiManager().findClosest(poiType -> poiType == ModRegistry.MAGNUM_TORCH_POI_TYPE.get(), new BlockPos(evt.getX(), evt.getY(), evt.getZ()), 64, PoiManager.Occupancy.ANY);
        }
    }

    private static boolean isAffected(LivingEntity entity, ServerConfig.SpawnBlockingConfig config) {
        if (config.mobWhitelist.contains(entity.getType())) return false;
        if (entity.getType().getCategory() == config.mobCategory) return true;
        return config.mobBlacklist.contains(entity.getType());
    }

    private Optional<BlockPos> findLightningRod(BlockPos p_143249_) {
        Optional<BlockPos> optional = this.getPoiManager().findClosest((p_184069_) -> {
            return p_184069_ == PoiType.LIGHTNING_ROD;
        }, (p_184055_) -> {
            return p_184055_.getY() == this.getLevel().getHeight(Heightmap.Types.WORLD_SURFACE, p_184055_.getX(), p_184055_.getZ()) - 1;
        }, p_143249_, 128, PoiManager.Occupancy.ANY);
        return optional.map((p_184053_) -> {
            return p_184053_.above(1);
        });
    }
}
