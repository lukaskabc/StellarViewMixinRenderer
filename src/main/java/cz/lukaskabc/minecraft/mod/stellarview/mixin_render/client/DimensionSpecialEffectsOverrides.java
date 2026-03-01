package cz.lukaskabc.minecraft.mod.stellarview.mixin_render.client;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.DimensionSpecialEffects;

import java.util.Optional;

public record DimensionSpecialEffectsOverrides(
        boolean disableOverride,
        Optional<Float> cloudHeight,
        Optional<Boolean> hasGround,
        Optional<DimensionSpecialEffects.SkyType> skyType,
        Optional<Boolean> forceBrightLightmap,
        Optional<Boolean> constantAmbientLight,
        boolean hasSnowAndRain,
        boolean disableFog,
        boolean fogAlways,
        boolean hasSunrise,
        boolean hasClouds
) {

    public static final Codec<DimensionSpecialEffects.SkyType> SKY_TYPE_CODEC = Codec.STRING.xmap(
            s -> DimensionSpecialEffects.SkyType.valueOf(s.toUpperCase()),
            type -> type.name().toLowerCase()
    );

    public static final Codec<DimensionSpecialEffectsOverrides> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("disable_override", false).forGetter(DimensionSpecialEffectsOverrides::disableOverride),
            Codec.FLOAT.optionalFieldOf("cloud_height").forGetter(DimensionSpecialEffectsOverrides::cloudHeight),
            Codec.BOOL.optionalFieldOf("has_ground").forGetter(DimensionSpecialEffectsOverrides::hasGround),
            SKY_TYPE_CODEC.optionalFieldOf("sky_type").forGetter(DimensionSpecialEffectsOverrides::skyType),
            Codec.BOOL.optionalFieldOf("force_bright_lightmap").forGetter(DimensionSpecialEffectsOverrides::forceBrightLightmap),
            Codec.BOOL.optionalFieldOf("constant_ambient_light").forGetter(DimensionSpecialEffectsOverrides::constantAmbientLight),
            Codec.BOOL.optionalFieldOf("has_snow_and_rain", true).forGetter(DimensionSpecialEffectsOverrides::hasSnowAndRain),
            Codec.BOOL.optionalFieldOf("disable_fog", false).forGetter(DimensionSpecialEffectsOverrides::disableFog),
            Codec.BOOL.optionalFieldOf("fog_always", false).forGetter(DimensionSpecialEffectsOverrides::fogAlways),
            Codec.BOOL.optionalFieldOf("has_sunrise", true).forGetter(DimensionSpecialEffectsOverrides::hasSunrise),
            Codec.BOOL.optionalFieldOf("has_clouds", true).forGetter(DimensionSpecialEffectsOverrides::hasClouds)
    ).apply(instance, DimensionSpecialEffectsOverrides::new));

    public static final DimensionSpecialEffectsOverrides WITH_DEFAULTS = CODEC.parse(JsonOps.COMPRESSED, new JsonObject()).getOrThrow();
}