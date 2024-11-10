package org.sam.oresmod.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.sam.oresmod.InstamineLogic;
import org.sam.oresmod.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerMixin {
    @Inject(at = @At("HEAD"), cancellable = true, method = "getBlockBreakingSpeed")
    private void getBlockBreakingSpeed(BlockState blockState, CallbackInfoReturnable<Float> cir) {
        // Call the instamine logic to determine if we should override the mining speed
        float instamineSpeed = InstamineLogic.calculateInstamineSpeed(blockState, (PlayerEntity) (Object) this);

        if (instamineSpeed != -1.0f) {
            cir.setReturnValue(instamineSpeed); // Override the mining speed if instamine conditions are met
        }
    }
}
