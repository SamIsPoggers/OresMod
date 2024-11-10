package org.sam.oresmod;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import org.sam.oresmod.item.ModItems;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InstamineLogic {
    private static final Logger LOGGER = LogManager.getLogger("InstamineLogic");

    public static float calculateInstamineSpeed(BlockState blockState, PlayerEntity player) {
        ItemStack itemStack = player.getMainHandStack();
        Item item = itemStack.getItem();

        // Log the current item being used
        LOGGER.info("Current item: " + item);

        // Check if the item is ModItems.DRAGON_PICKAXE and is a tool item (like a pickaxe)
        if (item == ModItems.DRAGON_PICKAXE && item instanceof ToolItem) {
            int efficiencyLevel = EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, itemStack);
            LOGGER.info("Efficiency level: " + efficiencyLevel);

            // Check for Deepslate
            if (efficiencyLevel >= 6 && blockState.getBlock() == Blocks.DEEPSLATE) {
                float instamineSpeed = 125; // Increase speed for instamine
                LOGGER.info("Instamine successful for Deepslate! Speed: " + instamineSpeed);
                return instamineSpeed; // Return the increased mining speed for Deepslate
            }

            // Check for Obsidian
            if (efficiencyLevel >= 7 && blockState.getBlock() == Blocks.OBSIDIAN) {
                float instamineSpeed = 3500; // Increase speed for instamine
                LOGGER.info("Instamine successful for Obsidian! Speed: " + instamineSpeed);
                return instamineSpeed; // Return the increased mining speed for Obsidian
            }


            // Check for Other than obsidian
            if (efficiencyLevel >= 7 && blockState.getBlock() != Blocks.BEDROCK && blockState.isIn(BlockTags.PICKAXE_MINEABLE)) {
                float instamineSpeed = 3500; // Increase speed for instamine
                LOGGER.info("Instamine successful for the block! Speed: " + instamineSpeed);
                return instamineSpeed; // Return the increased mining speed for Deepslate
            }
        } else {
            LOGGER.info("Item is not the Dragon Pickaxe: " + item);
        }

        // Log if no instamine conditions are met
        LOGGER.info("No instamine conditions met for block: " + blockState.getBlock());
        return -1.0f; // Return -1.0 if instamine conditions are not met
    }
}
