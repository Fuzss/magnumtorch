package fuzs.magnumtorch.registry;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.block.MagnumTorchBlock;
import fuzs.puzzleslib.core.Services;
import fuzs.puzzleslib.registry.RegistryManager;
import fuzs.puzzleslib.registry.RegistryReference;
import fuzs.puzzleslib.registry.builder.ModPoiTypeBuilder;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class ModRegistry {
    private static final RegistryManager REGISTRY = Services.FACTORIES.registry(MagnumTorch.MOD_ID);
    public static final RegistryReference<Block> DIAMOND_MAGNUM_TORCH_BLOCK = REGISTRY.registerBlockWithItem("diamond_magnum_torch", () -> new MagnumTorchBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F, 3.5F).sound(SoundType.WOOD).lightLevel((p_187431_) -> {
        return 10;
    }).noOcclusion()), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryReference<Block> EMERALD_MAGNUM_TORCH_BLOCK = REGISTRY.registerBlockWithItem("emerald_magnum_torch", () -> new MagnumTorchBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F, 3.5F).sound(SoundType.WOOD).lightLevel((p_187431_) -> {
        return 10;
    }).noOcclusion()), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryReference<Block> AMETHYST_MAGNUM_TORCH_BLOCK = REGISTRY.registerBlockWithItem("amethyst_magnum_torch", () -> new MagnumTorchBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F, 3.5F).sound(SoundType.WOOD).lightLevel((p_187431_) -> {
        return 10;
    }).noOcclusion()), CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryReference<PoiType> DIAMOND_MAGNUM_TORCH_POI_TYPE = REGISTRY.registerPoiTypeBuilder("diamond_magnum_torch", () -> ModPoiTypeBuilder.of(0, 1, ModRegistry.DIAMOND_MAGNUM_TORCH_BLOCK.get()));
    public static final RegistryReference<PoiType> EMERALD_MAGNUM_TORCH_POI_TYPE = REGISTRY.registerPoiTypeBuilder("emerald_magnum_torch", () -> ModPoiTypeBuilder.of(0, 1, ModRegistry.EMERALD_MAGNUM_TORCH_BLOCK.get()));
    public static final RegistryReference<PoiType> AMETHYST_MAGNUM_TORCH_POI_TYPE = REGISTRY.registerPoiTypeBuilder("amethyst_magnum_torch", () -> ModPoiTypeBuilder.of(0, 1, ModRegistry.AMETHYST_MAGNUM_TORCH_BLOCK.get()));

    public static void touch() {

    }
}