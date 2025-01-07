package dev.crmodders.flux.events;

import dev.crmodders.flux.localization.Language;

public class OnLanguageChangedEvent {

    private final Language newLanguage;

    public OnLanguageChangedEvent(Language newLanguage) {
        this.newLanguage = newLanguage;
    }

    public Language newLanguage() {
        return newLanguage;
    }
}
