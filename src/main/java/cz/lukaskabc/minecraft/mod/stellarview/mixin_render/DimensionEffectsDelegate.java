package cz.lukaskabc.minecraft.mod.stellarview.mixin_render;

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

/**
 * Delegates all calls to the original delegate.
 * Overrides {@link #adjustLightmapColors} and {@link #renderSky} to add custom StellarView behavior.
 */
public class DimensionEffectsDelegate extends DimensionSpecialEffects {
    private final DimensionSpecialEffects delegate;

    public DimensionEffectsDelegate(DimensionSpecialEffects delegate) {
        // calls delegate so it should not matter but whatever
        super(delegate.getCloudHeight(), delegate.hasGround(), delegate.skyType(), delegate.forceBrightLightmap(), delegate.constantAmbientLight());
        this.delegate = delegate;
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 vec3, float v) {
        return this.delegate.getBrightnessDependentFogColor(vec3, v);
    }

    @Override
    public boolean isFoggyAt(int i, int i1) {
        return this.delegate.isFoggyAt(i, i1);
    }

    @Override
    public void adjustLightmapColors(ClientLevel level, float partialTicks, float skyDarken, float blockLightRedFlicker, float skyLight, int pixelX, int pixelY, Vector3f colors) {
        StellarViewLightmapEffects.defaultLightmapColors(level, partialTicks, skyDarken, skyLight, blockLightRedFlicker, pixelX, pixelY, colors);

        if(StellarView.isEnhancedCelestialsLoaded())
            EnhancedCelestialsCompatibility.adjustLightmapColors(level, partialTicks, skyDarken, skyLight, blockLightRedFlicker, pixelX, pixelY, colors);
    }

    @Override
    public boolean tickRain(ClientLevel level, int ticks, Camera camera) {
        return delegate.tickRain(level, ticks, camera);
    }

    @Override
    public boolean renderSnowAndRain(ClientLevel level, int ticks, float partialTick, LightTexture lightTexture, double camX, double camY, double camZ) {
        return delegate.renderSnowAndRain(level, ticks, partialTick, lightTexture, camX, camY, camZ);
    }

    @Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, Matrix4f modelViewMatrix, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        return ViewCenters.renderViewCenterSky(level, ticks, partialTick, modelViewMatrix, camera, projectionMatrix, isFoggy, setupFog);
    }

    @Override
    public boolean renderClouds(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, double camX, double camY, double camZ, Matrix4f modelViewMatrix, Matrix4f projectionMatrix) {
        return delegate.renderClouds(level, ticks, partialTick, poseStack, camX, camY, camZ, modelViewMatrix, projectionMatrix);
    }

    @Override
    public boolean constantAmbientLight() {
        return delegate.constantAmbientLight();
    }

    @Override
    public boolean forceBrightLightmap() {
        return delegate.forceBrightLightmap();
    }

    @Override
    public SkyType skyType() {
        return delegate.skyType();
    }

    @Override
    public boolean hasGround() {
        return delegate.hasGround();
    }

    @Override
    public float getCloudHeight() {
        return delegate.getCloudHeight();
    }

    @Override
    public @Nullable float[] getSunriseColor(float timeOfDay, float partialTicks) {
        return delegate.getSunriseColor(timeOfDay, partialTicks);
    }
}
