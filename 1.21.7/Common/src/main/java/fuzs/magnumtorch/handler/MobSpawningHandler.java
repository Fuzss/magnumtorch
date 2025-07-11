package fuzs.magnumtorch.handler;

import com.google.common.base.Predicates;
import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.config.ServerConfig;
import fuzs.magnumtorch.world.level.block.MagnumTorchType;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import fuzs.puzzleslib.api.util.v1.EntityHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MobSpawningHandler {

    public static EventResult onEntityLoad(Entity entity, ServerLevel serverLevel, boolean isNewlySpawned) {
        if (!isNewlySpawned || !(entity instanceof Mob mob) || !MagnumTorch.CONFIG.getHolder(ServerConfig.class)
                .isAvailable()) {
            return EventResult.PASS;
        }
        EntitySpawnReason entitySpawnReason = EntityHelper.getMobSpawnReason(mob);
        if (entitySpawnReason != null) {
            for (MagnumTorchType type : MagnumTorchType.values()) {
                if (isSpawnCancelled(serverLevel.getPoiManager(),
                        entity.getType(),
                        entity.blockPosition(),
                        entitySpawnReason,
                        type.getPoiType().key(),
                        type.getConfig())) {
                    // collect for running at the end of this server tick, other passengers might still be added to the level after this,
                    // and calling Entity::discard to early for them will log an annoying warning
                    List<Entity> entities = entity.getRootVehicle()
                            .getSelfAndPassengers()
                            .distinct()
                            .filter((Entity passenger) -> passenger != entity)
                            .toList();
                    serverLevel.getServer().schedule(new TickTask(serverLevel.getServer().getTickCount(), () -> {
                        entities.forEach(Entity::discard);
                    }));
                    return EventResult.INTERRUPT;
                }
            }
        }
        return EventResult.PASS;
    }

    private static boolean isSpawnCancelled(PoiManager poiManager, EntityType<?> entityType, BlockPos toCheck, @NotNull EntitySpawnReason spawnType, ResourceKey<PoiType> poiTypeKey, ServerConfig.MagnumTorchConfig config) {
        if (config.blockedSpawnTypes.contains(spawnType) && config.isAffected(entityType)) {
            // range based on cuboid as it contains all other shape types, 1.7320508076=3**0.5
            return poiManager.findAll(poiType -> poiType.is(poiTypeKey),
                            Predicates.alwaysTrue(),
                            toCheck,
                            (int) Math.ceil(Math.max(config.horizontalRange, config.verticalRange) * 1.7320508076),
                            PoiManager.Occupancy.ANY)
                    .anyMatch(pos -> config.shapeType.isPositionContained(pos,
                            toCheck,
                            config.horizontalRange,
                            config.verticalRange));
        }
        return false;
    }
}
