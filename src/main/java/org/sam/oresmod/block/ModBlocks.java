package org.sam.oresmod.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import org.sam.oresmod.OresMod;
import org.sam.oresmod.block.custom.NetheriteAnvilBlock;
import org.sam.oresmod.block.custom.SuperEnchantmentTable;

public class ModBlocks {

    public static final Block ULTRIS_ORE = RegisterBlock("ultris_ore"
            , new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.ANCIENT_DEBRIS)
                    .mapColor(MapColor.LIME).sounds(BlockSoundGroup.STONE), UniformIntProvider.create(3, 7)));
    public static final Block DEEPSLATE_ULTRIS_ORE = RegisterBlock("deepslate_ultris_ore"
            , new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.ANCIENT_DEBRIS)
                    .mapColor(MapColor.LIME).sounds(BlockSoundGroup.DEEPSLATE), UniformIntProvider.create(3, 7)));
    public static final Block ULTRIS_BLOCK = RegisterBlock("ultris_block", new Block(FabricBlockSettings.copyOf(Blocks.ANCIENT_DEBRIS).mapColor(MapColor.LIME).sounds(BlockSoundGroup.METAL)));


    public static final Block NETHERITE_ANVIL = RegisterBlock("netherite_anvil",
            new NetheriteAnvilBlock(FabricBlockSettings.copyOf(Blocks.ANVIL).mapColor(MapColor.DEEPSLATE_GRAY).
                    sounds(BlockSoundGroup.ANVIL).nonOpaque()));

    public static final Block SUPER_ENCH_TABLE = RegisterBlock("super_enchanting_table",
            new SuperEnchantmentTable(FabricBlockSettings.copyOf(Blocks.ENCHANTING_TABLE).mapColor(MapColor.PALE_PURPLE).nonOpaque()));


    public static final Block DEEPSLATE_PERLIUM_ORE = RegisterBlock("deepslate_perlium_ore"
            , new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.DIAMOND_ORE)
                    .mapColor(MapColor.PURPLE).sounds(BlockSoundGroup.DEEPSLATE), UniformIntProvider.create(3, 7)));
    public static final Block PERLIUM_ORE = RegisterBlock("perlium_ore"
            , new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.DIAMOND_ORE)
                    .mapColor(MapColor.PURPLE).sounds(BlockSoundGroup.STONE), UniformIntProvider.create(3, 7)));
    public static final Block PERLIUM_BLOCK = RegisterBlock("perlium_block", new Block(FabricBlockSettings.copyOf(Blocks.DIAMOND_ORE).mapColor(MapColor.PURPLE).sounds(BlockSoundGroup.METAL)));


    public static final Block DEEPSLATE_INFREX_ORE = RegisterBlock("deepslate_infrex_ore"
            , new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE_DIAMOND_ORE)
                    .mapColor(MapColor.PURPLE).sounds(BlockSoundGroup.DEEPSLATE), UniformIntProvider.create(3, 7)));
    public static final Block INFREX_ORE = RegisterBlock("infrex_ore"
            , new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE_DIAMOND_ORE)
                    .mapColor(MapColor.PURPLE).sounds(BlockSoundGroup.STONE), UniformIntProvider.create(3, 7)));
    public static final Block INFREX_BLOCK = RegisterBlock("infrex_block", new Block(FabricBlockSettings.copyOf(Blocks.DEEPSLATE_DIAMOND_ORE).mapColor(MapColor.PURPLE).sounds(BlockSoundGroup.METAL)));



    private static Block RegisterBlock(String name, Block block){
        RegisterBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(OresMod.MOD_ID, name), block);
    }

    private static Item RegisterBlockItem(String name, Block block){
        return Registry.register(Registries.ITEM, new Identifier(OresMod.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        OresMod.LOGGER.info("Registering Mod Blocks for " + OresMod.MOD_ID);
    }

}
