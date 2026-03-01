package cz.lukaskabc.minecraft.mod.stellarview.mixin_render.client;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult.Error;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static cz.lukaskabc.minecraft.mod.stellarview.mixin_render.StellarViewMixinRenderer.MODID;

public class ResourcepackReloadListener {
    static final String DIMENSION_SPECIAL_EFFECTS = "dimension_special_effects";
    private static final Logger LOG = LogUtils.getLogger();

    private static <T> void onCodecError(Error<T> err, String type) {
        LOG.error("Failed to read {}: {}", type, err.messageSupplier());
    }

    /**
     * Checks whether the given location starts with the given subFolder
     * and returns a new location in the same namespace without the leading subfolder.
     *
     * @param location  the location to check for leading {@code subFolder}
     * @param subFolder the subFolder in {@code location}
     * @return the resolved location or empty
     */
    private static Optional<ResourceLocation> enterSubFolder(ResourceLocation location, String subFolder) {
        if (location.getPath().startsWith(subFolder)) {
            return Optional.of(
                    location.withPath(location.getPath().substring(subFolder.length() + 1)) // +1 for slash
            );
        }
        return Optional.empty();
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ReloadListener extends SimpleJsonResourceReloadListener {
        public ReloadListener() {
            super(new GsonBuilder().create(), MODID);
        }

        @SubscribeEvent
        public static void registerReloadListener(RegisterClientReloadListenersEvent event) {
            event.registerReloadListener(new ReloadListener());
        }

        @Override
        protected void apply(Map<ResourceLocation, JsonElement> jsonMap, ResourceManager manager, ProfilerFiller filler) {
            final Map<ResourceLocation, DimensionSpecialEffectsOverrides> overrides = new HashMap<>(jsonMap.size());

            for (Map.Entry<ResourceLocation, JsonElement> entry : jsonMap.entrySet()) {
                final ResourceLocation originalLocation = entry.getKey();
                final JsonElement json = entry.getValue();
                enterSubFolder(originalLocation, DIMENSION_SPECIAL_EFFECTS).ifPresent(location -> {
                    DimensionSpecialEffectsOverrides.CODEC.parse(JsonOps.INSTANCE, json).ifSuccess(override ->
                            overrides.put(location, override)).ifError(err -> onCodecError(err, DIMENSION_SPECIAL_EFFECTS));
                });
            }

            DimensionSpecialEffectsRegistry.set(overrides);
        }
    }
}
