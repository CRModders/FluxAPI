package dev.crmodders.flux.events;

import com.badlogic.gdx.files.FileHandle;
import dev.crmodders.flux.localization.ILanguageFile;
import dev.crmodders.flux.localization.LanguageManager;
import dev.crmodders.flux.localization.files.LanguageFileVersion1;

import java.io.IOException;

public class OnRegisterLanguageEvent extends Event {

    public void registerLanguage(FileHandle file) {
        try {
            ILanguageFile lang = LanguageFileVersion1.loadLanguageFile(file);
            LanguageManager.registerLanguageFile(lang);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
