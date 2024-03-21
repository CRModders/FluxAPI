package io.github.crmodders.flux.api.config.modinfo;

import io.github.crmodders.flux.FluxAPI;
import io.github.crmodders.flux.api.config.modinfo.json.Dependency;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import org.hjson.JsonArray;
import org.hjson.JsonObject;
import org.hjson.JsonValue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;

public class ModManager {
    public static String ModsDir = FabricLoaderImpl.INSTANCE.getModsDirectory().getAbsolutePath().replaceAll("\\\\\\.", "");

//    public static List<FabricModInfo> getModsFromFabric() {
//        List<FabricModInfo> modInfoList = new ArrayList<>();
//        for (ModContainer container : FabricLoaderImpl.INSTANCE.getAllMods()) {
//            try {
//                modInfoList.add(new FabricModInfo(
//                        obj.get("schemaVersion").asInt(),
//                        obj.get("id").asString(),
//                        obj.get("version").asString(),
//                        obj.get("name").asString(),
//                        obj.get("description").asString(),
//                        toStringArray(obj.get("authors").asArray()),
//                        obj.get("license").asString(),
//                        obj.get("environment").asString(),
//                        new EntryPoints(
//                                getAllowedDefaultStrArray(obj.get("entrypoints"), "main"),
//                                getAllowedDefaultStrArray(obj.get("entrypoints"), "client"),
//                                getAllowedDefaultStrArray(obj.get("entrypoints"), "server"),
//                                getAllowedDefaultStrArray(obj.get("entrypoints"), "preLaunch")
//                        ),
//                        getAllowedDefaultStrArray(obj, "mixins"),
//                        convertDependencies(container.getMetadata().getDependencies())
//                ));
//            } catch (Exception ignore) {
//                ignore.printStackTrace();
//            }
//        }
//        return modInfoList;
//    }
//
//   public static Dependency[] convertDependencies(Collection<ModDependency> dependencies) {
//        List<Dependency> t = new ArrayList<>();
//        for (ModDependency dependency : dependencies) {
//            t.add(new Dependency(dependency.getModId(), null));
//        }
//        return t.toArray(Dependency[]::new);
//   }

    public static List<FabricModInfo> getModsFromFiles() {
        List<FabricModInfo> modInfoList = new ArrayList<>();
        for (File fMod : new File(ModsDir).listFiles()) {
            try {
                ZipFile mod = new ZipFile(fMod.getAbsolutePath());
                String fabricModJson = new String(
                        mod.getInputStream(mod.getEntry("fabric.mod.json")).readAllBytes()
                );

                JsonObject obj = JsonObject.readJSON(fabricModJson).asObject();
                modInfoList.add(new FabricModInfo(
                        obj.get("schemaVersion").asInt(),
                        obj.get("id").asString(),
                        obj.get("version").asString(),
                        obj.get("name").asString(),
                        obj.get("description").asString(),
                        toStringArray(obj.get("authors").asArray()),
                        obj.get("license").asString(),
                        obj.get("environment").asString(),
                        new EntryPoints(
                                getAllowedDefaultStrArray(obj.get("entrypoints"), "main"),
                                getAllowedDefaultStrArray(obj.get("entrypoints"), "client"),
                                getAllowedDefaultStrArray(obj.get("entrypoints"), "server"),
                                getAllowedDefaultStrArray(obj.get("entrypoints"), "preLaunch")
                        ),
                        getAllowedDefaultStrArray(obj, "mixins"),
                        getDependencies(obj)
                ));
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
        }
        return modInfoList;
    }

    private static Dependency[] getDependencies(JsonValue obj) {
        try {
            obj = obj.asObject().get("depends");
        } catch (Exception ignore) {
            return new Dependency[0];
        }
        Dependency[] dependencies = new Dependency[obj.asObject().names().size()];
        for (int i = 0; i < obj.asObject().names().size(); i++) {
            String name = obj.asObject().names().get(i);
            dependencies[i] = new Dependency(
                    name,
                    obj.asObject().get(name).asString()
            );
        }
        return dependencies;
    }

    private static String[] getAllowedDefaultStrArray(JsonValue obj, String key) {
        try {
            return toStringArray(obj.asObject().get(key).asArray());
        } catch (Exception ignore) {
            FluxAPI.LOGGER.info("SET KEY \""+key+"\" TO DEFAULT AFTER BEING \"NULL\"");
            return new String[0];
        }
    }

    private static String[] toStringArray(JsonArray array) {
        String[] arry = new String[array.size()];
        for (int i = 0; i < array.size(); i++) {
            arry[i] = array.get(i).asString();
        }
        return arry;
    }
}
