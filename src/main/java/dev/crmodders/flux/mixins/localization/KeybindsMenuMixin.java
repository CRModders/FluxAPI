package dev.crmodders.flux.mixins.localization;

import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.gamestates.KeybindsMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = KeybindsMenu.class, priority = 2000)
public class KeybindsMenuMixin {

	@Unique
	private static final TranslationKey TEXT_FORWARD =     new TranslationKey("keybinds.forward");
	@Unique
	private static final TranslationKey TEXT_BACKWARD =     new TranslationKey("keybinds.backward");
	@Unique
	private static final TranslationKey TEXT_LEFT =     new TranslationKey("keybinds.left");
	@Unique
	private static final TranslationKey TEXT_RIGHT =     new TranslationKey("keybinds.right");
	@Unique
	private static final TranslationKey TEXT_JUMP =     new TranslationKey("keybinds.jump");
	@Unique
	private static final TranslationKey TEXT_CROUCH =     new TranslationKey("keybinds.crouch");
	@Unique
	private static final TranslationKey TEXT_SPRINT =     new TranslationKey("keybinds.sprint");
	@Unique
	private static final TranslationKey TEXT_PRONE =     new TranslationKey("keybinds.prone");
	@Unique
	private static final TranslationKey TEXT_INVENTORY =     new TranslationKey("keybinds.inventory");
	@Unique
	private static final TranslationKey TEXT_DROP_ITEM =     new TranslationKey("keybinds.drop_item");
	@Unique
	private static final TranslationKey TEXT_HIDE_UI =     new TranslationKey("keybinds.hide_ui");
	@Unique
	private static final TranslationKey TEXT_SCREENSHOT =     new TranslationKey("keybinds.screenshot");
	@Unique
	private static final TranslationKey TEXT_DEBUG_INFO =     new TranslationKey("keybinds.debug_info");
	@Unique
	private static final TranslationKey TEXT_NO_CLIP =     new TranslationKey("keybinds.no_clip");
	@Unique
	private static final TranslationKey TEXT_RELOAD_SHADERS =     new TranslationKey("keybinds.reload_shaders");
	@Unique
	private static final TranslationKey TEXT_FULLSCREEN =     new TranslationKey("keybinds.fullscreen");

	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/ui/UIElement;setText(Ljava/lang/String;)V", ordinal = 0), index = 0)
	private String start(String text) {
		return FluxConstants.TextDone.getTranslated().string();
}

	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/gamestates/KeybindsMenu;addKeybindButton(Ljava/lang/String;Lfinalforeach/cosmicreach/settings/Keybind;)V", ordinal = 0), index = 0)
	private String forward(String x) {
		return TEXT_FORWARD.getTranslated().string();
}

	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/gamestates/KeybindsMenu;addKeybindButton(Ljava/lang/String;Lfinalforeach/cosmicreach/settings/Keybind;)V", ordinal = 1), index = 0)
	private String backward(String x) {
		return TEXT_BACKWARD.getTranslated().string();
}

	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/gamestates/KeybindsMenu;addKeybindButton(Ljava/lang/String;Lfinalforeach/cosmicreach/settings/Keybind;)V", ordinal = 2), index = 0)
	private String left(String x) {
		return TEXT_LEFT.getTranslated().string();
}

	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/gamestates/KeybindsMenu;addKeybindButton(Ljava/lang/String;Lfinalforeach/cosmicreach/settings/Keybind;)V", ordinal = 3), index = 0)
	private String right(String x) {
		return TEXT_RIGHT.getTranslated().string();
}

	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/gamestates/KeybindsMenu;addKeybindButton(Ljava/lang/String;Lfinalforeach/cosmicreach/settings/Keybind;)V", ordinal = 4), index = 0)
	private String jump(String x) {
		return TEXT_JUMP.getTranslated().string();
}

	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/gamestates/KeybindsMenu;addKeybindButton(Ljava/lang/String;Lfinalforeach/cosmicreach/settings/Keybind;)V", ordinal = 5), index = 0)
	private String crouch(String x) {
		return TEXT_CROUCH.getTranslated().string();
}

	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/gamestates/KeybindsMenu;addKeybindButton(Ljava/lang/String;Lfinalforeach/cosmicreach/settings/Keybind;)V", ordinal = 6), index = 0)
	private String sprint(String x) {
		return TEXT_SPRINT.getTranslated().string();
}

	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/gamestates/KeybindsMenu;addKeybindButton(Ljava/lang/String;Lfinalforeach/cosmicreach/settings/Keybind;)V", ordinal = 7), index = 0)
	private String prone(String x) {
		return TEXT_PRONE.getTranslated().string();
}

	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/gamestates/KeybindsMenu;addKeybindButton(Ljava/lang/String;Lfinalforeach/cosmicreach/settings/Keybind;)V", ordinal = 8), index = 0)
	private String inventory(String x) {
		return TEXT_INVENTORY.getTranslated().string();
}

	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/gamestates/KeybindsMenu;addKeybindButton(Ljava/lang/String;Lfinalforeach/cosmicreach/settings/Keybind;)V", ordinal = 9), index = 0)
	private String dropItem(String x) {
		return TEXT_DROP_ITEM.getTranslated().string();
}

	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/gamestates/KeybindsMenu;addKeybindButton(Ljava/lang/String;Lfinalforeach/cosmicreach/settings/Keybind;)V", ordinal = 10), index = 0)
	private String hideUI(String x) {
		return TEXT_HIDE_UI.getTranslated().string();
}

	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/gamestates/KeybindsMenu;addKeybindButton(Ljava/lang/String;Lfinalforeach/cosmicreach/settings/Keybind;)V", ordinal = 11), index = 0)
	private String screenshot(String x) {
		return TEXT_SCREENSHOT.getTranslated().string();
}

	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/gamestates/KeybindsMenu;addKeybindButton(Ljava/lang/String;Lfinalforeach/cosmicreach/settings/Keybind;)V", ordinal = 12), index = 0)
	private String debugInfo(String x) {
		return TEXT_DEBUG_INFO.getTranslated().string();
}

	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/gamestates/KeybindsMenu;addKeybindButton(Ljava/lang/String;Lfinalforeach/cosmicreach/settings/Keybind;)V", ordinal = 13), index = 0)
	private String noClip(String x) {
		return TEXT_NO_CLIP.getTranslated().string();
}

	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/gamestates/KeybindsMenu;addKeybindButton(Ljava/lang/String;Lfinalforeach/cosmicreach/settings/Keybind;)V", ordinal = 14), index = 0)
	private String reloadShaders(String x) {
		return TEXT_RELOAD_SHADERS.getTranslated().string();
}

	@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/gamestates/KeybindsMenu;addKeybindButton(Ljava/lang/String;Lfinalforeach/cosmicreach/settings/Keybind;)V", ordinal = 15), index = 0)
	private String fullscreen(String x) {
		return TEXT_FULLSCREEN.getTranslated().string();
}

}
