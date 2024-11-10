package org.sam.oresmod.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import org.sam.oresmod.OresMod;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    PERLIUM("perlium", new int[] { 382, 555, 525, 452 }, new int[] { 4, 9, 6, 4 }, 19,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2F, 0.0F, () -> Ingredient.ofItems(ModItems.PERLIUM)),
    INFREX("infrex", new int[] { 502, 712, 672, 562 }, new int[] { 5, 12, 8, 5 }, 19,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2F, 0.0F, () -> Ingredient.ofItems(ModItems.INFREX)),
    DRAGON("dragon", new int[] { 1000, 1500, 1300, 1150 }, new int[] { 13, 22, 16, 12 }, 25,
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 4F, 1.0F, () -> Ingredient.ofItems(ModItems.DRAGONSCALE));

    private final String name;
    private final int[] specificDurability;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    ModArmorMaterials(String name, int[] specificDurability, int[] protectionAmounts,
                      int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance,
                      Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.specificDurability = specificDurability;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurability(ArmorItem.Type type) {
        return this.specificDurability[type.ordinal()];
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return protectionAmounts[type.ordinal()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return OresMod.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
