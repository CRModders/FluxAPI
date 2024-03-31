package dev.crmodders.flux.menus;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonValue;
import dev.crmodders.flux.api.config.BasicConfig;
import dev.crmodders.flux.api.gui.TextBoxElement;
import dev.crmodders.flux.localization.TranslationKey;
import finalforeach.cosmicreach.gamestates.GameState;

public class EditConfigMenu extends LayoutMenu {

    public static final TranslationKey TEXT_EMPTY = new TranslationKey("fluxapi:1");

    private BasicConfig config;

    public EditConfigMenu(GameState previousState, BasicConfig config) {
        super(previousState);
        this.config = config;

        setLayoutEnabled(false);
        addCancelButton();
        addSaveButton();
        config.save();
    }

    private void addTextField(String key) {
        System.out.println(key);
        addUIElement(new TextBoxElement(TEXT_EMPTY) {
            @Override
            protected void onLoadString() {
            }

            @Override
            protected void onSaveString() {
            }
        });
    }

    @Override
    protected void onCancel() {
        config.load();
        super.onCancel();
    }

    @Override
    protected void onSave() {
        config.save();
        super.onSave();
    }
}
