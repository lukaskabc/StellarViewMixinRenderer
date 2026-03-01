package cz.lukaskabc.minecraft.mod.stellarview.mixin_render.client;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.Objects;

public class DimensionSpecialEffectsRegistry {
    private static Map<ResourceLocation, DimensionSpecialEffectsOverrides> overrides;

    private DimensionSpecialEffectsRegistry() {
        throw new AssertionError();
    }

    public static void set(Map<ResourceLocation, DimensionSpecialEffectsOverrides> overrides) {
        DimensionSpecialEffectsRegistry.overrides = Objects.requireNonNull(overrides);
    }

    public static DimensionSpecialEffectsOverrides getOrDefault(ResourceLocation location) {
        DimensionSpecialEffectsOverrides value = overrides.get(location);
        if (value != null) {
            return value;
        }
        return DimensionSpecialEffectsOverrides.WITH_DEFAULTS;
    }

}
