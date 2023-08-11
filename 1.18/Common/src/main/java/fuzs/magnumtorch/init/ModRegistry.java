package fuzs.magnumtorch.init;

import fuzs.magnumtorch.MagnumTorch;
import fuzs.magnumtorch.world.level.block.MagnumTorchBlock;
import fuzs.puzzleslib.api.init.v2.RegistryManager;
import fuzs.puzzleslib.api.init.v2.RegistryReference;
import fuzs.puzzleslib.api.init.v2.builder.PoiTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class ModRegistry {
    static final RegistryManager REGISTRY = RegistryManager.instant(MagnumTorch.MOD_ID);
    public static final RegistryReference<Block> DIAMOND_MAGNUM_TORCH_BLOCK = REGISTRY.placeholder(Registry.BLOCK_REGISTRY, MagnumTorchBlock.Type.DIAMOND.getSerializedName());
    public static final RegistryReference<Block> EMERALD_MAGNUM_TORCH_BLOCK = REGISTRY.placeholder(Registry.BLOCK_REGISTRY, MagnumTorchBlock.Type.EMERALD.getSerializedName());
    public static final RegistryReference<Block> AMETHYST_MAGNUM_TORCH_BLOCK = REGISTRY.placeholder(Registry.BLOCK_REGISTRY, MagnumTorchBlock.Type.AMETHYST.getSerializedName());
    public static final RegistryReference<Item> DIAMOND_MAGNUM_TORCH_ITEM = REGISTRY.placeholder(Registry.ITEM_REGISTRY, MagnumTorchBlock.Type.DIAMOND.getSerializedName());
    public static final RegistryReference<Item> EMERALD_MAGNUM_TORCH_ITEM = REGISTRY.placeholder(Registry.ITEM_REGISTRY, MagnumTorchBlock.Type.EMERALD.getSerializedName());
    public static final RegistryReference<Item> AMETHYST_MAGNUM_TORCH_ITEM = REGISTRY.placeholder(Registry.ITEM_REGISTRY, MagnumTorchBlock.Type.AMETHYST.getSerializedName());
    public static final RegistryReference<PoiType> DIAMOND_MAGNUM_TORCH_POI_TYPE = REGISTRY.placeholder(Registry.POINT_OF_INTEREST_TYPE_REGISTRY, MagnumTorchBlock.Type.DIAMOND.getSerializedName());
    public static final RegistryReference<PoiType> EMERALD_MAGNUM_TORCH_POI_TYPE = REGISTRY.placeholder(Registry.POINT_OF_INTEREST_TYPE_REGISTRY, MagnumTorchBlock.Type.EMERALD.getSerializedName());
    public static final RegistryReference<PoiType> AMETHYST_MAGNUM_TORCH_POI_TYPE = REGISTRY.placeholder(Registry.POINT_OF_INTEREST_TYPE_REGISTRY, MagnumTorchBlock.Type.AMETHYST.getSerializedName());

    public static void touch() {
        for (MagnumTorchBlock.Type type : MagnumTorchBlock.Type.values()) {
            RegistryReference<Block> block = REGISTRY.registerBlock(type.getSerializedName(), () -> new MagnumTorchBlock(type, BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F, 3.5F).sound(SoundType.WOOD).lightLevel(state -> {
                return 10;
            }).noOcclusion()));
            REGISTRY.registerBlockItem(block);
            REGISTRY.registerPoiTypeBuilder(type.getSerializedName(), () -> PoiTypeBuilder.of(0, 1, block.get()));
        }
    }
}