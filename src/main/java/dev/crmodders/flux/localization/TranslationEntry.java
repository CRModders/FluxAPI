package dev.crmodders.flux.localization;

import java.util.Collections;
import java.util.List;

public class TranslationEntry {
    private final List<TranslationString> string;
    private final List<String> parameters;

    public TranslationEntry(List<TranslationString> string, List<String> parameters) {
        this.string = Collections.unmodifiableList(string);
        this.parameters = Collections.unmodifiableList(parameters);
        for(TranslationString str : this.string) {
            str.compile(parameters);
        }
    }

    public TranslationEntry(List<TranslationString> string) {
        this.string = Collections.unmodifiableList(string);
        this.parameters = Collections.emptyList();
    }

    public TranslationEntry() {
        this.string = List.of(new TranslationString("undefined"));
        this.parameters = Collections.emptyList();
    }

    public List<TranslationString> getStrings() {
        return string;
    }

    public TranslationString getString() {
        return string.get(0);
    }

    public TranslationString getString(int i) {
        return string.get(i);
    }

    public List<String> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return string.toString();
    }
}
