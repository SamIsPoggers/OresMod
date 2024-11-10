package org.sam.oresmod.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import org.sam.oresmod.client.gui.screen.ingame.NetheriteAnvilScreen;
import org.sam.oresmod.client.gui.screen.ingame.SuperEnchantmentScreen;
import org.sam.oresmod.screen.ModScreenHandlers;

public class OresModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModScreenHandlers.registerAllScreenHandlers();

        HandledScreens.register(ModScreenHandlers.NETHERITE_SCREEN_HANDLER, NetheriteAnvilScreen::new);
        HandledScreens.register(ModScreenHandlers.SUPER_ENCH_HANDLER, SuperEnchantmentScreen::new);
    }
}
