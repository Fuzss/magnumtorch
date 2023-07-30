package fuzs.magnumtorch;

import fuzs.puzzleslib.core.CommonFactories;
import net.fabricmc.api.ModInitializer;

public class MagnumTorchFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CommonFactories.INSTANCE.modConstructor(MagnumTorch.MOD_ID).accept(new MagnumTorch());
    }
}
