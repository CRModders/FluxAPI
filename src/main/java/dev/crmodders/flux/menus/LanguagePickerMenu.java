package dev.crmodders.flux.menus;

import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.api.gui.ButtonElement;
import dev.crmodders.flux.api.gui.TextElement;
import dev.crmodders.flux.api.gui.base.BaseButton;
import dev.crmodders.flux.api.gui.base.BaseText;
import dev.crmodders.flux.localization.TranslationApi;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.gamestates.GameState;

import java.util.List;
import java.util.Locale;

public class LanguagePickerMenu extends ScrollMenu{

    public static final TranslationKey TEXT_TITLE = new TranslationKey("fluxapi:flux_options.language");
    public static final TranslationKey TEXT_LANGUAGE = new TranslationKey("fluxapi:flux_options.language");

    private class LanguageButton extends  BaseButton {
        public Locale locale;

        public LanguageButton(Locale locale) {
            this.locale = locale;
        }

        @Override
        public void onMouseReleased() {
            super.onMouseReleased();
            selectLanguage(locale);
        }

        @Override
        public String updateTranslation(TranslationKey key) {
            String text = key.getTranslated().format(locale.getDisplayName());
            if(LanguagePickerMenu.this.locale == locale) {
                return "[%6" + text + "%f]";
            }
            return text;
        }

    }

    private Locale locale;

    public LanguagePickerMenu(GameState previousState) {
        super(previousState);

        addCancelButton();
        addSaveButton();

        this.locale = FluxSettings.LanguageSetting.getValue();

        for(Locale locale : TranslationApi.getLanguages()) {
            LanguageButton button = new LanguageButton(locale);
            button.translation = TEXT_LANGUAGE;
            addScrollElement(button);
        }

    }

    public void selectLanguage(Locale locale) {
        this.locale = locale;
    }

    @Override
    public void render(float partTime) {
        super.render(partTime);
        List<Locale> languages = TranslationApi.getLanguages();
        locale = languages.get(getSelectedIndex());
        elements.forEach(BaseText::updateText);
    }

    @Override
    protected void onCancel() {
        super.onCancel();
    }

    @Override
    protected void onSave() {
        FluxSettings.LanguageSetting.setValue(locale);
        super.onSave();
    }


}
