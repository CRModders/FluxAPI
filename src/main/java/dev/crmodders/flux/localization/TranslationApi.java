package dev.crmodders.flux.localization;

import com.badlogic.gdx.files.FileHandle;
import dev.crmodders.flux.FluxConstants;
import dev.crmodders.flux.FluxSettings;
import dev.crmodders.flux.api.resource.ResourceLocation;
import dev.crmodders.flux.registry.Registries;
import dev.crmodders.flux.registry.registries.AccessableRegistry;
import dev.crmodders.flux.tags.Identifier;
import finalforeach.cosmicreach.io.SaveLocation;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

import static com.badlogic.gdx.Gdx.files;

public class TranslationApi {
    public static Identifier getLocaleIdentifier(Locale locale) {
        return new Identifier(FluxConstants.MOD_ID, locale.toString().replace("_","-"));
    }

    public static final Locale LOCALE_EN_US = Locale.forLanguageTag("en-US");
    public static final Identifier EN_US = getLocaleIdentifier(LOCALE_EN_US);

    public static void discoverLANGUAGES() {

        List<FileHandle> discoveredFiles = new ArrayList<>();
        for (ModContainer container : FabricLoader.getInstance().getAllMods()) {
            URL url;
            String modId = container.getMetadata().getId();
            if (modId.equals("fabricloader") || (url = TranslationApi.class.getResource("/assets/" + modId + "/LANGUAGES/")) == null)
                continue;
            try {
                URI uri = url.toURI();
                HashMap<String, String> env = new HashMap<String, String>();
                env.put("create", "true");
                FileSystem zipfs = FileSystems.newFileSystem(uri, env);
                Path path = Paths.get(url.toURI());
                try (Stream<Path> entries = Files.walk(path, 5)) {
                    entries.forEach(p -> {
                        String fileName = p.getFileName().toString();
                        if (fileName.endsWith(".json")) {
                            FileHandle handle = new ResourceLocation(modId, "LANGUAGES/" + fileName).load();
                            discoveredFiles.add(handle);
                        }
                    });
                }
                zipfs.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        Collections.addAll(discoveredFiles, files.absolute(SaveLocation.getSaveFolderLocation() + "/mods/assets/LANGUAGES").list());

        try {
            registerLanguage(LanguageFile.loadLanguageFile(FluxConstants.LanguageEnUs.load()));
            setLanguage(LOCALE_EN_US);
            for(FileHandle discovered : discoveredFiles) {
                registerLanguage(LanguageFile.loadLanguageFile(discovered));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerLanguage(LanguageFile file) {
        Identifier identifier = getLocaleIdentifier(file.getLocale());
        AccessableRegistry<LanguageFile> LANGUAGE_FILES = ((AccessableRegistry<LanguageFile>) Registries.LANGUAGE_FILES);
        if(LANGUAGE_FILES.contains(identifier)) {
            LanguageFile existing = LANGUAGE_FILES.get(identifier);
            existing.merge(file);
        } else {
            Registries.LANGUAGE_FILES.register(identifier, file);
        }
        LanguageFile en_US = LANGUAGE_FILES.get(EN_US);
        Registries.LANGUAGES.register(identifier, new Language(en_US, file));
    }

    public static List<Locale> getLANGUAGES() {
        return Arrays.stream(((AccessableRegistry<Language>) Registries.LANGUAGES).getRegisteredNames()).map(id -> Locale.forLanguageTag(id.name)).toList();
    }

    public static void setLanguage(Locale locale){
        FluxSettings.SelectedLanguage = ((AccessableRegistry<Language>) Registries.LANGUAGES).get(getLocaleIdentifier(locale));
    }

}
