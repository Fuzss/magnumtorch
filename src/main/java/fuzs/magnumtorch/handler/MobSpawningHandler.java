package fuzs.magnumtorch.handler;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.config.ServerConfig;
import fuzs.magnumtorch.registry.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.stream.Stream;

public class MobSpawningHandler {
    @SubscribeEvent
    public void onCheckSpawn(final LivingSpawnEvent.CheckSpawn evt) {
        // handles most spawn attempts such as natural spawning at night, cats, phantoms, patrols and monster spawners
        if (!this.onLivingSpawn(evt.getEntityLiving().getType(), evt.getWorld(), evt.getX(), evt.getY(), evt.getZ(), evt.getSpawnReason())) {
            evt.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public void onSpecialSpawn(final LivingSpawnEvent.SpecialSpawn evt) {
        // only respond to event firing from EntityType::spawn, other Forge implementations are broken, therefore specifically exclude those
        // this is fine since the ones we care about should already have been handled by CheckSpawn
        if (evt.getSpawnReason() == MobSpawnType.NATURAL || evt.getSpawnReason() == MobSpawnType.SPAWNER) return;
        // handles missing cases from CheckSpawn, most importantly wandering traders, zombie pigmen from portals
        if (!this.onLivingSpawn(evt.getEntityLiving().getType(), evt.getWorld(), evt.getX(), evt.getY(), evt.getZ(), evt.getSpawnReason())) {
            evt.setCanceled(true);
        }
    }

    private boolean onLivingSpawn(EntityType<?> entityType, LevelAccessor levelAccessor, double posX, double posY, double posZ, MobSpawnType spawnType) {
        if (levelAccessor.isClientSide()) return true;
        PoiManager poiManager = ((ServerLevelAccessor) levelAccessor).getLevel().getPoiManager();
        BlockPos pos = new BlockPos(posX, posY, posZ);
        if (this.isSpawnCancelled(poiManager, entityType, pos, spawnType, ModRegistry.DIAMOND_MAGNUM_TORCH_POI_TYPE.get(), MagnumTorch.CONFIG.server().diamond)) {
            return false;
        } else if (this.isSpawnCancelled(poiManager, entityType, pos, spawnType, ModRegistry.EMERALD_MAGNUM_TORCH_POI_TYPE.get(), MagnumTorch.CONFIG.server().emerald)) {
            return false;
        } else if (this.isSpawnCancelled(poiManager, entityType, pos, spawnType, ModRegistry.AMETHYST_MAGNUM_TORCH_POI_TYPE.get(), MagnumTorch.CONFIG.server().amethyst)) {
            return false;
        }
        return true;
    }

    private boolean isSpawnCancelled(PoiManager poiManager, EntityType<?> entityType, BlockPos pos, MobSpawnType spawnType, PoiType poiType, ServerConfig.TorchConfig config) {
        return config.blockedSpawnTypes.contains(spawnType) && this.isAffected(entityType, config) && this.anyInRange(poiManager, poiType, pos, config);
    }

    private boolean isAffected(EntityType<?> entityType, ServerConfig.TorchConfig config) {
        if (config.mobWhitelist.contains(entityType)) return false;
        if (config.mobCategories.contains(entityType.getCategory())) return true;
        return config.mobBlacklist.contains(entityType);
    }

    private boolean anyInRange(PoiManager poiManager, PoiType poiType, BlockPos pos, ServerConfig.TorchConfig config) {
        // range based on cuboid
        Stream<BlockPos> all = poiManager.findAll(poiType1 -> poiType1 == poiType, pos1 -> true, pos, (int) Math.ceil(Math.sqrt(config.horizontalRange * config.horizontalRange + config.verticalRange * config.verticalRange)), PoiManager.Occupancy.ANY);
        return all.anyMatch(center -> this.isInRange(center, pos, config.horizontalRange, config.verticalRange, config.shapeType));
    }

    private boolean isInRange(BlockPos center, BlockPos pos, int horizontalRange, int verticalRange, ServerConfig.ShapeType shapeType) {
        int dimX = Math.abs(center.getX() - pos.getX());
        int dimY = Math.abs(center.getY() - pos.getY());
        int dimZ = Math.abs(center.getZ() - pos.getZ());
        return switch (shapeType) {
            case ELLIPSOID -> {
                yield (dimX * dimX + dimZ * dimZ) / (float) (horizontalRange * horizontalRange) + (dimY * dimY) / (float) (verticalRange * verticalRange) <= 1.0;
            }
            case CYLINDER -> {
                yield dimX * dimX + dimZ * dimZ <= horizontalRange * horizontalRange && dimY <= verticalRange;
            }
            case CUBOID -> {
                yield dimX <= horizontalRange && dimZ <= horizontalRange && dimY <= verticalRange;
            }
        };
    }
}
