package org.sam.oresmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import org.sam.oresmod.block.ModBlocks;
import org.sam.oresmod.item.ModItems;

public class ModLootTableProvider extends FabricBlockLootTableProvider {

    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.NETHERITE_ANVIL);
        addDrop(ModBlocks.SUPER_ENCH_TABLE);
        addDrop(Blocks.BEDROCK);

        addDrop(ModBlocks.ULTRIS_BLOCK);
        addDrop(ModBlocks.ULTRIS_ORE, customOreDrops(ModBlocks.ULTRIS_ORE, ModItems.ULTRIS));
        addDrop(ModBlocks.DEEPSLATE_ULTRIS_ORE, customOreDrops(ModBlocks.DEEPSLATE_ULTRIS_ORE, ModItems.ULTRIS));

        addDrop(ModBlocks.PERLIUM_BLOCK);
        addDrop(ModBlocks.PERLIUM_ORE, customOreDrops(ModBlocks.PERLIUM_ORE, ModItems.PERLIUM));
        addDrop(ModBlocks.DEEPSLATE_PERLIUM_ORE, customOreDrops(ModBlocks.DEEPSLATE_PERLIUM_ORE, ModItems.PERLIUM));

        addDrop(ModBlocks.INFREX_BLOCK);
        addDrop(ModBlocks.INFREX_ORE, customOreDrops(ModBlocks.INFREX_ORE, ModItems.INFREX));
        addDrop(ModBlocks.DEEPSLATE_INFREX_ORE, customOreDrops(ModBlocks.DEEPSLATE_INFREX_ORE, ModItems.INFREX));
    }

    public LootTable.Builder customOreDrops(Block drop, Item item) {
        return dropsWithSilkTouch(drop, (LootPoolEntry.Builder)this.applyExplosionDecay(drop, ItemEntry.builder(item)
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 1.0F)))
                        .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
    }
}
