package io.github.crmodders.flux.api.config;

import finalforeach.cosmicreach.io.SaveLocation;
import io.github.crmodders.flux.FluxAPI;
import io.github.crmodders.flux.FluxConstants;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import org.hjson.JsonObject;
import org.hjson.Stringify;

import java.io.*;

public class BasicConfig {

    public static String configDir;

    static {
        configDir = SaveLocation.getSaveFolderLocation() + "/configs";

        if (!new File(configDir).exists())
            new File(configDir).mkdir();
    }

    private static Builder makeIfNotExists(String name) {
        File file = new File(configDir + "\\" + name + ".hjson");
        return new Builder(file);
    }

    private static BasicConfig find(String name) {
        File file = new File(configDir + "\\" + name + ".hjson");
        if (file.exists()) return new BasicConfig(file);
        return null;
    }

    private final JsonObject value;

    protected BasicConfig(File file) {
        try {
            value = JsonObject.readJSON(new FileReader(file)).asObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T getValue(String key, T _default) {
        if (value.get(key) != null)
            return switch (_default.getClass().getName()) {
                case "Integer" -> (T) Integer.valueOf(value.get(key).asInt());
                case "Long" -> (T) Long.valueOf(value.get(key).asLong());
                case "Double" -> (T) Double.valueOf(value.get(key).asDouble());
                case "Float" -> (T) Float.valueOf(value.get(key).asFloat());
                case "String" -> (T) String.valueOf(value.get(key).asString());
                case "Boolean" -> (T) Boolean.valueOf(value.get(key).asBoolean());
                default -> _default;
            };
        return _default;
    }

    public static class Builder {

        private final File file;
        private final JsonObject jsonObject;
        private boolean exists;

        private Builder(File file) {
            this.file = file;
            this.jsonObject = new JsonObject();
            this.exists = file.exists();
        }

        public <T> Builder addOption(String key, T value) {
            if (exists) return this;

            switch (value.getClass().getName()) {
                case "Integer": jsonObject.set(key, (Integer) value);
                case "Long": jsonObject.set(key, (Integer) value);
                case "Double": jsonObject.set(key, (Integer) value);
                case "Float": jsonObject.set(key, (Integer) value);
                case "String": jsonObject.set(key, (Integer) value);
                case "Boolean": jsonObject.set(key, (Boolean) value);
                default:
                    FluxConstants.LOGGER.warning("CANNOT SET KEY \"%s\" WITH TYPE \"%s\"".formatted(key, value.getClass().getName()));
            }
            return this;
        }

        public BasicConfig build() {
            if (exists) return new BasicConfig(file);

            try {
                FileWriter f = new FileWriter(file.getAbsolutePath());
                f.write(jsonObject.toString(Stringify.FORMATTED));
                f.close();
                return new BasicConfig(file);
            } catch (Exception ignore) {
                FluxConstants.LOGGER.warning("COULD NOT BUILD \""+file+"\"");
                return null;
            }
        }


    }

}
