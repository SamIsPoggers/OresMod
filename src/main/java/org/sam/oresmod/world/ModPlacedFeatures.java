package org.sam.oresmod.world;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import org.sam.oresmod.OresMod;

import java.util.List;

public class ModPlacedFeatures {

    public static final RegistryKey<PlacedFeature> ULTRIS_ORE_PLACED_KEY = registerKey("ultris_ore_placed");

    public static final RegistryKey<PlacedFeature> PERLIUM_ORE_PLACED_KEY = registerKey("perlium_ore_placed");

    public static final RegistryKey<PlacedFeature> INFREX_ORE_PLACED_KEY = registerKey("infrex_ore_placed");

    public static void boostrap(Registerable<PlacedFeature> context) {

        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, ULTRIS_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.ULTRIS_ORE_KEY),
                ModOrePlacement.modifiersWithCount(1, //veins per chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-512), YOffset.fixed(0))));

        register(context, PERLIUM_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.PERLIUM_ORE_KEY),
                ModOrePlacement.modifiersWithCount(6, //veins per chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-512), YOffset.fixed(0))));

        register(context, INFREX_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.INFREX_ORE_KEY),
                ModOrePlacement.modifiersWithCount(1, //veins per chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-512), YOffset.fixed(0))));

    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(OresMod.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

}
