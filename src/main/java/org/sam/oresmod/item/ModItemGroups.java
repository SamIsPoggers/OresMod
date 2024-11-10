package org.sam.oresmod.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.sam.oresmod.OresMod;
import org.sam.oresmod.block.ModBlocks;

public class ModItemGroups {

    public static final ItemGroup ULTRIS_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(OresMod.MOD_ID,
            "ultris"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.ultris"))
                    .icon(() -> new ItemStack(ModItems.ULTRIS)).entries((displayContext, entries) -> {

                        entries.add(ModItems.ULTRIS);
                        entries.add(ModBlocks.ULTRIS_ORE);
                        entries.add(ModBlocks.DEEPSLATE_ULTRIS_ORE);
                        entries.add(ModBlocks.ULTRIS_BLOCK);
                    }).build());


    public static final ItemGroup PERLIUM_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(OresMod.MOD_ID,
                    "perlium"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.perlium"))
                    .icon(() -> new ItemStack(ModItems.PERLIUM)).entries((displayContext, entries) -> {

                        entries.add(ModItems.PERLIUM);
                        entries.add(ModBlocks.PERLIUM_ORE);
                        entries.add(ModBlocks.DEEPSLATE_PERLIUM_ORE);
                        entries.add(ModBlocks.PERLIUM_BLOCK);
                        entries.add(ModItems.PERLIUM_SWORD);
                        entries.add(ModItems.PERLIUM_AXE);
                        entries.add(ModItems.PERLIUM_PICKAXE);
                        entries.add(ModItems.PERLIUM_SHOVEL);
                        entries.add(ModItems.PERLIUM_HOE);
                        entries.add(ModItems.PERLIUM_HELMET);
                        entries.add(ModItems.PERLIUM_CHESTPLATE);
                        entries.add(ModItems.PERLIUM_LEGGINGS);
                        entries.add(ModItems.PERLIUM_BOOTS);
                    }).build());

    public static final ItemGroup INFREX_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(OresMod.MOD_ID,
                    "infrex"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.infrex"))
                    .icon(() -> new ItemStack(ModItems.INFREX)).entries((displayContext, entries) -> {

                        entries.add(ModItems.INFREX);
                        entries.add(ModBlocks.INFREX_ORE);
                        entries.add(ModBlocks.DEEPSLATE_INFREX_ORE);
                        entries.add(ModBlocks.INFREX_BLOCK);
                        entries.add(ModItems.INFREX_SWORD);
                        entries.add(ModItems.INFREX_AXE);
                        entries.add(ModItems.INFREX_PICKAXE);
                        entries.add(ModItems.INFREX_SHOVEL);
                        entries.add(ModItems.INFREX_HOE);
                        entries.add(ModItems.INFREX_HELMET);
                        entries.add(ModItems.INFREX_CHESTPLATE);
                        entries.add(ModItems.INFREX_LEGGINGS);
                        entries.add(ModItems.INFREX_BOOTS);
                    }).build());

    public static final ItemGroup DRAGONSCALE_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(OresMod.MOD_ID,
                    "dragonscale"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.dragonscale"))
                    .icon(() -> new ItemStack(ModItems.DRAGONSCALE)).entries((displayContext, entries) -> {

                        entries.add(ModItems.DRAGONSCALE);
                        entries.add(ModItems.DRAGON_HELMET);
                        entries.add(ModItems.DRAGON_CHESTPLATE);
                        entries.add(ModItems.DRAGON_LEGGINGS);
                        entries.add(ModItems.DRAGON_BOOTS);
                        entries.add(ModItems.DRAGON_SWORD);
                        entries.add(ModItems.DRAGON_AXE);
                        entries.add(ModItems.DRAGON_PICKAXE);
                        entries.add(ModItems.DRAGON_SHOVEL);
                        entries.add(ModItems.DRAGON_HOE);
                        entries.add(ModBlocks.NETHERITE_ANVIL);
                        entries.add(ModBlocks.SUPER_ENCH_TABLE);
                    }).build());

    public static void registerItemGroups(){
        OresMod.LOGGER.info("Registering Item Groups for " + OresMod.MOD_ID);
    }
}
