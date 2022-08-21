package fuzs.magnumtorch.data;

import fuzs.magnumtorch.init.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator p_125973_) {
        super(p_125973_);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> p_176532_) {
        ShapedRecipeBuilder.shaped(ModRegistry.DIAMOND_MAGNUM_TORCH_BLOCK.get())
                .define('L', ItemTags.LOGS)
                .define('T', Items.FIRE_CHARGE)
                .define('G', Items.GOLD_INGOT)
                .define('#', Items.DIAMOND)
                .pattern("GTG")
                .pattern("#L#")
                .pattern("#L#")
                .unlockedBy("has_diamond", has(Items.DIAMOND))
                .save(p_176532_);
        ShapedRecipeBuilder.shaped(ModRegistry.EMERALD_MAGNUM_TORCH_BLOCK.get())
                .define('L', ItemTags.LOGS)
                .define('T', Items.FIRE_CHARGE)
                .define('G', Items.GOLD_INGOT)
                .define('#', Items.EMERALD)
                .pattern("GTG")
                .pattern("#L#")
                .pattern("#L#")
                .unlockedBy("has_emerald", has(Items.EMERALD))
                .save(p_176532_);
        ShapedRecipeBuilder.shaped(ModRegistry.AMETHYST_MAGNUM_TORCH_BLOCK.get())
                .define('L', ItemTags.LOGS)
                .define('T', Items.FIRE_CHARGE)
                .define('G', Items.GOLD_INGOT)
                .define('#', Items.AMETHYST_SHARD)
                .pattern("GTG")
                .pattern("#L#")
                .pattern("#L#")
                .unlockedBy("has_amethyst_shard", has(Items.AMETHYST_SHARD))
                .save(p_176532_);
    }
}
