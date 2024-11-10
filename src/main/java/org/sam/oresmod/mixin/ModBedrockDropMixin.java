package org.sam.oresmod.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.sam.oresmod.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.mojang.text2speech.Narrator.LOGGER;

@Mixin(Block.class)
public abstract class ModBedrockDropMixin {

    @Inject(method = "onBreak", at = @At("HEAD"))
    private void onBedrockBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo ci) {
        // Check if the block is bedrock
        if (state.getBlock() == Blocks.BEDROCK) {
            ItemStack heldItem = player.getMainHandStack();

            // Check if the player is using the Dragon Pickaxe with Efficiency 7
            if (heldItem.getItem() == ModItems.DRAGON_PICKAXE &&
                    EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, heldItem) == 7) {

                // Spawn a bedrock item entity at the block position
                ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(Blocks.BEDROCK));
                world.spawnEntity(itemEntity);

                // Optional: log to confirm drop
                LOGGER.info("Bedrock item dropped at " + pos);
            }
        }
    }
}
