package org.sam.oresmod.screen;

import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static ScreenHandlerType<NetheriteScreenHandler> NETHERITE_SCREEN_HANDLER;
    public static ScreenHandlerType<SuperEnchantmentScreenHandler> SUPER_ENCH_HANDLER;

    public static void registerAllScreenHandlers(){
        NETHERITE_SCREEN_HANDLER = new ScreenHandlerType<>(NetheriteScreenHandler::new, FeatureSet.empty());
        SUPER_ENCH_HANDLER = new ScreenHandlerType<>(SuperEnchantmentScreenHandler::new, FeatureSet.empty());
    }
}
