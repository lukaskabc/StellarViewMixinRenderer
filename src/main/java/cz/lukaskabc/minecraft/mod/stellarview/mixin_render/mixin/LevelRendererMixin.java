package cz.lukaskabc.minecraft.mod.stellarview.mixin_render.mixin;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.povstalec.stellarview.client.render.ViewCenters;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LevelRenderer.class, priority = 2000)
public class LevelRendererMixin {
    @Shadow
    @Mutable
    private ClientLevel level;

    @Shadow
    private int ticks;

    @Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
    private void renderSkyHead(Matrix4f frustumMatrix, Matrix4f projectionMatrix, float partialTick, Camera camera, boolean isFoggy, Runnable skyFogSetup, CallbackInfo ci) {
        boolean cancelVanilla = ViewCenters.renderViewCenterSky(this.level, this.ticks, partialTick, frustumMatrix, camera, projectionMatrix, isFoggy, skyFogSetup);
        if (cancelVanilla) {
            ci.cancel();
        }
    }
}
