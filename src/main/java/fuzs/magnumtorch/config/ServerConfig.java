package fuzs.magnumtorch.config;

import fuzs.puzzleslib.config.AbstractConfig;
import fuzs.puzzleslib.config.annotation.Config;
import fuzs.puzzleslib.config.serialization.EntryCollectionBuilder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
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
        @Config(name = "mob_category", description = {"Mobs of this category are prevented from spawning through natural means (e.g. mob spawners and breeding will still work).", "For refining affected mobs use blacklist and whitelist options.", "If you only want to prevent a few mob types from spawning that do not fit any category leave this list empty and include them in the blacklist option."})
        @Config.AllowedValues(values = {"MONSTER", "CREATURE", "AMBIENT", "AXOLOTLS", "UNDERGROUND_WATER_CREATURE", "WATER_CREATURE", "WATER_AMBIENT"})
        List<String> mobCategoryRaw = Stream.of(MobCategory.MONSTER).map(Enum::name).collect(Collectors.toList());
        @Config(name = "mob_blacklist", description = {"Mobs that should not be allowed to spawn despite being absent from \"mob_category\".", EntryCollectionBuilder.CONFIG_DESCRIPTION})
        List<String> mobBlacklistRaw = EntryCollectionBuilder.getKeyList(ForgeRegistries.ENTITIES);
        @Config(name = "mob_whitelist", description = {"Mobs that should still be allowed to spawn despite being included in \"mob_category\".", EntryCollectionBuilder.CONFIG_DESCRIPTION})
        List<String> mobWhitelistRaw = EntryCollectionBuilder.getKeyList(ForgeRegistries.ENTITIES);
        @Config(description = {"Type of shape used for calculating area in which spawns are prevented.", "This basically let's you choose between taxi cab or euclidean metrics."})
        public ShapeType shapeType = ShapeType.ELLIPSOID;
        @Config(description = "Range for preventing mob spawns on x-z-plane.")
        @Config.IntRange(min = 0)
        public int horizontalRange;
        @Config(description = "Range for preventing mob spawns on y-dimension.")
        @Config.IntRange(min = 0)
        public int verticalRange;
        // do not include event type as it will break raids
        @Config(description = "Types of mob spawns to block. By default this is configured to only affect natural spawns occurring without player interaction.")
        @Config.AllowedValues(values = {"NATURAL", "CHUNK_GENERATION", "SPAWNER", "STRUCTURE", "BREEDING", "MOB_SUMMONED", "JOCKEY", "EVENT", "CONVERSION", "REINFORCEMENT", "TRIGGERED", "BUCKET", "SPAWN_EGG", "COMMAND", "DISPENSER", "PATROL"})
        List<String> blockedSpawnTypesRaw = Stream.of(MobSpawnType.NATURAL, MobSpawnType.PATROL).map(Enum::name).collect(Collectors.toList());

        public Set<MobCategory> mobCategories;
        public Set<MobSpawnType> blockedSpawnTypes;
        public Set<EntityType<?>> mobBlacklist;
        public Set<EntityType<?>> mobWhitelist;

        public TorchConfig(String name) {
            super(name);
        }

        @Override
        protected void afterConfigReload() {
            // manually process enum lists as night config keeps values as strings, making it hard to deal with as generic type suggests an enum value
            this.mobCategories = this.mobCategoryRaw.stream().map(MobCategory::valueOf).collect(Collectors.toSet());
            this.blockedSpawnTypes = this.blockedSpawnTypesRaw.stream().map(MobSpawnType::valueOf).collect(Collectors.toSet());
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
            // additionally includes event type to block wandering trader and llama spawning
            this.blockedSpawnTypesRaw = Stream.of(MobSpawnType.NATURAL, MobSpawnType.EVENT, MobSpawnType.PATROL).map(Enum::name).collect(Collectors.toList());
        }
    }
}
