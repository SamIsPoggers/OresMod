package org.sam.oresmod;

import net.fabricmc.api.ModInitializer;
import org.sam.oresmod.block.ModBlocks;
import org.sam.oresmod.item.ModItemGroups;
import org.sam.oresmod.item.ModItems;
import org.sam.oresmod.world.gen.ModWorldGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OresMod implements ModInitializer {

    public static final String MOD_ID = "oresmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    @Override
    public void onInitialize() {
        LOGGER.info("Hello Fabric World!");

        ModItems.registerModItems();
        ModItemGroups.registerItemGroups();

        ModBlocks.registerModBlocks();

        ModWorldGeneration.generateModWorldGen();

        // Register ArmorHearts to enable extra hearts based on equipped armor
        ArmorHearts.register();

    }
}
