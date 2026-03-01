package cz.lukaskabc.minecraft.mod.stellarview.mixin_render.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.phys.Vec3;
import net.povstalec.stellarview.StellarView;
import net.povstalec.stellarview.client.render.ViewCenters;
import net.povstalec.stellarview.client.render.level.util.StellarViewLightmapEffects;
import net.povstalec.stellarview.compatibility.enhancedcelestials.EnhancedCelestialsCompatibility;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.function.Predicate;

/**
 * Delegates all calls to the original delegate.
 * Overrides {@link #adjustLightmapColors} and {@link #renderSky} to add custom StellarView behavior.
 */
public class DimensionEffectsDelegate extends DimensionSpecialEffects {
    private final DimensionSpecialEffects delegate;
    private final DimensionSpecialEffectsOverrides overrides;

    public DimensionEffectsDelegate(DimensionSpecialEffects delegate, DimensionSpecialEffectsOverrides overrides) {
        super(overrides.cloudHeight().orElseGet(delegate::getCloudHeight),
                overrides.hasGround().orElseGet(delegate::hasGround),
                overrides.skyType().orElseGet(delegate::skyType),
                overrides.forceBrightLightmap().orElseGet(delegate::forceBrightLightmap),
                overrides.constantAmbientLight().orElseGet(delegate::constantAmbientLight));
        this.delegate = delegate;
        this.overrides = overrides;
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 vec3, float v) {
        return this.delegate.getBrightnessDependentFogColor(vec3, v);
    }

    @Override
    public boolean isFoggyAt(int i, int i1) {
        if (overrides.disableFog()) {
            return false;
        }
        if (overrides.fogAlways()) {
            return true;
        }
        return this.delegate.isFoggyAt(i, i1);
    }

    @Override
    public void adjustLightmapColors(ClientLevel level, float partialTicks, float skyDarken, float blockLightRedFlicker, float skyLight, int pixelX, int pixelY, Vector3f colors) {
        StellarViewLightmapEffects.defaultLightmapColors(level, partialTicks, skyDarken, skyLight, blockLightRedFlicker, pixelX, pixelY, colors);

        if (StellarView.isEnhancedCelestialsLoaded())
            EnhancedCelestialsCompatibility.adjustLightmapColors(level, partialTicks, skyDarken, skyLight, blockLightRedFlicker, pixelX, pixelY, colors);
    }

    @Override
    public boolean tickRain(ClientLevel level, int ticks, Camera camera) {
        if (overrides.hasSnowAndRain()) {
            return delegate.tickRain(level, ticks, camera);
        }
        return false;
    }

    @Override
    public boolean renderSnowAndRain(ClientLevel level, int ticks, float partialTick, LightTexture lightTexture, double camX, double camY, double camZ) {
        if (overrides.hasSnowAndRain()) {
            return delegate.renderSnowAndRain(level, ticks, partialTick, lightTexture, camX, camY, camZ);
        }
        return false;
    }

    @Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, Matrix4f modelViewMatrix, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        return ViewCenters.renderViewCenterSky(level, ticks, partialTick, modelViewMatrix, camera, projectionMatrix, isFoggy, setupFog);
    }

    @Override
    public boolean renderClouds(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, double camX, double camY, double camZ, Matrix4f modelViewMatrix, Matrix4f projectionMatrix) {
        if (overrides.hasClouds()) {
            return delegate.renderClouds(level, ticks, partialTick, poseStack, camX, camY, camZ, modelViewMatrix, projectionMatrix);
        }
        return false;
    }

    @Override
    public @Nullable float[] getSunriseColor(float timeOfDay, float partialTicks) {
        if (overrides.hasSunrise()) {
            return delegate.getSunriseColor(timeOfDay, partialTicks);
        }
        return null;
    }
}
