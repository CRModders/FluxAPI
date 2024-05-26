package dev.crmodders.flux.events;

import com.badlogic.gdx.files.FileHandle;
import dev.crmodders.flux.localization.ILanguageFile;
import dev.crmodders.flux.localization.LanguageManager;
import dev.crmodders.flux.localization.files.LanguageFileVersion1;
import dev.crmodders.flux.logging.LoggingAgent;
import dev.crmodders.flux.logging.api.MicroLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class OnRegisterLanguageEvent extends Event {

    private static final MicroLogger LOGGER = LoggingAgent.getLogger("Language Registerer");

    public void registerLanguage(FileHandle file) {
        try {
            ILanguageFile lang = LanguageFileVersion1.loadLanguageFile(file);
            LanguageManager.registerLanguageFile(lang);
        } catch (IOException e) {
            LOGGER.error("Failed loading Language {}", file.path(), e);
        }
    }

}
