package fuzs.magnumtorch.config;

import fuzs.puzzleslib.config.AbstractConfig;
import fuzs.puzzleslib.config.annotation.Config;
import fuzs.puzzleslib.config.serialization.EntryCollectionBuilder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServerConfig extends AbstractConfig {
    @Config
    public DiamondConfig diamond = new DiamondConfig();
    @Config
    public EmeraldConfig emerald = new EmeraldConfig();
    @Config
    public AmethystConfig amethyst = new AmethystConfig();

    public ServerConfig() {
        super("");
    }

    @Override
    protected void afterConfigReload() {
        this.diamond.afterConfigReload();
        this.emerald.afterConfigReload();
        this.amethyst.afterConfigReload();
    }

    public enum ShapeType {
        ELLIPSOID, CYLINDER, CUBOID
    }

    public static abstract class TorchConfig extends AbstractConfig {
        @Config(description = {"Mobs of this category are prevented from spawning through natural means (e.g. mob spawners and breeding will still work).", "For refining affected mobs use blacklist and whitelist options.", "If you only want to prevent a few mobs from spawning that do not fit any category leave this list empty and include them in the blacklist option."})
        @Config.AllowedValues(values = {"MONSTER", "CREATURE", "AMBIENT", "AXOLOTLS", "UNDERGROUND_WATER_CREATURE", "WATER_CREATURE", "WATER_AMBIENT"})
        List<String> mobCategoryRaw = Stream.of(MobCategory.MONSTER).map(Enum::name).collect(Collectors.toList());
        @Config(description = "Mobs that should not be allowed to spawn despite being absent from \"mob_category\".")
        List<String> mobBlacklistRaw = EntryCollectionBuilder.getKeyList(ForgeRegistries.ENTITIES);
        @Config(description = "Mobs that should still be allowed to spawn despite being included in \"mob_category\".")
        List<String> mobWhitelistRaw = EntryCollectionBuilder.getKeyList(ForgeRegistries.ENTITIES);
        @Config(description = {"Type of shaped used for calculating area in which spawns are prevented.", "This basically let's you choose between taxi cab or euclidian metrics."})
        public ShapeType shapeType = ShapeType.ELLIPSOID;
        @Config(description = "Range for preventing mob spawns on x-z-plane.")
        public int horizontalRange;
        @Config(description = "Range for preventing mob spawns on y-dimension.")
        public int verticalRange;

        public Set<MobCategory> mobCategories;
        public Set<EntityType<?>> mobBlacklist;
        public Set<EntityType<?>> mobWhitelist;

        public TorchConfig(String name) {
            super(name);
        }

        @Override
        protected void afterConfigReload() {
            this.mobCategories = this.mobCategoryRaw.stream().map(MobCategory::valueOf).collect(Collectors.toSet());
            this.mobBlacklist = EntryCollectionBuilder.of(ForgeRegistries.ENTITIES).buildSet(this.mobBlacklistRaw);
            this.mobWhitelist = EntryCollectionBuilder.of(ForgeRegistries.ENTITIES).buildSet(this.mobWhitelistRaw);
        }
    }

    public static class DiamondConfig extends TorchConfig {
        public DiamondConfig() {
            super("diamond_torch");
            this.mobCategoryRaw = Stream.of(MobCategory.MONSTER).map(Enum::name).collect(Collectors.toList());
            this.horizontalRange = 64;
            this.verticalRange = 32;
        }
    }

    public static class EmeraldConfig extends TorchConfig {
        public EmeraldConfig() {
            super("emerald_torch");
            this.mobCategoryRaw = Stream.of(MobCategory.AMBIENT, MobCategory.AXOLOTLS, MobCategory.WATER_AMBIENT, MobCategory.WATER_CREATURE, MobCategory.UNDERGROUND_WATER_CREATURE).map(Enum::name).collect(Collectors.toList());
            this.horizontalRange = 64;
            this.verticalRange = 32;
        }
    }

    public static class AmethystConfig extends TorchConfig {
        public AmethystConfig() {
            super("amethyst_torch");
            this.mobCategoryRaw = Stream.of(MobCategory.CREATURE).map(Enum::name).collect(Collectors.toList());
            this.horizontalRange = 64;
            this.verticalRange = 32;
        }
    }
}
