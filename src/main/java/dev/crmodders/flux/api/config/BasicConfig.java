package dev.crmodders.flux.api.config;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.io.SaveLocation;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class BasicConfig {

    public static File ConfigDirectory = new File(SaveLocation.getSaveFolderLocation(), "configs");

    static {
        if(!ConfigDirectory.exists()) {
            ConfigDirectory.mkdir();
        }
    }

    public static File getConfigLocation(Identifier config) {
        File location = Path.of(ConfigDirectory.getAbsolutePath(), config.namespace, config.name).toFile();
        if(!location.exists()) {
            File parent = location.getParentFile();
            parent.mkdir();
        }
        return location;
    }

    private final Identifier id;
    private final String friendlyName;
    private JsonValue value;

    public BasicConfig(Identifier id, String friendlyName) {
        this.id = id;
        this.friendlyName = friendlyName;
        this.value = new JsonValue(JsonValue.ValueType.object);
    }

    public void load() {
        File file = getConfigLocation(id);
        if(!file.exists()) {
            save();
            return;
        }
        try (FileReader reader = new FileReader(file)) {
            JsonReader json = new JsonReader();
            value = json.parse(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save() {
        try(FileWriter writer = new FileWriter(getConfigLocation(id))) {
            value.prettyPrint(JsonWriter.OutputType.json, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Identifier getId() {
        return id;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public JsonValue getValue() {
        return value;
    }
}
