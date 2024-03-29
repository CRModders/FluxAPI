package dev.crmodders.flux.localization;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import dev.crmodders.flux.tags.Identifier;
import org.hjson.JsonArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class LanguageFile extends HashMap<TranslationKey, TranslationEntry> {

    public static LanguageFile loadLanguageFile(FileHandle file) throws IOException {
        JsonReader reader = new JsonReader();
        try (InputStream is = file.read()) {
            JsonValue value = reader.parse(is);
            return new LanguageFile(value);
        }
    }

    public static final int MAJOR_VERSION = 0;
    public static final int MINOR_VERSION = 0;

    private final Locale locale;
    private final int majorVersion;
    private final int minorVersion;

    public LanguageFile(JsonValue json) {
        JsonValue language_tag = json.get("language_tag");
        locale = Locale.forLanguageTag(language_tag.asString());
        majorVersion = json.getInt("major_version");
        minorVersion = json.getInt("minor_version");
        JsonValue ids = json.get("ids");

        for(int i = 0; i < ids.size; i++) {
            JsonValue id = json.get(ids.get(i).asString());
            Map<TranslationKey, TranslationEntry> translation = parseId(id);
            putAll(translation);
        }
    }

    public void merge(LanguageFile file){
        if(!this.locale.equals(file.locale)) {
            throw new RuntimeException("Can't Merge" + file.locale.getDisplayName() + " with " + this.locale.getDisplayName());
        }
        putAll(file);
    }

    public Locale getLocale() {
        return locale;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    private Map<TranslationKey, TranslationEntry> parseId(JsonValue id) {
        int priority = id.getInt("priority");
        JsonValue strings = id.get("strings");
        JsonValue format_templates = id.get("format_templates");
        return parseStrings(id, strings, format_templates);
    }

    private Map<TranslationKey, TranslationEntry> parseStrings(JsonValue id, JsonValue strings, JsonValue format_templates) {
        Map<TranslationKey, TranslationEntry> entries = new HashMap<>();
        for(int i = 0; i < strings.size; i++) {
            JsonValue string = strings.get(i);
            JsonValue format_template = format_templates.get(string.name);
            entries.put(parseKey(id, string), parseEntry(string, format_template));
        }
        return entries;
    }

    private TranslationKey parseKey(JsonValue id, JsonValue string) {
        return new TranslationKey(new Identifier(id.name, string.name));
    }

    private TranslationEntry parseEntry(JsonValue string, JsonValue format_template) {
        List<TranslationString> strings = parseArray(string).stream().map(TranslationString::new).toList();
        if(format_template == null) {
            return new TranslationEntry(strings);
        } else {
            List<String> parameters = parseArray(format_template);
            return new TranslationEntry(strings, parameters);
        }
    }

    private List<String> parseArray(JsonValue array){
        if(array.isArray()) {
            return Arrays.asList(array.asStringArray());
        } else {
            return List.of(array.asString());
        }
    }

}
