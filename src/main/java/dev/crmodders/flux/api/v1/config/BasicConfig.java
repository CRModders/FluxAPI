package dev.crmodders.flux.api.v1.config;

import dev.crmodders.flux.api.v1.FluxV1;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import org.hjson.JsonObject;
import org.hjson.JsonValue;

import java.io.*;

public class BasicConfig {
    public static String ConfigDir;

    static {
        ConfigDir = FabricLoaderImpl.INSTANCE.getGameDir().toAbsolutePath().toString().replaceAll("\\\\\\.", "") + "\\configs";

        if (!new File(ConfigDir).exists())
            new File(ConfigDir).mkdir();
    }

    public static FakeConfigBuilder createIfNotMade(String name) {
        File CFG = new File(ConfigDir + "\\" + name + ".hjson");
        if (!CFG.exists()) return new BasicConfigBuilder(CFG);
        return new FakeConfigBuilder(CFG);
    }

    JsonObject CFG_Value;

    public static BasicConfig get(File name) throws IOException {
        return new BasicConfig(name);
    }

    public BasicConfig(File name) throws IOException {
        CFG_Value = JsonValue.readHjson(new FileReader(name)).asObject();
    }

    public JsonValue getValue(String key, JsonObject default0) {
        try {
            CFG_Value.get(key).asDsf();
        } catch (Exception ignore) {
            return default0;
        }
        return CFG_Value.get(key);
    }

    public static class BasicConfigBuilder extends FakeConfigBuilder {
        private final File CFG_File;
        private JsonObject CFG_Value;

        private BasicConfigBuilder(File file) {
            super(file);
            CFG_File = file;
            CFG_Value = new JsonObject();
        }

        public BasicConfigBuilder addConfigOption(String key, String value) {
            CFG_Value = CFG_Value.set(key, value);
            return this;
        }
        public BasicConfigBuilder addConfigOption(String key, boolean value) {
            CFG_Value.set(key, value);
            return this;
        }
        public BasicConfigBuilder addConfigOption(String key, int value) {
            CFG_Value.set(key, value);
            return this;
        }
        public BasicConfigBuilder addConfigOption(String key, float value) {
            CFG_Value.set(key, value);
            return this;
        }
        public BasicConfigBuilder addConfigOption(String key, double value) {
            CFG_Value.set(key, value);
            return this;
        }
        public BasicConfigBuilder addConfigOption(String key, long value) {
            CFG_Value.set(key, value);
            return this;
        }

        public BasicConfig build() {
            try {
                FileWriter f = new FileWriter(CFG_File.getAbsolutePath());
                f.write(CFG_Value.toString());
                f.close();
                return new BasicConfig(CFG_File);
            } catch (Exception ignore) {
                FluxV1.LOGGER.info("COULD NOT BUILD \""+CFG_File+"\"");
                return null;
            }
        }
    }

    public static class FakeConfigBuilder {
        private final File CFG_File;

        private FakeConfigBuilder(File file) {
            CFG_File = file;
        }

        public FakeConfigBuilder addConfigOption(String key, String value) {
            FluxV1.LOGGER.info("Will Not Set To Prebuilt Config, \"" + key + ":" + value + "\"");
            return this;
        }
        public FakeConfigBuilder addConfigOption(String key, boolean value) {
            FluxV1.LOGGER.info("Will Not Set To Prebuilt Config, \"" + key + ":" + value + "\"");
            return this;
        }
        public FakeConfigBuilder addConfigOption(String key, int value) {
            FluxV1.LOGGER.info("Will Not Set To Prebuilt Config, \"" + key + ":" + value + "\"");
            return this;
        }
        public FakeConfigBuilder addConfigOption(String key, float value) {
            FluxV1.LOGGER.info("Will Not Set To Prebuilt Config, \"" + key + ":" + value + "\"");
            return this;
        }
        public FakeConfigBuilder addConfigOption(String key, double value) {
            FluxV1.LOGGER.info("Will Not Set To Prebuilt Config, \"" + key + ":" + value + "\"");
            return this;
        }
        public FakeConfigBuilder addConfigOption(String key, long value) {
            FluxV1.LOGGER.info("Will Not Set To Prebuilt Config, \"" + key + ":" + value + "\"");
            return this;
        }

        public BasicConfig build() {
            try {
                return new BasicConfig(CFG_File);
            } catch (Exception ignore) {
                FluxV1.LOGGER.info("COULD NOT GET \""+CFG_File+"\"");
                return null;
            }
        }
    }

}