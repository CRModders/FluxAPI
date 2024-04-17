package dev.crmodders.flux.api.settings;

import finalforeach.cosmicreach.settings.GameSetting;

import java.util.Locale;

public class LocaleSetting extends GameSetting {
    private final String key;
    private Locale value;

    public LocaleSetting(String key, Locale defaultValue) {
        this.key = key;
        Object mapping = getSetting(key, null, defaultValue);
        if (mapping instanceof String locale) {
            this.value = Locale.forLanguageTag(locale);
        } else {
            this.value = defaultValue;
        }

    }

    private void save() {
        setSetting(this.key, this.value.toLanguageTag());
        saveSettings();
    }

    public Locale getValue() {
        return this.value;
    }

    public Locale setValue(Locale newValue) {
        this.value = newValue;
        this.save();
        return this.value;
    }
}