package org.sam.oresmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.sam.oresmod.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.ULTRIS_BLOCK)
                .add(ModBlocks.ULTRIS_ORE)
                .add(ModBlocks.DEEPSLATE_ULTRIS_ORE)
                .add(ModBlocks.PERLIUM_BLOCK)
                .add(ModBlocks.PERLIUM_ORE)
                .add(ModBlocks.DEEPSLATE_PERLIUM_ORE)
                .add(ModBlocks.INFREX_BLOCK)
                .add(ModBlocks.INFREX_ORE)
                .add(ModBlocks.DEEPSLATE_INFREX_ORE)
                .add(ModBlocks.NETHERITE_ANVIL)
                .add(ModBlocks.SUPER_ENCH_TABLE);

        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL);

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.PERLIUM_BLOCK)
                .add(ModBlocks.PERLIUM_ORE)
                .add(ModBlocks.DEEPSLATE_PERLIUM_ORE)
                .add(ModBlocks.SUPER_ENCH_TABLE);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL);

        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("fabric", "needs_tool_level_4")))
                .add(ModBlocks.ULTRIS_BLOCK)
                .add(ModBlocks.ULTRIS_ORE)
                .add(ModBlocks.DEEPSLATE_ULTRIS_ORE)
                .add(ModBlocks.INFREX_BLOCK)
                .add(ModBlocks.INFREX_ORE)
                .add(ModBlocks.DEEPSLATE_INFREX_ORE)
                .add(ModBlocks.NETHERITE_ANVIL);
    }
}
