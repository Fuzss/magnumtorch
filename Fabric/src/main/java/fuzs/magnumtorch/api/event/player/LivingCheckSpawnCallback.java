package fuzs.magnumtorch.api.event.player;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;

@FunctionalInterface
public interface LivingCheckSpawnCallback {
    Event<LivingCheckSpawnCallback> EVENT = EventFactory.createArrayBacked(LivingCheckSpawnCallback.class, listeners -> (EntityType<?> entityType, LevelAccessor levelAccessor, double posX, double posY, double posZ, MobSpawnType spawnType) -> {
        for (LivingCheckSpawnCallback event : listeners) {
            if (!event.onLivingCheckSpawn(entityType, levelAccessor, posX, posY, posZ, spawnType)) {
                return false;
            }
        }
        return true;
    });

    boolean onLivingCheckSpawn(EntityType<?> entityType, LevelAccessor levelAccessor, double posX, double posY, double posZ, MobSpawnType spawnType);
}
