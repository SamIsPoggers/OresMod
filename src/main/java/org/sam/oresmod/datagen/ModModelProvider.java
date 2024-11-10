package org.sam.oresmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.ArmorItem;
import org.sam.oresmod.block.ModBlocks;
import org.sam.oresmod.item.ModItems;

public class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ULTRIS_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ULTRIS_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_ULTRIS_ORE);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PERLIUM_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PERLIUM_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_PERLIUM_ORE);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.INFREX_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.INFREX_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_INFREX_ORE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.ULTRIS, Models.GENERATED);
        itemModelGenerator.register(ModItems.PERLIUM, Models.GENERATED);
        itemModelGenerator.register(ModItems.INFREX, Models.GENERATED);
        itemModelGenerator.register(ModItems.DRAGONSCALE, Models.GENERATED);

        itemModelGenerator.register(ModItems.PERLIUM_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.PERLIUM_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.PERLIUM_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.PERLIUM_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.PERLIUM_SHOVEL, Models.HANDHELD);

        itemModelGenerator.register(ModItems.INFREX_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.INFREX_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.INFREX_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.INFREX_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.INFREX_SHOVEL, Models.HANDHELD);

        itemModelGenerator.register(ModItems.DRAGON_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.DRAGON_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.DRAGON_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.DRAGON_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.DRAGON_SHOVEL, Models.HANDHELD);

        itemModelGenerator.registerArmor(((ArmorItem) ModItems.PERLIUM_HELMET));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.PERLIUM_CHESTPLATE));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.PERLIUM_LEGGINGS));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.PERLIUM_BOOTS));

        itemModelGenerator.registerArmor(((ArmorItem) ModItems.INFREX_HELMET));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.INFREX_CHESTPLATE));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.INFREX_LEGGINGS));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.INFREX_BOOTS));

        itemModelGenerator.registerArmor(((ArmorItem) ModItems.DRAGON_HELMET));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.DRAGON_CHESTPLATE));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.DRAGON_LEGGINGS));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.DRAGON_BOOTS));
    }
}
