package org.sam.oresmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import org.sam.oresmod.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.PERLIUM_HELMET, ModItems.PERLIUM_CHESTPLATE, ModItems.PERLIUM_LEGGINGS, ModItems.PERLIUM_BOOTS,
                        ModItems.INFREX_HELMET, ModItems.INFREX_CHESTPLATE, ModItems.INFREX_LEGGINGS, ModItems.INFREX_BOOTS,
                        ModItems.DRAGON_HELMET, ModItems.DRAGON_CHESTPLATE, ModItems.DRAGON_LEGGINGS, ModItems.DRAGON_BOOTS);
    }
}
