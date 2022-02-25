package fuzs.magnumtorch.registry;

import com.google.common.collect.ImmutableSet;
import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.block.MagnumTorchBlock;
import fuzs.puzzleslib.registry.RegistryManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.RegistryObject;

public class ModRegistry {
    private static final RegistryManager REGISTRY = RegistryManager.of(MagnumTorch.MOD_ID);
    public static final RegistryObject<Block> DIAMOND_MAGNUM_TORCH_BLOCK = REGISTRY.registerBlockWithItem("diamond_magnum_torch", () -> new MagnumTorchBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F, 3.5F).sound(SoundType.WOOD).lightLevel((p_187431_) -> {
        return 10;
    }).noOcclusion()), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> EMERALD_MAGNUM_TORCH_BLOCK = REGISTRY.registerBlockWithItem("emerald_magnum_torch", () -> new MagnumTorchBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F, 3.5F).sound(SoundType.WOOD).lightLevel((p_187431_) -> {
        return 10;
    }).noOcclusion()), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> AMETHYST_MAGNUM_TORCH_BLOCK = REGISTRY.registerBlockWithItem("amethyst_magnum_torch", () -> new MagnumTorchBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F, 3.5F).sound(SoundType.WOOD).lightLevel((p_187431_) -> {
        return 10;
    }).noOcclusion()), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<PoiType> DIAMOND_MAGNUM_TORCH_POI_TYPE = REGISTRY.register(PoiType.class, "diamond_magnum_torch", () -> new PoiType("diamond_magnum_torch", ImmutableSet.copyOf(DIAMOND_MAGNUM_TORCH_BLOCK.get().getStateDefinition().getPossibleStates()), 0, 1));
    public static final RegistryObject<PoiType> EMERALD_MAGNUM_TORCH_POI_TYPE = REGISTRY.register(PoiType.class, "emerald_magnum_torch", () -> new PoiType("emerald_magnum_torch", ImmutableSet.copyOf(EMERALD_MAGNUM_TORCH_BLOCK.get().getStateDefinition().getPossibleStates()), 0, 1));
    public static final RegistryObject<PoiType> AMETHYST_MAGNUM_TORCH_POI_TYPE = REGISTRY.register(PoiType.class, "amethyst_magnum_torch", () -> new PoiType("amethyst_magnum_torch", ImmutableSet.copyOf(AMETHYST_MAGNUM_TORCH_BLOCK.get().getStateDefinition().getPossibleStates()), 0, 1));

    public static void touch() {

    }
}