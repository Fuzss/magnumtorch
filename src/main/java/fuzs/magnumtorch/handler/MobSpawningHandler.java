package fuzs.magnumtorch.handler;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.config.ServerConfig;
import fuzs.magnumtorch.registry.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.stream.Stream;

public class MobSpawningHandler {
    @SubscribeEvent
    public void onCheckSpawn(final LivingSpawnEvent.CheckSpawn evt) {
        if (evt.getWorld().isClientSide()) return;
        PoiManager poiManager = ((ServerLevel) evt.getWorld()).getPoiManager();
        LivingEntity entity = evt.getEntityLiving();
        BlockPos pos = new BlockPos(evt.getX(), evt.getY(), evt.getZ());
        if (this.isSpawnCancelled(poiManager, ModRegistry.DIAMOND_MAGNUM_TORCH_POI_TYPE.get(), entity, pos, MagnumTorch.CONFIG.server().diamond)) {
            evt.setResult(Event.Result.DENY);
        } else if (this.isSpawnCancelled(poiManager, ModRegistry.EMERALD_MAGNUM_TORCH_POI_TYPE.get(), entity, pos, MagnumTorch.CONFIG.server().emerald)) {
            evt.setResult(Event.Result.DENY);
        } else if (this.isSpawnCancelled(poiManager, ModRegistry.AMETHYST_MAGNUM_TORCH_POI_TYPE.get(), entity, pos, MagnumTorch.CONFIG.server().amethyst)) {
            evt.setResult(Event.Result.DENY);
        }
    }

    private boolean isSpawnCancelled(PoiManager poiManager, PoiType poiType, LivingEntity entity, BlockPos pos, ServerConfig.TorchConfig config) {
        return this.isAffected(entity, config) && this.anyInRange(poiManager, poiType, pos, config);
    }

    private boolean isAffected(LivingEntity entity, ServerConfig.TorchConfig config) {
        if (config.mobWhitelist.contains(entity.getType())) return false;
        if (config.mobCategories.contains(entity.getType().getCategory())) return true;
        return config.mobBlacklist.contains(entity.getType());
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
                yield (dimX * dimX + dimZ * dimZ) / (float) (horizontalRange * horizontalRange) + dimY / (float) verticalRange <= 1.0;
            }
            case CUBOID -> {
                yield (dimX + dimZ) / (float) horizontalRange + dimY / (float) verticalRange <= 1.0;
            }
        };
    }
}
