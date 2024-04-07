package dev.crmodders.flux.mixins.localization;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.crmodders.flux.FluxAPI;
import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.localization.TranslationApi;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.RuntimeInfo;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.entities.Entity;
import finalforeach.cosmicreach.entities.Player;
import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.savelib.blockdata.IBlockData;
import finalforeach.cosmicreach.savelib.blockdata.LayeredBlockData;
import finalforeach.cosmicreach.ui.FontRenderer;
import finalforeach.cosmicreach.ui.UI;
import finalforeach.cosmicreach.world.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.DecimalFormat;
import java.util.Iterator;

import static finalforeach.cosmicreach.ui.UI.batch;

@Mixin(UI.class)
public class F3MenuMixin {

    @Shadow private Viewport uiViewport;
    @Shadow private static DecimalFormat debugPositionFormat;

    @Unique
    private static String getTranslatedString(String key) {
        return new TranslationKey(key).getTranslated().string();
    }

    @Inject(method = "drawDebugText", at = @At("HEAD"), cancellable = true)
    private void drawDebugText(CallbackInfo ci) {
        StringBuilder debugText = new StringBuilder();

        World world = InGame.world;

        Player player = InGame.getLocalPlayer();
        Zone playerZone = player.getZone(world);
        Entity playerEntity = player.getEntity();

        int bx = (int)Math.floor(playerEntity.position.x);
        int by = (int)Math.floor(playerEntity.position.y);
        int bz = (int)Math.floor(playerEntity.position.z);

        Chunk chunk = playerZone.getChunkAtBlock(bx, by, bz);

        BlockState blockState = BlockSelection.getBlockLookingAt();
        int blockLight = playerZone.getBlockLight(chunk, bx, by, bz);

        int lightR = (blockLight & 3840) >> 8;
        int lightG = (blockLight & 240) >> 4;
        int lightB = blockLight & 15;

        int skyLight = playerZone.getSkyLight(chunk, bx, by, bz);

        debugText
                .append(getTranslatedString("debug_menu.runtime").formatted(
                        RuntimeInfo.version
                )).append("\n\n")

                .append(getTranslatedString("debug_menu.fps").formatted(
                        String.valueOf(Gdx.graphics.getFramesPerSecond())
                )).append("\n")

                .append(getTranslatedString("debug_menu.position").formatted(
                        debugPositionFormat.format(playerEntity.position.x),
                        debugPositionFormat.format(playerEntity.position.y),
                        debugPositionFormat.format(playerEntity.position.z)
                )).append("\n")

                .append(getTranslatedString("debug_menu.memory_usage"))
                .append("\n\t")
                .append(getTranslatedString("debug_menu.java_heap").formatted(RuntimeInfo.getJavaHeapUseStr()))
                .append("\n\t")
                .append(getTranslatedString("debug_menu.native_heap").formatted(RuntimeInfo.getNativeHeapUseStr()))
                .append("\n")

                .append(getTranslatedString("debug_menu.chunk_title")).append("\n").append("\n\t").append(getTranslatedString("debug_menu.current_chunk").formatted(
                        chunk
                ))
        ;

        LayeredBlockData<?> layered;
        IBlockData<?> var22;
        if (chunk != null && chunk.blockData != null) {
            var22 = chunk.blockData;
            if (var22 instanceof LayeredBlockData) {
                layered = (LayeredBlockData<?>)var22;
                debugText
                        .append("\n\t")
                        .append(getTranslatedString("debug_menu.layer_type").formatted(
                                layered.getLayer(by - chunk.blockY).getClass().getSimpleName()
                        ));
            }
        }

        if (chunk != null) {
            var22 = chunk.blockData;
            if (var22 instanceof LayeredBlockData) {
                layered = (LayeredBlockData<?>)var22;
                debugText.append("\n\t").append(getTranslatedString("debug_menu.palette_size")
                        .formatted(layered.getPaletteSize()));
            }
        }

        if (world != null) {
            int numChunks = 0;

            Region r;
            for(Iterator<?> var25 = playerZone.regions.values().iterator(); var25.hasNext(); numChunks += r.getNumberOfChunks()) {
                r = (Region)var25.next();
            }

            debugText.append("\n\t").append(getTranslatedString("debug_menu.regions_loaded")
                    .formatted(playerZone.regions.size()));
            debugText.append("\n\t").append(getTranslatedString("debug_menu.chunks_loaded")
                    .formatted(numChunks));

            Zone zone = player.getZone(world);
            if (zone != null && zone.zoneGenerator != null) {
                debugText.append("\n\t").append(getTranslatedString("debug_menu.world_seed")
                        .formatted(world.worldSeed));
            }

            if (zone != null && zone.zoneGenerator != null) {
                debugText.append("\n\t").append(getTranslatedString("debug_menu.zone_seed")
                        .formatted(zone.zoneGenerator.seed));
            }
        }

        if (blockState != null) {
            debugText
                    .append("\n\t")
                    .append(getTranslatedString("debug_menu.looking_at").formatted(
                            blockState.getSaveKey()
                    ));
        }

        debugText
                .append("\n\t")
                .append(getTranslatedString("debug_menu.lighting_title"))
                .append("\n\t\t")
                .append(getTranslatedString("debug_menu.sky_lighting").formatted(String.valueOf(skyLight)))
                .append("\n\t\t")
                .append(getTranslatedString("debug_menu.block_lighting_title"))
                .append("\n\t\t\t")
                .append(getTranslatedString("debug_menu.block_light_red").formatted(String.valueOf(lightR)))
                .append("\n\t\t\t")
                .append(getTranslatedString("debug_menu.block_light_green").formatted(String.valueOf(lightG)))
                .append("\n\t\t\t")
                .append(getTranslatedString("debug_menu.block_light_blue").formatted(String.valueOf(lightB)))
        ;

        FontRenderer.fontTexture.bind(0);
        FontRenderer.drawText(
                batch,
                this.uiViewport,
                debugText.toString(),
                -this.uiViewport.getWorldWidth() / 2.0F,
                -this.uiViewport.getWorldHeight() / 2.0F
        );
        ci.cancel();
    }

}
