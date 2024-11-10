package org.sam.oresmod.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.objectweb.asm.Opcodes;
import org.sam.oresmod.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static org.sam.oresmod.OresMod.MOD_ID;

@Mixin(AbstractBlock.class)
abstract class ModBedrockMixin {
    @Unique
    private long miningStartTime = -1;

    @Unique
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Inject(at = @At(value = "JUMP", opcode = Opcodes.IFNE, shift = At.Shift.AFTER),
            method = "calcBlockBreakingDelta",
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILSOFT)
    public void allowBedrockBreaking(BlockState state, PlayerEntity player, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        if (player.getMainHandStack().getItem().equals(ModItems.DRAGON_PICKAXE) && EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, player.getMainHandStack()) == 7) {
            if (state.equals(Blocks.BEDROCK.getDefaultState())) {
                // Start the timer when the player starts mining bedrock
                if (miningStartTime == -1) {
                    miningStartTime = System.currentTimeMillis();
                }

                long elapsedTime = System.currentTimeMillis() - miningStartTime;

                // If 10 seconds have passed, allow the block to break
                // Gradual break
                cir.setReturnValue(0.995f * 0.01f);  // Instant break after 10 seconds
            }
        }
    }
}