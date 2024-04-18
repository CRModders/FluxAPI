package dev.crmodders.flux.api.v5.settings;

import finalforeach.cosmicreach.settings.GameSetting;

public class StringSetting extends GameSetting {
    private final String key;
    private String value;

    public StringSetting(String key, String defaultValue) {
        this.key = key;
        Object mapping = getSetting(key, null, defaultValue);
        if (mapping instanceof String n) {
            this.value = n;
        } else {
            this.value = defaultValue;
        }

    }

    private void save() {
        setSetting(this.key, this.value);
        saveSettings();
    }

    public String getValue() {
        return this.value;
    }

    public String setValue(String newValue) {
        this.value = newValue;
        this.save();
        return this.value;
    }
}