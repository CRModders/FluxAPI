package dev.crmodders.flux.mixins.localization;

import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.WorldSelectionMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = WorldSelectionMenu.class, priority = 2000)
abstract public class WorldSelectionMenuMixin extends GameState {

	@Unique
	private static final TranslationKey TEXT_CREATE_WORLD = new TranslationKey("world_chooser_menu.create_new_world");
	@Unique
	private static final TranslationKey TEXT_OPEN_WORLDS_FOLDER = new TranslationKey("world_chooser_menu.open_worlds_directory");


	@ModifyArg(method = "create", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/ui/UIElement;setText(Ljava/lang/String;)V", ordinal = 3), index = 0)
	private String createWorld(String text) {
		return TEXT_CREATE_WORLD.getTranslated().string();
	}

	@ModifyArg(method = "create", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/ui/UIElement;setText(Ljava/lang/String;)V", ordinal = 4), index = 0)
	private String returnToMainMenu(String text) {
		return FluxConstants.TextReturnToMainMenu.getTranslated().string();
	}

	@ModifyArg(method = "create", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/ui/UIElement;setText(Ljava/lang/String;)V", ordinal = 5), index = 0)
	private String openDir(String text) {
		return TEXT_OPEN_WORLDS_FOLDER.getTranslated().string();
	}


}
