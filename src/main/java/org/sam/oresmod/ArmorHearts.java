package org.sam.oresmod;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.sam.oresmod.item.ModItems;

import java.util.UUID;

public class ArmorHearts {
    // Unique UUIDs for each piece of armor
    private static final UUID DRAGON_HELMET_UUID = UUID.fromString("a1f1c6c8-8d0f-4a28-91b8-d5fc2c18bb9f");
    private static final UUID DRAGON_CHESTPLATE_UUID = UUID.fromString("2b5e7c4c-9e2f-4d98-8efc-8f5e5c4dbe98");
    private static final UUID DRAGON_LEGGINGS_UUID = UUID.fromString("3e4c81e6-a4df-4a27-b81e-28bb98f4c2c7");
    private static final UUID DRAGON_BOOTS_UUID = UUID.fromString("4f7e8e9a-1f3d-4b39-95df-b8c0f1e8b717");
    private static final UUID INFREX_HELMET_UUID = UUID.fromString("5a9b8e8a-7e5c-4a9f-b5e9-f8b7c7d6b4c6");
    private static final UUID INFREX_CHESTPLATE_UUID = UUID.fromString("6b8f7d9c-3a1e-4c7d-b4f7-9f8c5e1b6d5a");
    private static final UUID INFREX_LEGGINGS_UUID = UUID.fromString("7d9e8c6b-9f1d-4a8c-a3b6-d7e9f5c4a3c8");
    private static final UUID INFREX_BOOTS_UUID = UUID.fromString("8e7b6c5d-4f9c-4e7b-b1a5-e6f4d8c3b5b7");

    // Method to get the attribute modifier based on armor piece
    private static EntityAttributeModifier getModifier(Item armorPiece) {
        if (armorPiece == ModItems.DRAGON_HELMET) return new EntityAttributeModifier(DRAGON_HELMET_UUID, "Dragon Helmet Health", 2.0, EntityAttributeModifier.Operation.ADDITION);
        if (armorPiece == ModItems.DRAGON_CHESTPLATE) return new EntityAttributeModifier(DRAGON_CHESTPLATE_UUID, "Dragon Chestplate Health", 8.0, EntityAttributeModifier.Operation.ADDITION);
        if (armorPiece == ModItems.DRAGON_LEGGINGS) return new EntityAttributeModifier(DRAGON_LEGGINGS_UUID, "Dragon Leggings Health", 6.0, EntityAttributeModifier.Operation.ADDITION);
        if (armorPiece == ModItems.DRAGON_BOOTS) return new EntityAttributeModifier(DRAGON_BOOTS_UUID, "Dragon Boots Health", 4.0, EntityAttributeModifier.Operation.ADDITION);
        if (armorPiece == ModItems.INFREX_HELMET) return new EntityAttributeModifier(INFREX_HELMET_UUID, "Infrex Helmet Health", 1.0, EntityAttributeModifier.Operation.ADDITION);
        if (armorPiece == ModItems.INFREX_CHESTPLATE) return new EntityAttributeModifier(INFREX_CHESTPLATE_UUID, "Infrex Chestplate Health", 4.0, EntityAttributeModifier.Operation.ADDITION);
        if (armorPiece == ModItems.INFREX_LEGGINGS) return new EntityAttributeModifier(INFREX_LEGGINGS_UUID, "Infrex Leggings Health", 3.0, EntityAttributeModifier.Operation.ADDITION);
        if (armorPiece == ModItems.INFREX_BOOTS) return new EntityAttributeModifier(INFREX_BOOTS_UUID, "Infrex Boots Health", 2.0, EntityAttributeModifier.Operation.ADDITION);
        return null;
    }

    // Method to add the attribute modifier
    private static void applyModifier(PlayerEntity player, EntityAttributeModifier modifier) {
        if (modifier != null && !player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).hasModifier(modifier)) {
            player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addPersistentModifier(modifier);
        }
    }

    // Method to remove the attribute modifier
    private static void removeModifier(PlayerEntity player, EntityAttributeModifier modifier) {
        if (modifier != null && player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).hasModifier(modifier)) {
            player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).removeModifier(modifier.getId());
        }
    }

    // Main registration method
    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof PlayerEntity player) {
                updatePlayerHearts(player);
            }
        });

        ServerEntityEvents.EQUIPMENT_CHANGE.register((entity, slot, from, to) -> {
            if (entity instanceof PlayerEntity player) {
                EntityAttributeModifier oldModifier = getModifier(from.getItem());
                EntityAttributeModifier newModifier = getModifier(to.getItem());

                // Remove old modifier and apply new one if they differ
                if (oldModifier != null) removeModifier(player, oldModifier);
                if (newModifier != null) applyModifier(player, newModifier);
            }
        });
    }

    // Update method to check all equipped items and adjust hearts on player
    private static void updatePlayerHearts(PlayerEntity player) {
        DefaultedList<ItemStack> armorItems = player.getInventory().armor;

        for (ItemStack armorItem : armorItems) {
            EntityAttributeModifier modifier = getModifier(armorItem.getItem());
            if (modifier != null) {
                applyModifier(player, modifier);
            }
        }
    }
}
