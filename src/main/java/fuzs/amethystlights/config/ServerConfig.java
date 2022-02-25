package fuzs.amethystlights.config;

import fuzs.puzzleslib.config.AbstractConfig;
import fuzs.puzzleslib.config.annotation.Config;
import fuzs.puzzleslib.config.serialization.EntryCollectionBuilder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;

public class ServerConfig extends AbstractConfig {
    @Config
    public TorchConfig torch = new TorchConfig();
    @Config
    public LanternConfig lantern = new LanternConfig();

    public ServerConfig() {
        super("");
    }

    @Override
    protected void afterConfigReload() {
        this.torch.afterConfigReload();
        this.lantern.afterConfigReload();
    }

    public enum FoodMechanics {
        VANILLA, COMBAT_UPDATE, LEGACY_COMBAT, CUSTOM
    }

    public enum ShapeType {
        ELLIPSOID, CYLINDER, CUBOID
    }

    public static abstract class SpawnBlockingConfig extends AbstractConfig {
        @Config(description = {"Mobs of this category are prevented from spawning through natural means (e.g. mob spawners and breeding will still work).", "For refining affected mobs use blacklist and whitelist options.", "If you only want to prevent a few mobs from spawning that do not fit any category, set this to \"MISC\" and include them in the blacklist option."})
        public MobCategory mobCategory;
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

        public Set<EntityType<?>> mobBlacklist;
        public Set<EntityType<?>> mobWhitelist;

        public SpawnBlockingConfig(String name) {
            super(name);
        }

        @Override
        protected void afterConfigReload() {
            this.mobBlacklist = EntryCollectionBuilder.of(ForgeRegistries.ENTITIES).buildSet(this.mobBlacklistRaw);
            this.mobWhitelist = EntryCollectionBuilder.of(ForgeRegistries.ENTITIES).buildSet(this.mobWhitelistRaw);
        }
    }

    public static class TorchConfig extends SpawnBlockingConfig {
        public TorchConfig() {
            super("magnum_torch");
            this.mobCategory = MobCategory.MONSTER;
            this.horizontalRange = 64;
            this.verticalRange = 32;
        }
    }

    public static class LanternConfig extends SpawnBlockingConfig {
        public LanternConfig() {
            super("amethyst_lantern");
            this.mobCategory = MobCategory.MONSTER;
            this.horizontalRange = 16;
            this.verticalRange = 8;
        }
    }
}
