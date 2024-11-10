package org.sam.oresmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import org.sam.oresmod.block.ModBlocks;
import org.sam.oresmod.item.ModItems;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {

    private static List<ItemConvertible> ULTRIS_SMELTABLES = List.of(ModBlocks.ULTRIS_ORE,
            ModBlocks.DEEPSLATE_ULTRIS_ORE);

    private static List<ItemConvertible> PERLIUM_SMELTABLES = List.of(ModBlocks.PERLIUM_ORE,
            ModBlocks.DEEPSLATE_PERLIUM_ORE);

    private static List<ItemConvertible> INFREX_SMELTABLES = List.of(ModBlocks.INFREX_ORE,
            ModBlocks.DEEPSLATE_INFREX_ORE);

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerSmelting(exporter, ULTRIS_SMELTABLES, RecipeCategory.MISC,
                ModItems.ULTRIS, 0.7f, 200, "Ultris");
        offerBlasting(exporter, ULTRIS_SMELTABLES, RecipeCategory.MISC,
                ModItems.ULTRIS, 0.7f, 100, "Ultris");
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.ULTRIS,
                RecipeCategory.DECORATIONS, ModBlocks.ULTRIS_BLOCK);


        offerSmelting(exporter, PERLIUM_SMELTABLES, RecipeCategory.MISC,
                ModItems.PERLIUM, 0.7f, 200, "Perlium");
        offerBlasting(exporter, PERLIUM_SMELTABLES, RecipeCategory.MISC,
                ModItems.PERLIUM, 0.7f, 100, "Perlium");
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.PERLIUM,
                RecipeCategory.DECORATIONS, ModBlocks.PERLIUM_BLOCK);

        offerSmelting(exporter, INFREX_SMELTABLES, RecipeCategory.MISC,
                ModItems.INFREX, 0.7f, 200, "Infrex");
        offerBlasting(exporter, INFREX_SMELTABLES, RecipeCategory.MISC,
                ModItems.INFREX, 0.7f, 100, "Infrex");
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.INFREX,
                RecipeCategory.DECORATIONS, ModBlocks.INFREX_BLOCK);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.PERLIUM_SWORD)
                .pattern(" P ")
                .pattern(" P ")
                .pattern(" S ")
                .input('P', ModItems.PERLIUM)
                .input('S', Items.STICK)
                .criterion(hasItem(ModItems.PERLIUM), conditionsFromItem(ModItems.PERLIUM))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.PERLIUM_SWORD)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.PERLIUM_AXE)
                .pattern(" PP")
                .pattern(" SP")
                .pattern(" S ")
                .input('P', ModItems.PERLIUM)
                .input('S', Items.STICK)
                .criterion(hasItem(ModItems.PERLIUM), conditionsFromItem(ModItems.PERLIUM))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.PERLIUM_AXE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.PERLIUM_PICKAXE)
                .pattern("PPP")
                .pattern(" S ")
                .pattern(" S ")
                .input('P', ModItems.PERLIUM)
                .input('S', Items.STICK)
                .criterion(hasItem(ModItems.PERLIUM), conditionsFromItem(ModItems.PERLIUM))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.PERLIUM_PICKAXE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.PERLIUM_SHOVEL)
                .pattern(" P ")
                .pattern(" S ")
                .pattern(" S ")
                .input('P', ModItems.PERLIUM)
                .input('S', Items.STICK)
                .criterion(hasItem(ModItems.PERLIUM), conditionsFromItem(ModItems.PERLIUM))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.PERLIUM_SHOVEL)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.PERLIUM_HOE)
                .pattern(" PP")
                .pattern(" S ")
                .pattern(" S ")
                .input('P', ModItems.PERLIUM)
                .input('S', Items.STICK)
                .criterion(hasItem(ModItems.PERLIUM), conditionsFromItem(ModItems.PERLIUM))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.PERLIUM_HOE)));



        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.INFREX_SWORD)
                .pattern(" I ")
                .pattern(" I ")
                .pattern(" S ")
                .input('I', ModItems.INFREX)
                .input('S', Items.STICK)
                .criterion(hasItem(ModItems.INFREX), conditionsFromItem(ModItems.INFREX))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.INFREX_SWORD)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.INFREX_AXE)
                .pattern(" II")
                .pattern(" SI")
                .pattern(" S ")
                .input('I', ModItems.INFREX)
                .input('S', Items.STICK)
                .criterion(hasItem(ModItems.INFREX), conditionsFromItem(ModItems.INFREX))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.INFREX_AXE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.INFREX_PICKAXE)
                .pattern("III")
                .pattern(" S ")
                .pattern(" S ")
                .input('I', ModItems.INFREX)
                .input('S', Items.STICK)
                .criterion(hasItem(ModItems.INFREX), conditionsFromItem(ModItems.INFREX))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.INFREX_PICKAXE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.INFREX_SHOVEL)
                .pattern(" I ")
                .pattern(" S ")
                .pattern(" S ")
                .input('I', ModItems.INFREX)
                .input('S', Items.STICK)
                .criterion(hasItem(ModItems.INFREX), conditionsFromItem(ModItems.INFREX))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.INFREX_SHOVEL)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.INFREX_HOE)
                .pattern(" II")
                .pattern(" S ")
                .pattern(" S ")
                .input('I', ModItems.INFREX)
                .input('S', Items.STICK)
                .criterion(hasItem(ModItems.INFREX), conditionsFromItem(ModItems.INFREX))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.INFREX_HOE)));


        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.PERLIUM_HELMET)
                .pattern("PPP")
                .pattern("P P")
                .pattern("   ")
                .input('P', ModItems.PERLIUM)
                .criterion(hasItem(ModItems.PERLIUM), conditionsFromItem(ModItems.PERLIUM))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.PERLIUM_HELMET)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.PERLIUM_CHESTPLATE)
                .pattern("P P")
                .pattern("PPP")
                .pattern("PPP")
                .input('P', ModItems.PERLIUM)
                .criterion(hasItem(ModItems.PERLIUM), conditionsFromItem(ModItems.PERLIUM))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.PERLIUM_CHESTPLATE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.PERLIUM_LEGGINGS)
                .pattern("PPP")
                .pattern("P P")
                .pattern("P P")
                .input('P', ModItems.PERLIUM)
                .criterion(hasItem(ModItems.PERLIUM), conditionsFromItem(ModItems.PERLIUM))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.PERLIUM_LEGGINGS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.PERLIUM_BOOTS)
                .pattern("   ")
                .pattern("P P")
                .pattern("P P")
                .input('P', ModItems.PERLIUM)
                .criterion(hasItem(ModItems.PERLIUM), conditionsFromItem(ModItems.PERLIUM))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.PERLIUM_BOOTS)));


        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.INFREX_HELMET)
                .pattern("III")
                .pattern("I I")
                .pattern("   ")
                .input('I', ModItems.INFREX)
                .criterion(hasItem(ModItems.INFREX), conditionsFromItem(ModItems.INFREX))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.INFREX_HELMET)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.INFREX_CHESTPLATE)
                .pattern("I I")
                .pattern("III")
                .pattern("III")
                .input('I', ModItems.INFREX)
                .criterion(hasItem(ModItems.INFREX), conditionsFromItem(ModItems.INFREX))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.INFREX_CHESTPLATE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.INFREX_LEGGINGS)
                .pattern("III")
                .pattern("I I")
                .pattern("I I")
                .input('I', ModItems.INFREX)
                .criterion(hasItem(ModItems.INFREX), conditionsFromItem(ModItems.INFREX))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.INFREX_LEGGINGS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.INFREX_BOOTS)
                .pattern("   ")
                .pattern("I I")
                .pattern("I I")
                .input('I', ModItems.INFREX)
                .criterion(hasItem(ModItems.INFREX), conditionsFromItem(ModItems.INFREX))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.INFREX_BOOTS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.NETHERITE_ANVIL)
                .pattern("BBB")
                .pattern(" U ")
                .pattern("III")
                .input('B', Items.NETHERITE_BLOCK)
                .input('I', Items.NETHERITE_INGOT)
                .input('U', ModBlocks.ULTRIS_BLOCK)
                .criterion(hasItem(Items.NETHERITE_INGOT), conditionsFromItem(Items.NETHERITE_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.NETHERITE_ANVIL)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.SUPER_ENCH_TABLE)
                .pattern(" D ")
                .pattern("UPU")
                .pattern("PPP")
                .input('D', ModItems.DRAGONSCALE)
                .input('U', ModItems.ULTRIS)
                .input('P', ModBlocks.PERLIUM_BLOCK)
                .criterion(hasItem(ModItems.ULTRIS), conditionsFromItem(ModItems.ULTRIS))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.SUPER_ENCH_TABLE)));

        offerSmithingUpgrade(exporter, ModItems.INFREX_HELMET,
                ModItems.DRAGONSCALE, RecipeCategory.MISC, ModItems.DRAGON_HELMET);
        offerSmithingUpgrade(exporter, ModItems.INFREX_CHESTPLATE,
                ModItems.DRAGONSCALE, RecipeCategory.MISC, ModItems.DRAGON_CHESTPLATE);
        offerSmithingUpgrade(exporter, ModItems.INFREX_LEGGINGS,
                ModItems.DRAGONSCALE, RecipeCategory.MISC, ModItems.DRAGON_LEGGINGS);
        offerSmithingUpgrade(exporter, ModItems.INFREX_BOOTS,
                ModItems.DRAGONSCALE, RecipeCategory.MISC, ModItems.DRAGON_BOOTS);

        offerSmithingUpgrade(exporter, ModItems.INFREX_SWORD,
                ModItems.DRAGONSCALE, RecipeCategory.MISC, ModItems.DRAGON_SWORD);
        offerSmithingUpgrade(exporter, ModItems.INFREX_AXE,
                ModItems.DRAGONSCALE, RecipeCategory.MISC, ModItems.DRAGON_AXE);
        offerSmithingUpgrade(exporter, ModItems.INFREX_PICKAXE,
                ModItems.DRAGONSCALE, RecipeCategory.MISC, ModItems.DRAGON_PICKAXE);
        offerSmithingUpgrade(exporter, ModItems.INFREX_SHOVEL,
                ModItems.DRAGONSCALE, RecipeCategory.MISC, ModItems.DRAGON_SHOVEL);
        offerSmithingUpgrade(exporter, ModItems.INFREX_HOE,
                ModItems.DRAGONSCALE, RecipeCategory.MISC, ModItems.DRAGON_HOE);
    }

    public static void offerSmithingUpgrade(Consumer<RecipeJsonProvider> exporter, Item input, Item upgradeMaterial, RecipeCategory category, Item result) {
        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(new ItemConvertible[]{Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE}), Ingredient.ofItems(new ItemConvertible[]{input}), Ingredient.ofItems(new ItemConvertible[]{upgradeMaterial}), category, result).criterion("has_netherite_ingot", conditionsFromItem(Items.NETHERITE_INGOT)).offerTo(exporter, getItemPath(result) + "_smithing");
    }
}
