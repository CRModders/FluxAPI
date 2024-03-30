package dev.crmodders.flux.mixins.localization;

import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.RuntimeInfo;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.MainMenu;
import finalforeach.cosmicreach.settings.Controls;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = MainMenu.class, priority = 2000)
abstract public class MainMenuMixin extends GameState {

	@Unique
	private static final TranslationKey TEXT_START = new TranslationKey("main_menu.start");
	@Unique
	private static final TranslationKey TEXT_OPEN_SAVE_DIRECTORY = new TranslationKey("main_menu.open_save_directory");
	@Unique
	private static final TranslationKey TEXT_OPTIONS = new TranslationKey("main_menu.options");
	@Unique
	private static final TranslationKey TEXT_QUIT = new TranslationKey("main_menu.quit");
	@Unique
	private static final TranslationKey TEXT_YT_CHANNEL = new TranslationKey("main_menu.yt_channel");
	@Unique
	private static final TranslationKey TEXT_CONTROLLER_INFO = new TranslationKey("main_menu.controller_info");
	@Unique
	private static final TranslationKey TEXT_VERSION = new TranslationKey("main_menu.game_version");
	@Unique
	private static final TranslationKey TEXT_CONTROLLER = new TranslationKey("main_menu.controller");
	@Unique
	private static final TranslationKey TEXT_CONTROLLERS = new TranslationKey("main_menu.controllers");
	@Unique
	private static final TranslationKey TEXT_MAC_SUPPORT = new TranslationKey("main_menu.mac_warning");

	@ModifyArg(method = "create", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/ui/UIElement;setText(Ljava/lang/String;)V", ordinal = 0), index = 0)
	private String start(String text) {
		return TEXT_START.getTranslated().string();
	}

	@ModifyArg(method = "create", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/ui/UIElement;setText(Ljava/lang/String;)V", ordinal = 1), index = 0)
	private String openSaveDirectory(String text) {
		return TEXT_OPEN_SAVE_DIRECTORY.getTranslated().string();
	}

	@ModifyArg(method = "create", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/ui/UIElement;setText(Ljava/lang/String;)V", ordinal = 2), index = 0)
	private String options(String text) {
		return TEXT_OPTIONS.getTranslated().string();
	}

	@ModifyArg(method = "create", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/ui/UIElement;setText(Ljava/lang/String;)V", ordinal = 3), index = 0)
	private String quit(String text) {
		return TEXT_QUIT.getTranslated().string();
	}

	@ModifyVariable(method = "render", at = @At("STORE"), ordinal = 0)
	private String promoText(String x) {
		if (x.equals("Watch the devlogs at youtube.com/@finalforeach")) {
			return TEXT_YT_CHANNEL.getTranslated().string();
		}
		if (x.startsWith("Cosmic Reach Version")) {
			return TEXT_VERSION.getTranslated().format("Pre-Alpha-" + RuntimeInfo.version);
		}
		return x;
	}

	@ModifyVariable(method = "render", at = @At("STORE"), ordinal = 2)
	private String controller(String x) {
		String controller = TEXT_CONTROLLER.getTranslated().string();
		String controllers = TEXT_CONTROLLERS.getTranslated().string();;
		return TEXT_CONTROLLER_INFO.getTranslated().format(String.valueOf(Controls.controllers.size), Controls.controllers.size == 1 ? controller : controllers);
	}

	@ModifyVariable(method = "render", at = @At("STORE"), ordinal = 1)
	private String macWarning(String x) {
		if(x.equals("WARNING: Mac is not supported at this time. The game is unlikely to work.")) {
			return TEXT_MAC_SUPPORT.getTranslated().string();
		}
		return x;
	}

}
