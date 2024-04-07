package dev.crmodders.flux.menus;

import com.badlogic.gdx.graphics.Texture;
import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.api.gui.ButtonElement;
import dev.crmodders.flux.api.gui.TextElement;
import dev.crmodders.flux.api.gui.base.BaseButton;
import dev.crmodders.flux.api.gui.base.BaseText;
import dev.crmodders.flux.api.resource.ResourceLocation;
import dev.crmodders.flux.localization.TranslationApi;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.OptionsMenu;
import finalforeach.cosmicreach.io.SaveLocation;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.VerticalAnchor;
import org.pmw.tinylog.Logger;

import java.awt.*;
import java.util.List;
import java.util.Locale;

public class LanguagePickerMenu extends ScrollMenu{

    public static final TranslationKey TEXT_LANGUAGE = new TranslationKey("fluxapi:flux_options.language");

    private class LanguageButton extends BaseText {
        public Locale locale;

        public LanguageButton(Locale locale) {
            this.locale = locale;
        }

        @Override
        public String updateTranslation(TranslationKey key) {
            String text = key.getTranslated().format(locale.getDisplayName());
            if(LanguagePickerMenu.this.locale == locale) {
                return "%6" + text;
            }
            return text;
        }

    }
    public static Texture directoryIcon = new Texture(FluxConstants.DirectoryIcon.load());
    public static Texture reloadIcon = new Texture(FluxConstants.ReloadIcon.load());

    private Locale locale;

    public LanguagePickerMenu(GameState previousState) {
        super(previousState);

        addCancelButton();
        addSaveButton();

        ButtonElement openDirectory = new ButtonElement(this::openLanguageDirectory);
        openDirectory.setBounds(5, 5, 50, 50);
        openDirectory.setAnchors(HorizontalAnchor.LEFT_ALIGNED, VerticalAnchor.TOP_ALIGNED);
        openDirectory.setIcon(directoryIcon, 40f, 0f);
        openDirectory.updateText();
        addFluxElement(openDirectory);

        ButtonElement reload = new ButtonElement(this::reloadLanguages);
        reload.setBounds(60, 5, 50, 50);
        reload.setAnchors(HorizontalAnchor.LEFT_ALIGNED, VerticalAnchor.TOP_ALIGNED);
        reload.setIcon(reloadIcon, 40f, 0f);
        reload.updateText();
        addFluxElement(reload);

        reloadButtons();
    }

    private void reloadButtons() {
        List<Locale> languages = TranslationApi.getLanguages();

        elements.forEach(this::removeScrollElement);

        this.locale = FluxSettings.LanguageSetting.getValue();
        for(Locale locale : languages) {
            LanguageButton button = new LanguageButton(locale);
            button.translation = TEXT_LANGUAGE;
            addScrollElement(button);
        }

        this.setSelectedIndex(languages.indexOf(this.locale));
    }


    public void openLanguageDirectory() {
        try {
            SaveLocation.OpenFolderWithFileManager(TranslationApi.LANGUAGE_FOLDER);
        } catch (Exception e) {
            Logger.error(e);
        }
    }

    public void reloadLanguages() {
        TranslationApi.discoverLanguages();
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
        TranslationApi.setLanguage(locale);
        super.onSave();
    }


}
