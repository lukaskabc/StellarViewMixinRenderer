package cz.lukaskabc.minecraft.mod.stellarview.mixin_render.mixin;

import cz.lukaskabc.minecraft.mod.stellarview.mixin_render.DimensionEffectsDelegate;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.povstalec.stellarview.client.render.ViewCenters;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.function.Supplier;

@Mixin(value = ClientLevel.class, priority = 2000)
public class ClientLevelMixin {
    @Mutable
    @Shadow
    @Final
    private DimensionSpecialEffects effects;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void overrideEffectsAssignment(
            ClientPacketListener connection,
            ClientLevel.ClientLevelData clientLevelData,
            ResourceKey<Level> dimension,
            Holder<DimensionType> dimensionType,
            int viewDistance,
            int serverSimulationDistance,
            Supplier<ProfilerFiller> profiler,
            LevelRenderer levelRenderer,
            boolean isDebug,
            long biomeZoomSeed,
            CallbackInfo ci
    ) {
        if (ViewCenters.isViewCenterPresent(dimension.location())) {
            Objects.requireNonNull(effects, "Original DimensionSpecialEffects in ClientLevel cannot be null");
            this.effects = new DimensionEffectsDelegate(effects);
        }
    }
}
