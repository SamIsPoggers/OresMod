package org.sam.oresmod.world;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import org.sam.oresmod.OresMod;
import org.sam.oresmod.block.ModBlocks;

import java.util.List;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> ULTRIS_ORE_KEY = registerKey("ultris_ore");

    public static final RegistryKey<ConfiguredFeature<?, ?>> PERLIUM_ORE_KEY = registerKey("perlium_ore");

    public static final RegistryKey<ConfiguredFeature<?, ?>> INFREX_ORE_KEY = registerKey("infrex_ore");

    public static void boostrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplacables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplacables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreFeatureConfig.Target> overworldUltrisOres =
                List.of(OreFeatureConfig.createTarget(stoneReplacables, ModBlocks.ULTRIS_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplacables, ModBlocks.DEEPSLATE_ULTRIS_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> overworldPerliumOres =
                List.of(OreFeatureConfig.createTarget(stoneReplacables, ModBlocks.PERLIUM_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplacables, ModBlocks.DEEPSLATE_PERLIUM_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> overworldInfrexOres =
                List.of(OreFeatureConfig.createTarget(stoneReplacables, ModBlocks.INFREX_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplacables, ModBlocks.DEEPSLATE_INFREX_ORE.getDefaultState()));

        register(context, ULTRIS_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldUltrisOres, 4));
        register(context, PERLIUM_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldPerliumOres, 6));
        register(context, INFREX_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldInfrexOres, 4));

    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(OresMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}
