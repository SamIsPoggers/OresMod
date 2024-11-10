package org.sam.oresmod.mixin;

import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.minecraft.item.ItemStack;
import org.sam.oresmod.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragonEntity.class)
public class EnderDragonEntityMixin {

    @Inject(method = "updatePostDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/boss/dragon/EnderDragonEntity;remove(Lnet/minecraft/entity/Entity$RemovalReason;)V"))
    private void injectOnDragonDeath(CallbackInfo ci) {
        // Make sure this is on the server side
        if (!((EnderDragonEntity) (Object) this).getWorld().isClient) {
            ServerWorld world = (ServerWorld) ((EnderDragonEntity) (Object) this).getWorld();
            Random random = world.getRandom();

            // Generate random amount between 1 and 2
            int amount = random.nextBetween(1, 2);

            // Create the ItemStack for ModItems.DRAGONSCALE with the amount
            ItemStack dragonScaleStack = new ItemStack(ModItems.DRAGONSCALE, amount);

            // Drop the ItemStack at the Ender Dragon's position
            ((EnderDragonEntity) (Object) this).dropStack(dragonScaleStack);
        }
    }
}
