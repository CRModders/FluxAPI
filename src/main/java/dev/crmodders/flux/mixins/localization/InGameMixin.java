package dev.crmodders.flux.mixins.localization;

import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.io.SaveLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = InGame.class, priority = 2000)
public class InGameMixin {

    @Unique
    private static final TranslationKey TEXT_SCREENSHOT_SAVED_TO = new TranslationKey("in_game.screenshot_saved_to");
    @Shadow
    private transient String lastScreenshotFileName;

    @ModifyVariable(method = "render", at = @At("STORE"), ordinal = 0)
    private String replace(String x) {
        return TEXT_SCREENSHOT_SAVED_TO.getTranslated().format(lastScreenshotFileName.replace(SaveLocation.getSaveFolderLocation(), ""));
    }

}
