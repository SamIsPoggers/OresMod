package org.sam.oresmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.sam.oresmod.OresMod;

public class ModItems {

    public static final Item ULTRIS = registerItem("ultris", new Item(new FabricItemSettings()));

    public static final Item PERLIUM = registerItem("perlium", new Item(new FabricItemSettings()));

    public static final Item INFREX = registerItem("infrex", new Item(new FabricItemSettings()));

    public static final Item DRAGONSCALE = registerItem("dragonscale", new Item(new FabricItemSettings()));

    public static final Item PERLIUM_PICKAXE = registerItem("perlium_pickaxe",
            new PickaxeItem(ModToolMaterial.PERLIUM, 3, -2.8f, new FabricItemSettings()));
    public static final Item PERLIUM_AXE = registerItem("perlium_axe",
            new AxeItem(ModToolMaterial.PERLIUM, 7, -3.0f, new FabricItemSettings()));
    public static final Item PERLIUM_SHOVEL = registerItem("perlium_shovel",
            new ShovelItem(ModToolMaterial.PERLIUM, 3.5f, -3.0f, new FabricItemSettings()));
    public static final Item PERLIUM_HOE = registerItem("perlium_hoe",
            new HoeItem(ModToolMaterial.PERLIUM, -2, 0, new FabricItemSettings()));
    public static final Item PERLIUM_SWORD = registerItem("perlium_sword",
            new SwordItem(ModToolMaterial.PERLIUM, 5, -2.4f, new FabricItemSettings()));

    public static final Item INFREX_PICKAXE = registerItem("infrex_pickaxe",
            new PickaxeItem(ModToolMaterial.INFREX, 7, -2.8f, new FabricItemSettings()));
    public static final Item INFREX_AXE = registerItem("infrex_axe",
            new AxeItem(ModToolMaterial.INFREX, 12.0F, -3.0f, new FabricItemSettings()));
    public static final Item INFREX_SHOVEL = registerItem("infrex_shovel",
            new ShovelItem(ModToolMaterial.INFREX, 4.5f, -3.0f, new FabricItemSettings()));
    public static final Item INFREX_HOE = registerItem("infrex_hoe",
            new HoeItem(ModToolMaterial.INFREX, 0, 0, new FabricItemSettings()));
    public static final Item INFREX_SWORD = registerItem("infrex_sword",
            new SwordItem(ModToolMaterial.INFREX, 6, -2.4f, new FabricItemSettings()));


    public static final Item DRAGON_PICKAXE = registerItem("dragon_pickaxe",
            new PickaxeItem(ModToolMaterial.DRAGON, 9, -2.8f, new FabricItemSettings()));
    public static final Item DRAGON_AXE = registerItem("dragon_axe",
            new AxeItem(ModToolMaterial.DRAGON, 9.0F, -3.0f, new FabricItemSettings()));
    public static final Item DRAGON_SHOVEL = registerItem("dragon_shovel",
            new ShovelItem(ModToolMaterial.DRAGON, 6f, -3.0f, new FabricItemSettings()));
    public static final Item DRAGON_HOE = registerItem("dragon_hoe",
            new HoeItem(ModToolMaterial.DRAGON, 1, 0, new FabricItemSettings()));
    public static final Item DRAGON_SWORD = registerItem("dragon_sword",
            new SwordItem(ModToolMaterial.DRAGON, 10, -2.4f, new FabricItemSettings()));



    public static final Item PERLIUM_HELMET = registerItem("perlium_helmet",
            new ArmorItem(ModArmorMaterials.PERLIUM, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item PERLIUM_CHESTPLATE = registerItem("perlium_chestplate",
            new ArmorItem(ModArmorMaterials.PERLIUM, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item PERLIUM_LEGGINGS = registerItem("perlium_leggings",
            new ArmorItem(ModArmorMaterials.PERLIUM, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item PERLIUM_BOOTS = registerItem("perlium_boots",
            new ArmorItem(ModArmorMaterials.PERLIUM, ArmorItem.Type.BOOTS, new FabricItemSettings()));


    public static final Item INFREX_HELMET = registerItem("infrex_helmet",
            new ArmorItem(ModArmorMaterials.INFREX, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item INFREX_CHESTPLATE = registerItem("infrex_chestplate",
            new ArmorItem(ModArmorMaterials.INFREX, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item INFREX_LEGGINGS = registerItem("infrex_leggings",
            new ArmorItem(ModArmorMaterials.INFREX, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item INFREX_BOOTS = registerItem("infrex_boots",
            new ArmorItem(ModArmorMaterials.INFREX, ArmorItem.Type.BOOTS, new FabricItemSettings()));


    public static final Item DRAGON_HELMET = registerItem("dragon_helmet",
            new ArmorItem(ModArmorMaterials.DRAGON, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item DRAGON_CHESTPLATE = registerItem("dragon_chestplate",
            new ArmorItem(ModArmorMaterials.DRAGON, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item DRAGON_LEGGINGS = registerItem("dragon_leggings",
            new ArmorItem(ModArmorMaterials.DRAGON, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item DRAGON_BOOTS = registerItem("dragon_boots",
            new ArmorItem(ModArmorMaterials.DRAGON, ArmorItem.Type.BOOTS, new FabricItemSettings()));




    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries){
        entries.add(ULTRIS);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(OresMod.MOD_ID, name), item);
    }

    public static void registerModItems(){
        OresMod.LOGGER.info("Registering Mod Items for " + OresMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }

}
