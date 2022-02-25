package fuzs.amethystlights.registry;

import com.google.common.collect.ImmutableSet;
import fuzs.amethystlights.AmethystLights;
import fuzs.amethystlights.block.MagnumTorchBlock;
import fuzs.puzzleslib.registry.RegistryManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.RegistryObject;

public class ModRegistry {
    private static final RegistryManager REGISTRY = RegistryManager.of(AmethystLights.MOD_ID);
    public static final RegistryObject<Block> MAGNUM_TORCH_BLOCK = REGISTRY.registerBlock("magnum_torch", () -> new MagnumTorchBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(2.0F, 3.5F).lightLevel((p_187431_) -> {
        return 5;
    }).noOcclusion()));
    public static final RegistryObject<Block> AMETHYST_LANTERN_BLOCK = REGISTRY.registerBlock("amethyst_lantern", () -> new LanternBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.LANTERN).lightLevel((p_187431_) -> {
        return 5;
    }).noOcclusion()));
    public static final RegistryObject<PoiType> MAGNUM_TORCH_POI_TYPE = REGISTRY.register(PoiType.class, "magnum_torch", () -> new PoiType("magnum_torch", ImmutableSet.copyOf(MAGNUM_TORCH_BLOCK.get().getStateDefinition().getPossibleStates()), 0, 1));
    public static final RegistryObject<PoiType> AMETHYST_LANTERN_POI_TYPE = REGISTRY.register(PoiType.class, "amethyst_lantern", () -> new PoiType("amethyst_lantern", ImmutableSet.copyOf(AMETHYST_LANTERN_BLOCK.get().getStateDefinition().getPossibleStates()), 0, 1));

    public static void touch() {

    }
}