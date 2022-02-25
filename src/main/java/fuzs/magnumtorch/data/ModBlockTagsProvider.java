package fuzs.magnumtorch.data;

import fuzs.magnumtorch.registry.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator p_126511_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_126511_, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(ModRegistry.DIAMOND_MAGNUM_TORCH_BLOCK.get(), ModRegistry.EMERALD_MAGNUM_TORCH_BLOCK.get(), ModRegistry.AMETHYST_MAGNUM_TORCH_BLOCK.get());
    }
}
